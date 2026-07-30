package xyz.malefic.guptare.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val message: String,
)
