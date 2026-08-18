package xyz.malefic.guptare.client.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.ms.MsMenu
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getSiteSettings
import xyz.malefic.guptare.client.components.CopyrightStrip
import xyz.malefic.guptare.client.components.Logo
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.HideOnMdStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import xyz.malefic.guptare.client.styles.NavBarStyle
import xyz.malefic.guptare.client.styles.NavLinkActiveStyle
import xyz.malefic.guptare.client.styles.NavLinkStyle
import xyz.malefic.guptare.client.styles.ShowOnMdStyle
import xyz.malefic.guptare.client.util.TopLevelPages
import xyz.malefic.guptare.model.SiteInfo

@Layout
@Composable
fun TopLevelLayout(content: @Composable () -> Unit) {
    val ctx = rememberPageContext()
    val currentRoute = ctx.route.path
    var isMenuOpen by remember { mutableStateOf(false) }
    var siteInfo by remember { mutableStateOf<SiteInfo?>(null) }

    LaunchedEffect(Unit) {
        siteInfo =
            try {
                getSiteSettings()
            } catch (_: Exception) {
                null
            }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .backgroundColor(AppColors.Background),
    ) {
        Box(NavBarStyle.toModifier(), Alignment.Center) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .maxWidth(AppSpacing.ContainerMax)
                    .height(64.px),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Link("/", Modifier.textDecorationLine(TextDecorationLine.None)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.gap(16.px)) {
                        Logo(
                            Modifier
                                .size(40.px)
                                .borderRadius(50.percent)
                                .border(1.px, LineStyle.Solid, AppColors.Outline),
                            siteInfo?.logoUrl,
                        )
                        Span(
                            HeadlineSmStyle
                                .toModifier()
                                .color(
                                    AppColors.Secondary,
                                ).toAttrs(),
                        ) {
                            Text(siteInfo?.siteName ?: "Gupta Real Estate")
                        }
                    }
                }

                Box(Modifier.flexGrow(1))

                Row(
                    ShowOnMdStyle.toModifier().gap(32.px),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TopLevelPages.entries.forEach { page ->
                        val isActive = page.isCurrentPage(currentRoute)
                        Link(
                            page.route,
                            if (isActive) {
                                NavLinkActiveStyle.toModifier()
                            } else {
                                NavLinkStyle.toModifier()
                            },
                        ) {
                            Text(page.value)
                        }
                    }
                }

                Button(
                    HideOnMdStyle
                        .toModifier()
                        .backgroundColor(Colors.Transparent)
                        .border(0.px)
                        .color(AppColors.Primary)
                        .cursor(Cursor.Pointer)
                        .toAttrs {
                            onClick { isMenuOpen = !isMenuOpen }
                        },
                ) {
                    MsMenu()
                }
            }
        }

        if (isMenuOpen) {
            Box(
                Modifier
                    .fillMaxSize()
                    .position(Position.Fixed)
                    .zIndex(1000)
                    .backgroundColor(Color.rgba(0, 0, 0, 0.5f))
                    .onClick { isMenuOpen = false },
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(280.px)
                        .backgroundColor(AppColors.Surface)
                        .padding(24.px)
                        .onClick { it.stopPropagation() },
                ) {
                    Row(Modifier.margin(bottom = 32.px).gap(16.px), verticalAlignment = Alignment.CenterVertically) {
                        Logo(
                            Modifier.size(64.px).borderRadius(50.percent),
                            siteInfo?.logoUrl,
                        )
                        Column {
                            Span(
                                HeadlineSmStyle
                                    .toModifier()
                                    .color(
                                        AppColors.Primary,
                                    ).toAttrs(),
                            ) {
                                Text(siteInfo?.agentName ?: "Ruchika Gupta")
                            }
                            Span(
                                LabelSmStyle
                                    .toModifier()
                                    .color(
                                        AppColors.OnSurfaceVariant,
                                    ).toAttrs(),
                            ) {
                                Text(siteInfo?.agentLicense ?: "DRE# 02161384")
                            }
                            Span(
                                LabelSmStyle
                                    .toModifier()
                                    .color(
                                        AppColors.Secondary,
                                    ).toAttrs(),
                            ) {
                                Text(siteInfo?.agentPhone ?: "714-767-5752")
                            }
                        }
                    }

                    TopLevelPages.entries.forEach { page ->
                        val isActive = page.isCurrentPage(currentRoute)
                        Link(
                            page.route,
                            Modifier
                                .fillMaxWidth()
                                .padding(12.px, 16.px)
                                .borderRadius(AppRadius.Full)
                                .backgroundColor(
                                    if (isActive) AppColors.PrimaryContainer else Colors.Transparent,
                                ).color(
                                    if (isActive) AppColors.OnPrimaryContainer else AppColors.OnSurfaceVariant,
                                ).fontWeight(if (isActive) 700 else 400)
                                .textDecorationLine(TextDecorationLine.None)
                                .onClick { isMenuOpen = false },
                        ) {
                            Text(page.value)
                        }
                    }
                }
            }
        }

        Column(Modifier.fillMaxWidth()) {
            content()
            CopyrightStrip(siteInfo)
        }
    }
}

fun TopLevelPages.isCurrentPage(currentRoute: String): Boolean = currentRoute.trimEnd('/') == route.trimEnd('/')
