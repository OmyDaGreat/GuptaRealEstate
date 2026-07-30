package xyz.malefic.guptare.model

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val error: String,
)

val String.error
    get() = Error(this)
