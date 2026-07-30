package xyz.malefic.guptare.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Testimonial(
    val author: String,
    val quote: String,
    @SerialName("image_src") val imageSrc: String? = null,
)
