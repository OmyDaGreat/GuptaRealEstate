package xyz.malefic.guptare.server

import co.touchlab.kermit.Logger
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.hotreload.HotReloadServer
import org.http4k.hotreload.HotReloadable
import org.http4k.server.Undertow
import xyz.malefic.guptare.server.util.corsPolicy

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val server =
        HotReloadServer
            .http<ReloadableHttpApp>(Undertow(port), log = { Logger.d { it } }, error = { Logger.e { it } })
            .start()

    Logger.i { "Server started on port ${server.port()}!" }
}

class ReloadableHttpApp : HotReloadable<HttpHandler> {
    override fun create() = ServerFilters.Cors(corsPolicy).then(http)
}
