package xyz.malefic.guptare.server.util

import org.http4k.core.Filter
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.lens.RequestKey
import xyz.malefic.guptare.model.error
import kotlin.io.encoding.Base64
import kotlin.uuid.Uuid

val authenticatedUserId = RequestKey.required<Uuid>("authenticated-user-id")

val auth: Filter =
    Filter { next ->
        request@{ request ->
            val token =
                request
                    .header("Authorization")
                    ?.takeIf { it.startsWith("Bearer ") }
                    ?.removePrefix("Bearer ")
                    ?.trim()
            if (token.isNullOrBlank()) {
                return@request Response(UNAUTHORIZED).json("Missing bearer token".error)
            }
            if (!Base64.encode(token.encodeToByteArray()).contentEquals(bearerToken)) {
                return@request Response(UNAUTHORIZED).json("Invalid or expired token".error)
            }
            next(request.with(authenticatedUserId of userId))
        }
    }

val Request.id: Uuid
    get() = authenticatedUserId(this)

fun auth(next: Request.(Uuid) -> Response) = auth.then { request -> request.next(request.id) }
