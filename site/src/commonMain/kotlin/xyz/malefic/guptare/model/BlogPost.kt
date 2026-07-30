package xyz.malefic.guptare.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.uuid.Uuid

@Serializable
data class BlogPostResponse(
    val id: Uuid,
    val title: String,
    val summary: String,
    val content: String,
    val imageSrc: String? = null,
    val tags: List<String> = emptyList(),
    val date: LocalDate,
)

@Serializable
data class BlogPostRequest(
    val title: String,
    val summary: String,
    val content: String,
    val imageSrc: String? = null,
    val tags: List<String> = emptyList(),
) {
    fun toResponse(
        id: Uuid,
        date: LocalDate =
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date,
    ) = BlogPostResponse(id, title, summary, content, imageSrc, tags, date)
}
