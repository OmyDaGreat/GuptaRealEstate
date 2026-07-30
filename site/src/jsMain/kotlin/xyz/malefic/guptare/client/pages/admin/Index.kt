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
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
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
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getHomeInfo
import xyz.malefic.guptare.client.api.postHomeSettings
import xyz.malefic.guptare.client.components.AdminField
import xyz.malefic.guptare.client.components.AdminFieldNull
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
import xyz.malefic.guptare.model.HelpBoxHomeInfo
import xyz.malefic.guptare.model.HomeInfo

@InitRoute
fun initIndexPage(ctx: InitRouteContext) {
    ctx.data.add(
        AdminLayoutData(
            AdminPage.HOME,
        ),
    )
}

@Page
@Composable
fun AdminLayoutScope.HomePage() {
    val scope = rememberCoroutineScope()
    var settings by remember { mutableStateOf<HomeInfo?>(null) }
    var message by remember { mutableStateOf<Pair<String, Boolean>?>(null) }
    var selectingImageFor by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        settings =
            getHomeInfo()
    }

    Column(Modifier.fillMaxSize().overflow(Overflow.Auto).padding(AppSpacing.S4)) {
        H2(
            HeadlineMdStyle
                .toModifier()
                .margin(
                    bottom = AppSpacing.S4,
                ).toAttrs(),
        ) {
            Text("Home Page Settings")
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
                    Text("Hero Section")
                }
                AdminField("Hero Title", hero.title) {
                    settings = settings?.copy(hero = hero.copy(title = it))
                }
                AdminField("Hero Subtitle", hero.subtitle) {
                    settings = settings?.copy(hero = hero.copy(subtitle = it))
                }
                Row(
                    Modifier.gap(AppSpacing.S2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AdminField("Hero Image URL", hero.image) {
                        settings = settings?.copy(hero = hero.copy(image = it))
                    }
                    Button(Modifier.onClick { selectingImageFor = "heroImage" }.toAttrs()) { Text("Pick") }
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Stats Section (Recommended: 3)")
                }
                stats.forEachIndexed { index, stat ->
                    Row(
                        Modifier.fillMaxWidth().gap(AppSpacing.S2),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(Modifier.flexGrow(1)) {
                            AdminField("Stat ${index + 1}", stat) { newValue ->
                                settings = settings?.copy(stats = stats.toMutableList().apply { this[index] = newValue })
                            }
                        }
                        Button(
                            Modifier
                                .onClick {
                                    settings = settings?.copy(stats = stats.toMutableList().apply { removeAt(index) })
                                }.toAttrs(),
                        ) { Text("✕") }
                    }
                }
                Button(
                    Modifier
                        .margin(top = AppSpacing.S2)
                        .onClick {
                            settings = settings?.copy(stats = stats + "")
                        }.toAttrs(),
                ) { Text("+ Add Stat") }

                AdminFieldNull("Stats Notice", statsNotice) {
                    settings = settings?.copy(statsNotice = it)
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Help Section (Recommended: 2-3)")
                }
                AdminField("Help Title", help.title) {
                    settings = settings?.copy(help = help.copy(title = it))
                }
                AdminTextArea("Help Description", help.description) {
                    settings = settings?.copy(help = help.copy(description = it))
                }

                help.boxes.forEachIndexed { index, box ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .margin(
                                top = AppSpacing.S3,
                            ).padding(AppSpacing.S2)
                            .borderRadius(AppRadius.Md)
                            .backgroundColor(AppColors.SurfaceContainer),
                    ) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            H3(
                                HeadlineSmStyle
                                    .toModifier()
                                    .toAttrs(),
                            ) { Text("Help Box ${index + 1}") }
                            Button(
                                Modifier
                                    .onClick {
                                        settings =
                                            settings?.copy(help = help.copy(boxes = help.boxes.toMutableList().apply { removeAt(index) }))
                                    }.toAttrs(),
                            ) { Text("✕") }
                        }
                        AdminField("Title", box.title) { newValue ->
                            settings =
                                settings?.copy(
                                    help =
                                        help.copy(
                                            boxes =
                                                help.boxes.toMutableList().apply {
                                                    this[index] =
                                                        box.copy(title = newValue)
                                                },
                                        ),
                                )
                        }
                        AdminField("Description", box.description) { newValue ->
                            settings =
                                settings?.copy(
                                    help =
                                        help.copy(
                                            boxes =
                                                help.boxes.toMutableList().apply {
                                                    this[index] =
                                                        box.copy(description = newValue)
                                                },
                                        ),
                                )
                        }
                        Row(
                            Modifier.gap(AppSpacing.S2),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            AdminField("Image URL", box.image) { newValue ->
                                settings =
                                    settings?.copy(
                                        help =
                                            help.copy(
                                                boxes =
                                                    help.boxes.toMutableList().apply {
                                                        this[index] =
                                                            box.copy(image = newValue)
                                                    },
                                            ),
                                    )
                            }
                            Button(Modifier.onClick { selectingImageFor = "helpBox:$index" }.toAttrs()) { Text("Pick") }
                        }
                    }
                }
                Button(
                    Modifier
                        .margin(top = AppSpacing.S3)
                        .onClick {
                            settings = settings?.copy(help = help.copy(boxes = help.boxes + HelpBoxHomeInfo("", "", "")))
                        }.toAttrs(),
                ) { Text("+ Add Help Box") }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("About Section")
                }
                AdminField("About Title", about.title) {
                    settings = settings?.copy(about = about.copy(title = it))
                }
                AdminTextArea("About Description", about.description) {
                    settings = settings?.copy(about = about.copy(description = it))
                }
                Row(
                    Modifier.gap(AppSpacing.S2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AdminField("About Image URL", about.image) {
                        settings = settings?.copy(about = about.copy(image = it))
                    }
                    Button(Modifier.onClick { selectingImageFor = "aboutImage" }.toAttrs()) { Text("Pick") }
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Instagram Section (Recommended: 2)")
                }
                AdminField("Insta Title", insta.title) {
                    settings = settings?.copy(insta = insta.copy(title = it))
                }
                AdminTextArea("Insta Description", insta.description) {
                    settings = settings?.copy(insta = insta.copy(description = it))
                }
                AdminField("Insta Follow Link", insta.followLink) {
                    settings = settings?.copy(insta = insta.copy(followLink = it))
                }
                insta.posts.forEachIndexed { index, post ->
                    Row(
                        Modifier.fillMaxWidth().gap(AppSpacing.S2),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(Modifier.flexGrow(1)) {
                            AdminField(
                                "Insta Post ${index + 1} ID",
                                post,
                            ) { newValue ->
                                settings =
                                    settings?.copy(insta = insta.copy(posts = insta.posts.toMutableList().apply { this[index] = newValue }))
                            }
                        }
                        Button(
                            Modifier
                                .onClick {
                                    settings =
                                        settings?.copy(insta = insta.copy(posts = insta.posts.toMutableList().apply { removeAt(index) }))
                                }.toAttrs(),
                        ) { Text("✕") }
                    }
                }
                Button(
                    Modifier
                        .margin(top = AppSpacing.S2)
                        .onClick {
                            settings = settings?.copy(insta = insta.copy(posts = insta.posts + ""))
                        }.toAttrs(),
                ) { Text("+ Add Insta Post") }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("YouTube Section (Recommended: 2)")
                }
                AdminField("YouTube Title", youtube.title) {
                    settings = settings?.copy(youtube = youtube.copy(title = it))
                }
                AdminTextArea("YouTube Description", youtube.description) {
                    settings = settings?.copy(youtube = youtube.copy(description = it))
                }
                AdminField("YouTube Follow Link", youtube.followLink) {
                    settings = settings?.copy(youtube = youtube.copy(followLink = it))
                }
                youtube.posts.forEachIndexed { index, post ->
                    Row(
                        Modifier.fillMaxWidth().gap(AppSpacing.S2),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(Modifier.flexGrow(1)) {
                            AdminField(
                                "YouTube Post ${index + 1} ID",
                                post,
                            ) { newValue ->
                                settings =
                                    settings?.copy(
                                        youtube =
                                            youtube.copy(
                                                posts =
                                                    youtube.posts.toMutableList().apply {
                                                        this[index] =
                                                            newValue
                                                    },
                                            ),
                                    )
                            }
                        }
                        Button(
                            Modifier
                                .onClick {
                                    settings =
                                        settings?.copy(
                                            youtube = youtube.copy(posts = youtube.posts.toMutableList().apply { removeAt(index) }),
                                        )
                                }.toAttrs(),
                        ) { Text("✕") }
                    }
                }
                Button(
                    Modifier
                        .margin(top = AppSpacing.S2)
                        .onClick {
                            settings = settings?.copy(youtube = youtube.copy(posts = youtube.posts + ""))
                        }.toAttrs(),
                ) { Text("+ Add YouTube Post") }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(
                            top = AppSpacing.S4,
                            bottom = AppSpacing.S3,
                        ).toAttrs(),
                ) {
                    Text("Featured Testimonial")
                }
                AdminField("Author", testimonial.author) {
                    settings = settings?.copy(testimonial = testimonial.copy(author = it))
                }
                AdminTextArea("Quote", testimonial.quote) {
                    settings = settings?.copy(testimonial = testimonial.copy(quote = it))
                }
                Row(
                    Modifier.gap(AppSpacing.S2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AdminFieldNull("Image URL", testimonial.imageSrc) {
                        settings = settings?.copy(testimonial = testimonial.copy(imageSrc = it))
                    }
                    Button(Modifier.onClick { selectingImageFor = "testimonialImage" }.toAttrs()) { Text("Pick") }
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
                                            postHomeSettings(settings!!, token)
                                            "Settings saved successfully" to true
                                        } catch (e: Exception) {
                                            Logger.e(e, "Index") { "Failed to save settings" }
                                            "Failed to save settings" to false
                                        }
                                }
                            }
                        },
                ) {
                    Text("Save All Settings")
                }
            }
        }

        AssetLibrary(token) { url ->
            settings =
                when {
                    selectingImageFor == "heroImage" -> {
                        settings?.copy(hero = settings!!.hero.copy(image = url))
                    }

                    selectingImageFor == "aboutImage" -> {
                        settings?.copy(about = settings!!.about.copy(image = url))
                    }

                    selectingImageFor?.startsWith("helpBox:") == true -> {
                        val index = selectingImageFor!!.split(":")[1].toInt()
                        settings?.copy(
                            help =
                                settings!!.help.copy(
                                    boxes =
                                        settings!!.help.boxes.toMutableList().apply {
                                            this[index] = this[index].copy(image = url)
                                        },
                                ),
                        )
                    }

                    selectingImageFor == "testimonialImage" -> {
                        settings?.copy(testimonial = settings!!.testimonial.copy(imageSrc = url))
                    }

                    else -> {
                        settings
                    }
                }
            selectingImageFor = null
        }
    }
}
