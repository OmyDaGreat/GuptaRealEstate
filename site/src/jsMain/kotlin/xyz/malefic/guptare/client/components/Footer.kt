package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaInstagram
import com.varabyte.kobweb.silk.components.icons.fa.FaLinkedin
import com.varabyte.kobweb.silk.components.icons.fa.FaTiktok
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.ContainerStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import xyz.malefic.guptare.client.styles.SectionInverseStyle
import xyz.malefic.guptare.model.SiteInfo

@Composable
fun CopyrightStrip(siteInfo: SiteInfo?) =
    Box(
        SectionInverseStyle.toModifier().padding(
            top = AppSpacing.S6,
            bottom = AppSpacing.S4,
        ),
        Alignment.Center,
    ) {
        Box(
            ContainerStyle
                .toModifier(),
        ) {
            Column(
                Modifier.fillMaxWidth().gap(AppSpacing.S4),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Logos
                Row(
                    Modifier.gap(AppSpacing.S4),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    siteInfo?.affiliationLogos?.forEach { logoUrl ->
                        Image(logoUrl, "Affiliation Logo", Modifier.height(40.px))
                    }
                }

                // Social Icons
                Row(
                    Modifier.gap(AppSpacing.S4),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SocialIcon(siteInfo?.tiktokUrl) { FaTiktok() }
                    SocialIcon(siteInfo?.instagramUrl) { FaInstagram() }
                    SocialIcon(siteInfo?.linkedinUrl) { FaLinkedin() }
                }

                // Professional Info
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    P(
                        LabelSmStyle
                            .toModifier()
                            .color(AppColors.InverseOnSurface)
                            .textAlign(TextAlign.Center)
                            .margin(bottom = 4.px)
                            .toAttrs(),
                    ) {
                        Text("${siteInfo?.agentName ?: "Ruchika Gupta"} | Realtor® | ${siteInfo?.agentLicense ?: "DRE #02161384"}")
                    }
                    P(
                        LabelSmStyle
                            .toModifier()
                            .color(AppColors.InverseOnSurface)
                            .textAlign(TextAlign.Center)
                            .margin(bottom = 4.px)
                            .toAttrs(),
                    ) {
                        Text(
                            "${siteInfo?.brokerageName ?: "First Team Real Estate"} | ${siteInfo?.brokerageLicense ?: "[First Team's DRE #]"}",
                        )
                    }
                    P(
                        LabelSmStyle
                            .toModifier()
                            .color(AppColors.InverseOnSurface)
                            .textAlign(TextAlign.Center)
                            .margin(bottom = AppSpacing.S2)
                            .toAttrs(),
                    ) {
                        Text("© 2026 ${siteInfo?.agentName ?: "Ruchika Gupta"}. All rights reserved.")
                    }
                }

                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically,
                ) {
                    P(
                        Modifier
                            .color(AppColors.InverseOnSurface)
                            .opacity(0.7)
                            .fontSize(0.75.em)
                            .lineHeight(1.4)
                            .textAlign(TextAlign.Center)
                            .margin(top = AppSpacing.S2)
                            .toAttrs(),
                    ) {
                        Text(
                            siteInfo?.disclaimerText
                                ?: "All information provided is deemed reliable but is not guaranteed and should be independently verified. This is not intended as legal, tax, or financial advice. If you are currently working with another real estate agent under a signed representation agreement, this is not a solicitation of that business relationship.",
                        )
                    }
                    siteInfo?.disclaimerLogo?.let {
                        Image(
                            it,
                            "Equal Housing Opportunity",
                            Modifier.width(84.px).aspectRatio(1),
                        )
                    }
                }
            }
        }
    }

@Composable
private fun SocialIcon(
    url: String?,
    icon: @Composable () -> Unit,
) {
    Link(
        url ?: "#",
        Modifier
            .color(AppColors.InverseOnSurface)
            .opacity(0.8)
            .textDecorationLine(TextDecorationLine.None),
    ) {
        icon()
    }
}
