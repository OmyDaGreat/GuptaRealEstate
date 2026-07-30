package xyz.malefic.guptare.server.api

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import xyz.malefic.guptare.model.HomeInfo
import xyz.malefic.guptare.model.json
import xyz.malefic.guptare.server.data.homeInfo
import xyz.malefic.guptare.server.util.auth
import xyz.malefic.guptare.server.util.error
import xyz.malefic.guptare.server.util.json

val home: Array<RoutingHttpHandler> =
    arrayOf(
        "/api/home" bind GET to { request ->
            json(homeInfo)
        },
        "/api/home" bind POST to
            auth {
                homeInfo =
                    try {
                        json.decodeFromString<HomeInfo>(bodyString())
                    } catch (e: Exception) {
                        return@auth error("Invalid home settings")
                    }

                Response(OK)
            },
    )
