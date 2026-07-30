package xyz.malefic.guptare.server.api

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import xyz.malefic.guptare.model.json
import xyz.malefic.guptare.server.data.testimonials
import xyz.malefic.guptare.server.util.auth
import xyz.malefic.guptare.server.util.json

val testimonial: Array<RoutingHttpHandler> =
    arrayOf(
        "/api/testimonials" bind GET to {
            json(testimonials)
        },
        "/api/testimonials" bind POST to
            auth {
                testimonials =
                    try {
                        json.decodeFromString(bodyString())
                    } catch (e: Exception) {
                        return@auth error("Invalid webinar reviews")
                    }

                Response(OK)
            },
    )
