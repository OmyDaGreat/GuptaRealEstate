package xyz.malefic.guptare.model

import kotlinx.serialization.Serializable

@Serializable
data class SiteInfo(
    val siteName: String,
    val agentName: String,
    val agentLicense: String,
    val agentPhone: String,
    val agentEmail: String,
    val agentAddress: String,
    val brokerageName: String,
    val brokerageLicense: String,
    val footerDescription: String,
    val logoUrl: String,
    val affiliationLogos: List<String>,
    val tiktokUrl: String,
    val instagramUrl: String,
    val linkedinUrl: String,
    val disclaimerText: String,
    val disclaimerLogo: String?,
)
