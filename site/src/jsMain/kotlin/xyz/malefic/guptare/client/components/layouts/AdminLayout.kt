package xyz.malefic.guptare.client.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderRight
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexShrink
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.addIfAbsent
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.PasswordInput
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppModifiers
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.InputStyle
import xyz.malefic.guptare.client.util.postApi

data class AdminLayoutScope(
    val token: String,
)

enum class AdminPage(
    val title: String,
    val route: String,
) {
    SITE("Site", "/admin/site"),
    HOME("Home", "/admin/"),
    WEBINAR("Webinar", "/admin/webinar"),
    BLOG("Blog", "/admin/blog"),
    TESTIMONIALS("Testimonials", "/admin/testimonials"),
}

data class AdminLayoutData(
    val page: AdminPage,
)

@InitRoute
fun initPageLayout(ctx: InitRouteContext) {
    ctx.data.addIfAbsent {
        console.warn("${ctx.route.path} did not set PageLayoutData")
        AdminLayoutData(
            AdminPage.HOME,
        )
    }
}

enum class VerificationState {
    VERIFYING,
    VERIFIED,
    EMPTY,
    INVALID,
}

@Layout
@Composable
fun AdminLayout(
    ctx: PageContext,
    content: @Composable AdminLayoutScope.() -> Unit,
) {
    var token by remember { mutableStateOf(localStorage.getItem("admin_token") ?: "") }
    var verificationState by remember {
        mutableStateOf(VerificationState.VERIFYING)
    }
    val page by remember { mutableStateOf(ctx.data.getValue<AdminLayoutData>().page) }

    LaunchedEffect(token) {
        if (token.isBlank()) {
            verificationState = VerificationState.EMPTY
            return@LaunchedEffect
        }
        verificationState = VerificationState.VERIFYING
        try {
            val response = postApi("admin/verify", token)
            if (response.ok) {
                verificationState = VerificationState.VERIFIED
                localStorage.setItem("admin_token", token)
            } else {
                verificationState = VerificationState.INVALID
            }
        } catch (e: Exception) {
            console.log("Network error during verification: $e")
            verificationState = VerificationState.INVALID
        }
    }

    if (verificationState != VerificationState.VERIFIED) {
        TokenInput(verificationState, token) { token = it }
    } else {
        Row(
            Modifier
                .fillMaxWidth()
                .minHeight(
                    100.vh,
                ).backgroundColor(AppColors.SurfaceLow),
        ) {
            Column(
                Modifier
                    .width(260.px)
                    .alignSelf(AlignSelf.Stretch)
                    .flexShrink(0)
                    .backgroundColor(AppColors.SurfaceContainer)
                    .padding(AppSpacing.S3)
                    .borderRight(1.px, LineStyle.Solid, AppColors.Outline),
            ) {
                H1(
                    HeadlineSmStyle
                        .toModifier()
                        .color(AppColors.Primary)
                        .margin(bottom = AppSpacing.S4)
                        .toAttrs(),
                ) {
                    Text("Dashboard")
                }

                key(page) {
                    page.route.run {
                        AdminPage.entries.forEach {
                            AdminNavLink(
                                it.title,
                                it.route,
                            )
                        }
                        AdminNavLink("Back", "/")
                    }
                }

                Box(
                    Modifier.height(
                        AppSpacing.S4,
                    ),
                )
                Button(
                    Modifier
                        .backgroundColor(Colors.Transparent)
                        .color(AppColors.Error)
                        .border(0.px)
                        .cursor(Cursor.Pointer)
                        .fontWeight(FontWeight.Bold)
                        .padding(16.px)
                        .toAttrs {
                            onClick {
                                localStorage.removeItem("admin_token")
                                token = ""
                                verificationState = VerificationState.EMPTY
                            }
                        },
                ) {
                    Text("Logout")
                }
            }

            Box(Modifier.fillMaxSize().padding(AppSpacing.S4)) {
                AdminLayoutScope(token)
                    .content()
            }
        }
    }
}

@Composable
private fun TokenInput(
    verificationState: VerificationState,
    token: String,
    setToken: (String) -> Unit,
) {
    Box(
        Modifier.fillMaxSize().backgroundColor(AppColors.Background),
        Alignment.Center,
    ) {
        Column(
            AppModifiers.Card
                .padding(AppSpacing.S4)
                .width(400.px)
                .gap(AppSpacing.S2),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            H1(
                HeadlineSmStyle
                    .toModifier()
                    .margin(
                        bottom = AppSpacing.S2,
                    ).toAttrs(),
            ) {
                Text("Admin Login")
            }
            P(
                BodyMdStyle
                    .toModifier()
                    .textAlign(TextAlign.Center)
                    .toAttrs(),
            ) {
                Text("Please enter your administrator token to continue.")
            }
            PasswordInput(
                token,
                InputStyle
                    .toModifier()
                    .toAttrs {
                        onInput { setToken(it.value) }
                        placeholder("Enter token...")
                    },
            )
            if (verificationState == VerificationState.INVALID) {
                P(
                    BodyMdStyle
                        .toModifier()
                        .color(
                            AppColors.Error,
                        ).toAttrs(),
                ) {
                    Text("Invalid token")
                }
            }
            if (verificationState == VerificationState.VERIFYING) {
                P(
                    BodyMdStyle
                        .toModifier()
                        .toAttrs(),
                ) { Text("Verifying...") }
            }
        }
    }
}

@Composable
context(path: String)
private fun AdminNavLink(
    label: String,
    route: String,
) {
    val isActive = remember { path == route }
    Link(
        route,
        Modifier
            .fillMaxWidth()
            .padding(AppSpacing.S2)
            .borderRadius(AppRadius.Default)
            .backgroundColor(
                if (isActive) AppColors.PrimaryContainer else Colors.Transparent,
            ).color(
                if (isActive) AppColors.OnPrimaryContainer else AppColors.OnSurfaceVariant,
            ).fontWeight(if (isActive) FontWeight.Bold else FontWeight.Normal)
            .textDecorationLine(TextDecorationLine.None)
            .margin(bottom = AppSpacing.S1),
    ) {
        Text(label)
    }
}
