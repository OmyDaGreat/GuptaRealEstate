package xyz.malefic.guptare.server.api

import co.touchlab.kermit.Logger
import org.http4k.client.OkHttp
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.Credentials
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.accept
import org.http4k.lens.basicAuthentication
import org.http4k.lens.contentType
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import xyz.malefic.guptare.model.Contact
import xyz.malefic.guptare.model.EmailEntry
import xyz.malefic.guptare.model.EventPerson
import xyz.malefic.guptare.model.FollowUpBossEvent
import xyz.malefic.guptare.model.PhoneEntry
import xyz.malefic.guptare.model.json
import xyz.malefic.guptare.server.util.contains
import xyz.malefic.guptare.server.util.error
import xyz.malefic.guptare.server.util.fubApiKey

private val log = Logger.withTag("Contact")
private val client = OkHttp()

val contact: Array<RoutingHttpHandler> =
    arrayOf(
        "/api/contact" bind POST to request@{ request ->
            if (fubApiKey == null) {
                log.e { "Missing FUB_API_KEY environment variable" }
                return@request error("Missing CRM api key")
            }

            val contact =
                try {
                    json.decodeFromString<Contact>(request.bodyString())
                } catch (_: Exception) {
                    return@request error("Invalid contact")
                }

            if (!contact.email.contains("@") || !contact.email.contains(".")) {
                return@request error("Invalid email address")
            }
            if (contact.phone.filter { it.isDigit() }.length < 10) {
                return@request error("Invalid phone number")
            }

            val payload =
                FollowUpBossEvent(
                    "guptare.com/contact",
                    type = "Inquiry",
                    person =
                        EventPerson(
                            contact.firstName,
                            contact.lastName,
                            listOf(EmailEntry(contact.email)),
                            listOf(PhoneEntry(contact.phone)),
                            source = "guptare.com/contact",
                            tags = listOf("Inquiry"),
                        ),
                    description =
                        contact.message
                            .trim()
                            .replace("\r\n", "\n")
                            .replace('\r', '\n')
                            .replace(Regex("[\\u0000-\\u0008\\u000B\\u000C\\u000E-\\u001F\\u007F]"), "")
                            .take(1000),
                )

            val request =
                Request(POST, "https://api.followupboss.com/v1/events")
                    .accept(APPLICATION_JSON)
                    .basicAuthentication(Credentials(fubApiKey, ""))
                    .contentType(APPLICATION_JSON)
                    .body(json.encodeToString(payload))

            val response = client(request)

            if (response.status !in Status.SUCCESSFUL) {
                log.e { "Failed to send contact to FollowUpBoss; returned\n${response.toMessage()}" }
                return@request error("Failed to send contact to FollowUpBoss")
            }

            Response(OK)
        },
    )
