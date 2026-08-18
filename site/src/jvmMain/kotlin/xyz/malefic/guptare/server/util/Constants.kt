package xyz.malefic.guptare.server.util

import co.touchlab.kermit.Logger
import org.http4k.core.Method
import org.http4k.filter.AllowAllOriginPolicy
import org.http4k.filter.CorsPolicy
import java.io.File
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
    val envToken =
        System.getProperty("BEARER_TOKEN")?.takeIf { it.isNotBlank() }
            ?: System.getenv("BEARER_TOKEN")?.takeIf { it.isNotBlank() }

    if (envToken != null) {
        return@lazy Base64.encode(envToken.encodeToByteArray())
    }

    val tokenFile = File(assetsPath, ".bearer_token")
    val token =
        if (tokenFile.exists()) {
            tokenFile.readText().trim()
        } else {
            val newToken = Uuid.random().toString()
            try {
                tokenFile.parentFile?.mkdirs()
                tokenFile.writeText(newToken)
                Logger.w { "BEARER_TOKEN not found in environment. Generated and saved stable token to ${tokenFile.absolutePath}" }
            } catch (e: Exception) {
                Logger.e(e) { "Failed to save stable bearer token to ${tokenFile.absolutePath}" }
            }
            newToken
        }
    Base64.encode(token.encodeToByteArray())
}

val fubApiKey: String? = System.getProperty("FUB_API_KEY") ?: System.getenv("FUB_API_KEY")
