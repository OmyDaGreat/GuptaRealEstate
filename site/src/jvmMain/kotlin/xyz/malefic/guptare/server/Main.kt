package xyz.malefic.guptare.server

import co.touchlab.kermit.Logger
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.server.Jetty
import org.http4k.server.asServer
import xyz.malefic.guptare.server.util.corsPolicy

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val app = ServerFilters.Cors(corsPolicy).then(http)
    val server = app.asServer(Jetty(port)).start()

    Logger.i { "Server started on port ${server.port()}!" }
}
