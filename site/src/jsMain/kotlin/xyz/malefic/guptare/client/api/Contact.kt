package xyz.malefic.guptare.client.api

import xyz.malefic.guptare.client.util.postApi
import xyz.malefic.guptare.model.Contact

suspend fun postContact(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    message: String,
) = postApi("contact", Contact(firstName, lastName, email, phone, message))
