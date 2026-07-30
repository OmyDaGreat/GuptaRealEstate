package xyz.malefic.guptare.server.api

import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import xyz.malefic.guptare.server.util.auth

val admin: Array<RoutingHttpHandler> =
    arrayOf(
        "/api/admin/verify" bind POST to auth { Response(OK) },
    )
