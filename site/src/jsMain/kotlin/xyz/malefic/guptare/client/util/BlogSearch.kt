package xyz.malefic.guptare.client.util

import js.objects.unsafeJso
import xyz.malefic.guptare.client.util.js.BlogDocument
import xyz.malefic.guptare.client.util.js.MiniSearch
import xyz.malefic.guptare.client.util.js.MiniSearchOptions
import xyz.malefic.guptare.client.util.js.SearchOptions
import xyz.malefic.guptare.client.util.js.SearchResult
import xyz.malefic.guptare.model.BlogPostResponse

object BlogSearch {
    private var index: MiniSearch? = null

    fun build(posts: List<BlogPostResponse>) {
        val options =
            unsafeJso<MiniSearchOptions> {
                fields = arrayOf("title", "summary", "content", "tags")
                storeFields = arrayOf("title", "summary", "imageUrl", "tags", "date")
                idField = "id"
                extractField = { doc, field ->
                    when (field) {
                        "tags" -> doc.tags.joinToString(" ")
                        else -> doc.asDynamic()[field]
                    }
                }
            }

        val docs =
            posts
                .map { post ->
                    unsafeJso<BlogDocument> {
                        id = post.id.toString()
                        title = post.title
                        summary = post.summary
                        content = post.content
                        tags = post.tags.toTypedArray()
                        imageUrl = post.imageSrc
                        date = post.date.toString()
                    }
                }.toTypedArray()

        index = MiniSearch(options).also { it.addAll(docs) }
    }

    fun search(query: String): List<SearchResult> {
        val mini = index ?: return emptyList()
        val opts =
            unsafeJso<SearchOptions> {
                boost =
                    unsafeJso {
                        title = 3
                        summary = 2
                        tags = 2
                        content = 1
                    }
                fuzzy = 0.2
                prefix = true
            }
        return mini.search(query, opts).toList()
    }
}
