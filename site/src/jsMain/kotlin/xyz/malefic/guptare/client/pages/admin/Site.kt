package xyz.malefic.guptare.client.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getSiteSettings
import xyz.malefic.guptare.client.api.postSiteSettings
import xyz.malefic.guptare.client.components.AdminField
import xyz.malefic.guptare.client.components.AdminTextArea
import xyz.malefic.guptare.client.components.AssetLibrary
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.components.layouts.AdminLayoutData
import xyz.malefic.guptare.client.components.layouts.AdminLayoutScope
import xyz.malefic.guptare.client.components.layouts.AdminPage
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppModifiers
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.HeadlineMdStyle
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.SecondaryButtonStyle
import xyz.malefic.guptare.model.SiteInfo

@InitRoute
fun initSitePage(ctx: InitRouteContext) {
    ctx.data.add(
        AdminLayoutData(
            AdminPage.SITE,
        ),
    )
}

@Page
@Composable
fun AdminLayoutScope.SiteSettingsPage() {
    val scope = rememberCoroutineScope()
    var settings by remember { mutableStateOf<SiteInfo?>(null) }
    var message by remember { mutableStateOf<Pair<String, Boolean>?>(null) }
    var selectingImageFor by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        settings =
            getSiteSettings()
    }

    Column(Modifier.fillMaxSize().overflow(Overflow.Auto).padding(AppSpacing.S4)) {
        H2(
            HeadlineMdStyle
                .toModifier()
                .margin(
                    bottom = AppSpacing.S4,
                ).toAttrs(),
        ) {
            Text("Global Site Settings")
        }

        message?.let { message ->
            P(
                Modifier
                    .color(
                        if (message.second) AppColors.OnSecondaryContainer else AppColors.Error,
                    ).backgroundColor(
                        if (message.second) AppColors.SecondaryContainer else AppColors.ErrorContainer,
                    ).padding(AppSpacing.S2)
                    .borderRadius(AppRadius.Default)
                    .margin(bottom = AppSpacing.S3)
                    .toAttrs(),
            ) {
                Text(message.first)
            }
        }

        Column(
            AppModifiers.Card
                .padding(AppSpacing.S4)
                .fillMaxWidth()
                .margin(bottom = AppSpacing.S4),
        ) {
            Loading(settings) {
                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(bottom = AppSpacing.S3)
                        .toAttrs(),
                ) {
                    Text("Branding")
                }
                AdminField("Site Name", siteName) {
                    settings = settings?.copy(siteName = it)
                }
                Row(
                    Modifier.gap(AppSpacing.S2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AdminField("Logo URL", logoUrl) {
                        settings = settings?.copy(logoUrl = it)
                    }
                    Button(Modifier.onClick { selectingImageFor = "logoUrl" }.toAttrs()) { Text("Pick") }
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Agent Information")
                }
                AdminField("Agent Name", agentName) {
                    settings = settings?.copy(agentName = it)
                }
                AdminField("Agent License (DRE#)", agentLicense) {
                    settings = settings?.copy(agentLicense = it)
                }
                AdminField("Brokerage Name", brokerageName) {
                    settings = settings?.copy(brokerageName = it)
                }
                AdminField("Brokerage License (DRE#)", brokerageLicense) {
                    settings = settings?.copy(brokerageLicense = it)
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Affiliation Logos")
                }
                affiliationLogos.forEachIndexed { index, logo ->
                    Row(
                        Modifier.gap(AppSpacing.S2).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AdminField("Logo URL ${index + 1}", logo) { newVal ->
                            val newList = affiliationLogos.toMutableList()
                            newList[index] = newVal
                            settings = settings?.copy(affiliationLogos = newList)
                        }
                        Button(
                            Modifier
                                .onClick {
                                    val newList = affiliationLogos.toMutableList()
                                    newList.removeAt(index)
                                    settings = settings?.copy(affiliationLogos = newList)
                                }.toAttrs(),
                        ) { Text("Remove") }
                    }
                }
                Button(
                    Modifier
                        .onClick {
                            settings = settings?.copy(affiliationLogos = affiliationLogos + "")
                        }.toAttrs(),
                ) { Text("Add Logo") }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Contact Information")
                }
                AdminField("Email", agentEmail) {
                    settings = settings?.copy(agentEmail = it)
                }
                AdminField("Phone", agentPhone) {
                    settings = settings?.copy(agentPhone = it)
                }
                AdminField("Address", agentAddress) {
                    settings = settings?.copy(agentAddress = it)
                }
                AdminField("TikTok URL", tiktokUrl) {
                    settings = settings?.copy(tiktokUrl = it)
                }
                AdminField("Instagram URL", instagramUrl) {
                    settings = settings?.copy(instagramUrl = it)
                }
                AdminField("LinkedIn URL", linkedinUrl) {
                    settings = settings?.copy(linkedinUrl = it)
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Footer")
                }
                AdminTextArea("Footer Description", footerDescription) {
                    settings = settings?.copy(footerDescription = it)
                }
                AdminTextArea("Legal Disclaimer", disclaimerText) {
                    settings = settings?.copy(disclaimerText = it)
                }
                Row(
                    Modifier.gap(AppSpacing.S2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AdminField("Disclaimer Logo URL", logoUrl) {
                        settings = settings?.copy(logoUrl = it)
                    }
                    Button(Modifier.onClick { selectingImageFor = "disclaimerLogo" }.toAttrs()) { Text("Pick") }
                }

                Button(
                    SecondaryButtonStyle
                        .toModifier()
                        .margin(top = AppSpacing.S3)
                        .toAttrs {
                            onClick {
                                scope.launch {
                                    message =
                                        try {
                                            postSiteSettings(settings!!, token)
                                            "Settings saved successfully" to true
                                        } catch (e: Exception) {
                                            Logger.e(e, "Site") { "Failed to save settings" }
                                            "Failed to save settings" to false
                                        }
                                }
                            }
                        },
                ) {
                    Text("Save Site Settings")
                }
            }
        }

        AssetLibrary(token) { url ->
            if (selectingImageFor == "logoUrl") {
                settings = settings?.copy(logoUrl = url)
            }
            if (selectingImageFor == "disclaimerLogo") {
                settings = settings?.copy(disclaimerLogo = url)
            }
            selectingImageFor = null
        }
    }
}
