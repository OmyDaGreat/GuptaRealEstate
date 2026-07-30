package xyz.malefic.guptarealty.server.util

import co.touchlab.kermit.Logger
import org.http4k.core.Method
import org.http4k.filter.AllowAllOriginPolicy
import org.http4k.filter.CorsPolicy
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.encoding.Base64
import kotlin.uuid.Uuid

val corsPolicy =
    CorsPolicy(
        headers = listOf("Content-Type", "Authorization"),
        methods = Method.entries,
        originPolicy = AllowAllOriginPolicy,
    )

val mimeTypes =
    mapOf(
        "html" to "text/html; charset=utf-8",
        "js" to "application/javascript",
        "css" to "text/css",
        "jpg" to "image/jpeg",
        "jpeg" to "image/jpeg",
        "png" to "image/png",
        "svg" to "image/svg+xml",
        "ico" to "image/x-icon",
        "webp" to "image/webp",
        "woff" to "font/woff",
        "woff2" to "font/woff2",
        "json" to "application/json",
    )

val staticRoots: List<Path> by lazy {
    listOf(
        Paths.get("build", "dist", "js", "productionExecutable"),
        Paths.get("build", "dist", "js", "productionExecutable", "public"),
        Paths.get("site", "static"),
        Paths.get("/app", "site", "static"),
        Paths.get("/app", "site", "build", "dist", "js", "productionExecutable"),
        Paths.get("/app", "site", "build", "dist", "js", "productionExecutable", "public"),
    ).filter {
        val isDir = Files.isDirectory(it)
        if (isDir) Logger.i { "Static root found: ${it.toAbsolutePath()}" }
        isDir
    }
}

val assetsPath: String = System.getProperty("ASSETS_PATH") ?: System.getenv("ASSETS_PATH") ?: "assets"

val userId = Uuid.random()

val bearerToken: String by lazy {
    val rawToken =
        System.getProperty("BEARER_TOKEN")?.takeIf { it.isNotBlank() }
            ?: System.getenv("BEARER_TOKEN")?.takeIf { it.isNotBlank() }
            ?: Uuid.random().toString().also {
                Logger.w { "BEARER_TOKEN not found in environment. Generated random token for this session: $it" }
            }
    Base64.encode(rawToken.encodeToByteArray())
}

val fubApiKey: String? = System.getProperty("FUB_API_KEY") ?: System.getenv("FUB_API_KEY")
