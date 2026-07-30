package xyz.malefic.guptare.client.api

import xyz.malefic.guptare.client.util.getApiList
import xyz.malefic.guptare.client.util.postApi
import xyz.malefic.guptare.model.Testimonial

suspend fun getTestimonials() = getApiList<Testimonial>("testimonials")

suspend fun postTestimonials(
    token: String,
    reviews: List<Testimonial>,
) = postApi("testimonials", reviews, token)
