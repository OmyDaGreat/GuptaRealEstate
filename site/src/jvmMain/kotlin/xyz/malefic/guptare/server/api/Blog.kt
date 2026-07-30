package xyz.malefic.guptare.server.api

import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Method.PUT
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.path
import xyz.malefic.guptare.model.BlogPostRequest
import xyz.malefic.guptare.model.json
import xyz.malefic.guptare.server.data.blogs
import xyz.malefic.guptare.server.util.auth
import xyz.malefic.guptare.server.util.error
import xyz.malefic.guptare.server.util.json
import kotlin.uuid.Uuid

val blog: Array<RoutingHttpHandler> =
    arrayOf(
        "/api/blog" bind GET to request@{ request ->
            val id =
                try {
                    Uuid.parse(request.query("id") ?: return@request Response(OK).json(blogs))
                } catch (_: Exception) {
                    return@request error("Invalid blog post ID")
                }

            json(blogs.first { it.id == id })
        },
        "/api/blog" bind POST to
            auth {
                val post =
                    try {
                        json.decodeFromString<BlogPostRequest>(bodyString())
                    } catch (_: Exception) {
                        return@auth error("Invalid blog post")
                    }

                blogs += post.toResponse(Uuid.random())
                Response(OK)
            },
        "/api/blog/{id}" bind PUT to
            auth {
                val id = path("id")?.let { Uuid.parse(it) } ?: return@auth error("Missing blog post ID")
                val post =
                    try {
                        json.decodeFromString<BlogPostRequest>(bodyString()).toResponse(id)
                    } catch (_: Exception) {
                        return@auth error("Invalid blog post")
                    }

                val index = blogs.indexOfFirst { it.id == id }
                if (index == -1) return@auth error("Blog post not found")
                blogs[index] = post
                Response(OK)
            },
        "/api/blog/{id}" bind DELETE to
            auth {
                val id = path("id")?.let { Uuid.parse(it) } ?: return@auth error("Missing blog post ID")
                val index = blogs.indexOfFirst { it.id == id }
                if (index == -1) return@auth error("Blog post not found")
                blogs.removeAt(index)
                Response(OK)
            },
    )
