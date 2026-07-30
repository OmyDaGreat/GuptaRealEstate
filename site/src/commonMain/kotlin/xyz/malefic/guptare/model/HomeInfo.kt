package xyz.malefic.guptare.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeInfo(
    val hero: HeroHomeInfo,
    val stats: List<String>,
    @SerialName("stats_notice") val statsNotice: String?,
    val help: HelpHomeInfo,
    val about: AboutHomeInfo,
    val insta: SocialHomeInfo,
    val youtube: SocialHomeInfo,
    val testimonial: Testimonial,
)

@Serializable
data class HeroHomeInfo(
    val title: String,
    val subtitle: String,
    val image: String,
)

@Serializable
data class HelpHomeInfo(
    val title: String,
    val description: String,
    val boxes: List<HelpBoxHomeInfo>,
)

@Serializable
data class HelpBoxHomeInfo(
    val image: String,
    val title: String,
    val description: String,
)

@Serializable
data class SocialHomeInfo(
    val title: String,
    val description: String,
    @SerialName("follow_link") val followLink: String,
    val posts: List<String>,
)

@Serializable
data class AboutHomeInfo(
    val title: String,
    val description: String,
    val image: String,
)
