package xyz.malefic.guptare.client.api

import xyz.malefic.guptare.client.util.deleteApi
import xyz.malefic.guptare.client.util.getApi
import xyz.malefic.guptare.client.util.getApiList
import xyz.malefic.guptare.client.util.postApi
import xyz.malefic.guptare.client.util.putApi
import xyz.malefic.guptare.model.BlogPostRequest
import xyz.malefic.guptare.model.BlogPostResponse
import kotlin.uuid.Uuid

suspend fun getBlog() = getApiList<BlogPostResponse>("blog")

suspend fun getBlog(id: Uuid) = getApi<BlogPostResponse>("blog?id=$id")

suspend fun postBlog(
    post: BlogPostRequest,
    token: String,
) = postApi("blog", post, token)

suspend fun putBlog(
    id: Uuid,
    post: BlogPostRequest,
    token: String,
) = putApi("blog/$id", post, token)

suspend fun deleteBlog(
    id: Uuid,
    token: String,
) = deleteApi("blog/$id", token)
