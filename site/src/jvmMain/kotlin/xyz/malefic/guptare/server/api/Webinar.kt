package xyz.malefic.guptare.server.api

import co.touchlab.kermit.Logger
import org.http4k.client.OkHttp
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.Credentials
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.accept
import org.http4k.lens.basicAuthentication
import org.http4k.lens.contentType
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import xyz.malefic.guptare.model.EmailEntry
import xyz.malefic.guptare.model.EventPerson
import xyz.malefic.guptare.model.FollowUpBossEvent
import xyz.malefic.guptare.model.PhoneEntry
import xyz.malefic.guptare.model.Registration
import xyz.malefic.guptare.model.error
import xyz.malefic.guptare.model.json
import xyz.malefic.guptare.server.data.currentWebinar
import xyz.malefic.guptare.server.data.registrations
import xyz.malefic.guptare.server.data.webinarReviews
import xyz.malefic.guptare.server.data.webinarTips
import xyz.malefic.guptare.server.util.auth
import xyz.malefic.guptare.server.util.contains
import xyz.malefic.guptare.server.util.error
import xyz.malefic.guptare.server.util.fubApiKey
import xyz.malefic.guptare.server.util.json

private val log = Logger.withTag("Webinar")
private val client = OkHttp()

val webinar: Array<RoutingHttpHandler> =
    arrayOf(
        "/api/webinar" bind GET to { _ ->
            json(currentWebinar)
        },
        "/api/webinar" bind POST to
            auth {
                currentWebinar =
                    try {
                        json.decodeFromString(bodyString())
                    } catch (_: Exception) {
                        return@auth error("Invalid webinar")
                    }

                Response(OK)
            },
        "/api/webinar/tips" bind GET to {
            json(webinarTips)
        },
        "/api/webinar/tips" bind POST to
            auth {
                webinarTips =
                    try {
                        json.decodeFromString(bodyString())
                    } catch (_: Exception) {
                        return@auth Response(BAD_REQUEST).json("Invalid webinar tips".error)
                    }

                Response(OK)
            },
        "/api/webinar/reviews" bind GET to {
            json(webinarReviews)
        },
        "/api/webinar/reviews" bind POST to
            auth {
                webinarReviews =
                    try {
                        json.decodeFromString(bodyString())
                    } catch (_: Exception) {
                        return@auth error("Invalid webinar reviews")
                    }

                Response(OK)
            },
        "/api/webinar/registrations" bind GET to
            auth {
                if (query("title") == null) {
                    return@auth json(registrations)
                } else {
                    val title = query("title")!!
                    return@auth json(registrations[title] ?: emptyList())
                }
            },
        "/api/webinar/register" bind POST to request@{ request ->
            if (fubApiKey == null) {
                log.e { "Missing FUB_API_KEY environment variable" }
                return@request error("Missing CRM api key")
            }

            val registration =
                try {
                    json.decodeFromString<Registration>(request.bodyString())
                } catch (_: Exception) {
                    return@request error("Invalid registration")
                }

            val drip = request.query("drip").toBoolean()

            val payload =
                FollowUpBossEvent(
                    person =
                        EventPerson(
                            registration.firstName,
                            registration.lastName,
                            listOf(EmailEntry(registration.email)),
                            listOf(PhoneEntry(registration.phone)),
                            source = "guptare.com/webinar",
                            tags = listOf("Webinar") + if (drip) listOf("Drip") else emptyList(),
                        ),
                    description = "Signed up for webinar \"${currentWebinar.title}\"",
                )

            val request =
                Request(POST, "https://api.followupboss.com/v1/events")
                    .accept(APPLICATION_JSON)
                    .basicAuthentication(Credentials(fubApiKey, ""))
                    .contentType(APPLICATION_JSON)
                    .body(json.encodeToString(payload))

            val response = client(request)

            if (response.status !in Status.SUCCESSFUL) {
                log.e { "Failed to send registration to FollowUpBoss; returned\n${response.toMessage()}" }
                return@request error("Failed to send registration to FollowUpBoss")
            }

            registrations[currentWebinar.title]?.add(registration) ?: registrations.put(currentWebinar.title, arrayListOf(registration))

            Response(OK)
        },
    )
