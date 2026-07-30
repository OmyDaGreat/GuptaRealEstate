package xyz.malefic.guptare.client.api

import kotlinx.datetime.TimeZone
import xyz.malefic.guptare.client.util.getApi
import xyz.malefic.guptare.client.util.getApiList
import xyz.malefic.guptare.client.util.postApi
import xyz.malefic.guptare.model.Registration
import xyz.malefic.guptare.model.Webinar
import xyz.malefic.guptare.model.WebinarReview
import xyz.malefic.guptare.model.WebinarTipsSection

suspend fun getWebinar() = getApi<Webinar>("webinar?tz=${TimeZone.currentSystemDefault().id}")

suspend fun postWebinar(
    token: String,
    webinar: Webinar,
) = postApi("webinar", webinar, token)

suspend fun getWebinarTips() = getApi<WebinarTipsSection>("webinar/tips")

suspend fun postWebinarTips(
    token: String,
    tips: WebinarTipsSection,
) = postApi("webinar/tips", tips, token)

suspend fun getWebinarReviews() = getApiList<WebinarReview>("webinar/reviews")

suspend fun postWebinarReviews(
    token: String,
    reviews: List<WebinarReview>,
) = postApi("webinar/reviews", reviews, token)

suspend fun getWebinarRegistrations(
    token: String,
    title: String? = null,
) = getApiList<Registration>("webinar/registrations${title?.let { "?title=$title" } ?: ""}", token)

suspend fun postWebinarRegistration(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    drip: Boolean = false,
) = postApi("webinar/register?drip=$drip", Registration(firstName, lastName, email, phone))
