package xyz.malefic.guptare.client.util

enum class TopLevelPages(
    val value: String,
    val route: String,
) {
    INDEX("Home", "/"),
    WEBINAR("Webinar", "/webinar"),
    BLOG("Blog", "/blog"),
    TESTIMONIALS("Testimonials", "/testimonials"),
    CONTACT("Contact", "/contact"),
}
