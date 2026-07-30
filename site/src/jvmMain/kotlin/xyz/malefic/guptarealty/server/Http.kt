package xyz.malefic.guptarealty.server

import co.touchlab.kermit.Logger
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import xyz.malefic.guptarealty.server.api.admin
import xyz.malefic.guptarealty.server.api.assets
import xyz.malefic.guptarealty.server.api.blog
import xyz.malefic.guptarealty.server.api.contact
import xyz.malefic.guptarealty.server.api.home
import xyz.malefic.guptarealty.server.api.site
import xyz.malefic.guptarealty.server.api.testimonial
import xyz.malefic.guptarealty.server.api.webinar
import xyz.malefic.guptarealty.server.util.assetsPath
import xyz.malefic.guptarealty.server.util.mimeTypes
import xyz.malefic.guptarealty.server.util.staticRoots
import java.io.File
import java.nio.file.Files

private fun serveStaticFile(req: Request): Response {
    val path = req.uri.path.removePrefix("/")
    val ext = path.substringAfterLast('.', "")

    if (path.startsWith("assets/")) {
        val file = File(assetsPath, path.removePrefix("assets/"))
        return if (file.exists() && file.isFile) {
            val contentType = mimeTypes.getOrDefault(ext.lowercase(), "application/octet-stream")
            Response(OK).header("Content-Type", contentType).body(file.inputStream(), file.length())
        } else {
            Response(NOT_FOUND)
        }
    }

    val target = if (path.isBlank() || ext.isBlank()) "index.html" else path
    val contentType = mimeTypes.getOrDefault(target.substringAfterLast('.', "").lowercase(), "text/html; charset=utf-8")

    for (root in staticRoots) {
        val file = root.resolve(target).normalize()
        if (file.startsWith(root) && Files.isRegularFile(file)) {
            return Response(OK).header("Content-Type", contentType).body(Files.newInputStream(file), Files.size(file))
        }
    }

    return Response(NOT_FOUND)
}

val apiRoutes: RoutingHttpHandler =
    routes(
        "/api/ping" bind GET to { Response(OK).body("pong") },
        "/api/health" bind GET to { Response(OK).body("healthy") },
        *admin,
        *assets,
        *blog,
        *contact,
        *home,
        *site,
        *testimonial,
        *webinar,
    )

val http: HttpHandler =
    ServerFilters.GZip().then { request ->
        if (request.uri.path.startsWith("/api/")) {
            apiRoutes(request).also { Logger.d { "Serving API: ${request.uri.path}" } }
        } else {
            serveStaticFile(request).also { Logger.d { "Serving static file: ${request.uri.path}" } }
        }
    }
