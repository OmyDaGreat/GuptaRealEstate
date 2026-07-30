package xyz.malefic.guptare.client.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.alignSelf
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridColumn
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getBlog
import xyz.malefic.guptare.client.api.getHomeInfo
import xyz.malefic.guptare.client.components.Iframe
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppModifiers
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.BodyLgStyle
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.ContainerStyle
import xyz.malefic.guptare.client.styles.DisplayLgStyle
import xyz.malefic.guptare.client.styles.HeadlineMdStyle
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import xyz.malefic.guptare.client.styles.PrimaryButtonStyle
import xyz.malefic.guptare.client.styles.SectionStyle
import xyz.malefic.guptare.client.styles.ShowOnMdStyle
import xyz.malefic.guptare.model.BlogPostResponse
import xyz.malefic.guptare.model.HelpBoxHomeInfo
import xyz.malefic.guptare.model.HomeInfo

@Page
@Composable
fun HomePage() {
    var info by remember { mutableStateOf<HomeInfo?>(null) }
    var posts by remember { mutableStateOf<List<BlogPostResponse>?>(null) }

    LaunchedEffect(Unit) {
        info =
            getHomeInfo()
        posts =
            getBlog()
                .sortedByDescending { it.date }
                .take(3)
    }

    Column(Modifier.fillMaxSize()) {
        HeroSection(info)
        StatsSection(info)
        AboutSection(info)
        HelpSection(info)
        InstagramSection(info)
        YoutubeSection(info)
        BlogPreviewSection(posts)
        ReviewSection(info)
    }
}

@Composable
fun HeroSection(info: HomeInfo?) =
    Box(
        SectionStyle
            .toModifier()
            .position(Position.Relative)
            .overflow(Overflow.Hidden)
            .minHeight(80.vh)
            .padding(bottom = AppSpacing.SectionGap),
        Alignment.Center,
    ) {
        Loading(info?.hero) {
            Image(
                image,
                "Hero Image",
                Modifier
                    .fillMaxSize()
                    .objectFit(ObjectFit.Cover)
                    .position(Position.Absolute)
                    .zIndex(1),
            )

            Box(
                ContainerStyle
                    .toModifier()
                    .zIndex(2),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .maxWidth(640.px)
                        .padding(topBottom = AppSpacing.SectionGap)
                        .textAlign(TextAlign.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    H1(
                        DisplayLgStyle
                            .toModifier()
                            .color(AppColors.Secondary)
                            .margin(bottom = 24.px)
                            .toAttrs(),
                    ) {
                        Text(title)
                    }
                    P(
                        BodyLgStyle
                            .toModifier()
                            .color(AppColors.OnBackground)
                            .margin(bottom = 32.px)
                            .toAttrs(),
                    ) {
                        Text(subtitle)
                    }
                    Box(Modifier.gap(16.px).flexWrap(FlexWrap.Wrap), contentAlignment = Alignment.Center) {
                        Link(
                            "/contact",
                            PrimaryButtonStyle
                                .toModifier(),
                        ) {
                            Text("Let's Talk")
                        }
                    }
                }
            }
        }
    }

@Composable
fun StatsSection(info: HomeInfo?) =
    Loading(info) {
        Box(
            Modifier
                .fillMaxWidth()
                .backgroundColor(AppColors.Secondary)
                .minHeight(248.px)
                .position(Position.Relative),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                ContainerStyle
                    .toModifier(),
            ) {
                SimpleGrid(
                    numColumns(1, sm = 2, md = stats.size.coerceAtMost(4)),
                    Modifier.fillMaxWidth().padding(AppSpacing s 2),
                ) {
                    stats.forEach {
                        Span(
                            HeadlineMdStyle
                                .toModifier()
                                .color(AppColors.OnSecondary)
                                .padding(16.px)
                                .textAlign(TextAlign.Center)
                                .toAttrs(),
                        ) {
                            Text(it)
                        }
                    }
                }
            }

            statsNotice?.let {
                Span(
                    LabelSmStyle
                        .toModifier()
                        .color(AppColors.OnSecondary)
                        .opacity(0.6)
                        .fontSize(0.7.em)
                        .position(Position.Absolute)
                        .bottom(12.px)
                        .right(24.px)
                        .toAttrs(),
                ) {
                    Text(it)
                }
            }
        }
    }

@Composable
fun AboutSection(info: HomeInfo?) =
    Box(
        Modifier
            .backgroundColor(AppColors.SurfaceContainer)
            .margin(top = AppSpacing s 8)
            .alignSelf(AlignSelf.Stretch),
        Alignment.Center,
    ) {
        Box(
            ContainerStyle.toModifier().padding(
                topBottom =
                    AppSpacing s 8,
            ),
        ) {
            SimpleGrid(
                numColumns(1, md = 12),
                Modifier.gap(AppSpacing.Gutter).alignItems(AlignItems.Center),
            ) {
                Box(Modifier.gridColumn("span 5")) {
                    Loading(info?.about) {
                        Image(
                            image,
                            "About Image",
                            Modifier
                                .fillMaxWidth()
                                .aspectRatio(4, 5)
                                .borderRadius(AppRadius.Lg)
                                .then(AppModifiers.SoftShadow),
                        )
                    }
                }
                Column(Modifier.gridColumn("span 7").padding(left = AppSpacing.S5)) {
                    Loading(info?.about) {
                        H2(
                            DisplayLgStyle
                                .toModifier()
                                .fontSize(32.px)
                                .margin(bottom = 24.px)
                                .toAttrs(),
                        ) {
                            Text(title)
                        }

                        P(
                            BodyLgStyle
                                .toModifier()
                                .whiteSpace(WhiteSpace.PreWrap)
                                .toAttrs(),
                        ) {
                            Text(description)
                        }
                    }
                    Box(Modifier.margin(top = 24.px).gap(16.px).flexWrap(FlexWrap.Wrap), Alignment.Center) {
                        Link(
                            "/contact",
                            PrimaryButtonStyle
                                .toModifier(),
                        ) {
                            Text("Let's Talk")
                        }
                    }
                }
            }
        }
    }

@Composable
fun HelpSection(info: HomeInfo?) =
    Box(
        SectionStyle.toModifier().padding(
            topBottom = AppSpacing.MarginDesktop,
        ),
    ) {
        Box(
            ContainerStyle
                .toModifier(),
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Loading(info?.help) {
                    H2(
                        DisplayLgStyle
                            .toModifier()
                            .fontSize(32.px)
                            .margin(bottom = 24.px)
                            .toAttrs(),
                    ) {
                        Text(title)
                    }
                    Span(
                        BodyLgStyle
                            .toModifier()
                            .whiteSpace(WhiteSpace.PreWrap)
                            .textAlign(TextAlign.Center)
                            .toAttrs(),
                    ) {
                        Text(description)
                    }

                    SimpleGrid(
                        numColumns(1, sm = 2, md = boxes.size.coerceAtMost(3)),
                        Modifier
                            .gap(AppSpacing.Gutter)
                            .margin(top = 48.px)
                            .fillMaxWidth(),
                    ) {
                        boxes.forEach {
                            HelpBox(it)
                        }
                    }
                }
            }
        }
    }

@Composable
fun HelpBox(info: HelpBoxHomeInfo) =
    Column(
        Modifier
            .fillMaxWidth()
            .backgroundColor(AppColors.SurfaceLow)
            .borderRadius(AppRadius.Lg)
            .overflow(Overflow.Hidden)
            .border(1.px, LineStyle.Solid, AppColors.Outline)
            .then(AppModifiers.ElevatedShadow),
    ) {
        Image(info.image, "${info.title} Help Image", Modifier.fillMaxWidth().height(240.px).objectFit(ObjectFit.Cover))
        Column(
            Modifier.padding(
                AppSpacing.S4,
            ),
        ) {
            H2(
                HeadlineMdStyle
                    .toModifier()
                    .color(AppColors.Secondary)
                    .margin(bottom = 8.px)
                    .toAttrs(),
            ) {
                Text(info.title)
            }
            P(
                BodyMdStyle
                    .toModifier()
                    .color(AppColors.OnBackground)
                    .margin(bottom = 24.px)
                    .toAttrs(),
            ) {
                Text(info.description)
            }
            Link(
                "/contact",
                PrimaryButtonStyle
                    .toModifier(),
            ) {
                Text("Learn More")
            }
        }
    }

@Composable
fun InstagramSection(info: HomeInfo?) =
    Box(
        Modifier
            .backgroundColor(AppColors.Secondary)
            .padding(
                top = AppSpacing s 8,
                leftRight =
                    AppSpacing s 8,
                bottom =
                    AppSpacing s 12,
            ).fillMaxWidth(),
    ) {
        Box(
            ContainerStyle
                .toModifier(),
        ) {
            Loading(info?.insta) {
                SimpleGrid(
                    numColumns(1, sm = 2, md = (posts.size + 1).coerceAtMost(4)),
                    Modifier.gap(AppSpacing.Gutter).alignItems(AlignItems.Center),
                ) {
                    Column {
                        H2(
                            DisplayLgStyle
                                .toModifier()
                                .fontSize(32.px)
                                .margin(bottom = 24.px)
                                .color(AppColors.OnSecondary)
                                .toAttrs(),
                        ) {
                            Text(title)
                        }
                        P(
                            BodyLgStyle
                                .toModifier()
                                .margin(bottom = 24.px)
                                .whiteSpace(WhiteSpace.PreWrap)
                                .color(AppColors.OnSecondary)
                                .toAttrs(),
                        ) {
                            Text(description)
                        }
                        Link(
                            followLink,
                            ShowOnMdStyle
                                .toModifier()
                                .color(Colors.White)
                                .textDecorationLine(TextDecorationLine.Underline),
                        ) {
                            Text("Follow on Instagram")
                        }
                    }
                    posts.forEach {
                        Iframe(
                            it,
                            Modifier.fillMaxWidth().aspectRatio(9, 16).minHeight(450.px),
                        )
                    }
                }
            }
        }
    }

@Composable
fun YoutubeSection(info: HomeInfo?) =
    Box(
        Modifier
            .backgroundColor(AppColors.SurfaceContainer)
            .margin(
                top = AppSpacing s -8,
                leftRight =
                    AppSpacing s 8,
            ).padding(AppSpacing s 8)
            .position(Position.Relative)
            .zIndex(2)
            .borderRadius(
                topLeft = AppRadius.Lg,
                topRight = AppRadius.Lg,
            ).alignSelf(AlignSelf.Stretch),
    ) {
        Box(
            ContainerStyle
                .toModifier(),
        ) {
            Loading(info?.youtube) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .maxWidth(800.px)
                            .margin(bottom = 48.px)
                            .textAlign(TextAlign.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        H2(
                            DisplayLgStyle
                                .toModifier()
                                .fontSize(32.px)
                                .margin(bottom = 24.px)
                                .toAttrs(),
                        ) {
                            Text(title)
                        }
                        P(
                            BodyLgStyle
                                .toModifier()
                                .margin(bottom = 24.px)
                                .whiteSpace(WhiteSpace.PreWrap)
                                .toAttrs(),
                        ) {
                            Text(description)
                        }
                        Link(
                            followLink,
                            ShowOnMdStyle
                                .toModifier()
                                .textDecorationLine(TextDecorationLine.Underline),
                        ) {
                            Text("Follow on YouTube")
                        }
                    }
                    SimpleGrid(
                        numColumns(1, sm = 2, md = posts.size.coerceAtMost(3)),
                        Modifier.gap(AppSpacing.Gutter).fillMaxWidth(),
                    ) {
                        posts.forEach {
                            Iframe(
                                it,
                                Modifier.fillMaxWidth().aspectRatio(16, 9),
                            )
                        }
                    }
                }
            }
        }
    }

@Composable
fun BlogPreviewSection(posts: List<BlogPostResponse>?) =
    Box(
        SectionStyle.toModifier().padding(
            topBottom = AppSpacing.MarginDesktop,
        ),
        Alignment.Center,
    ) {
        Column(
            ContainerStyle
                .toModifier()
                .justifyContent(JustifyContent.SpaceBetween)
                .margin(bottom = 48.px),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            H2(
                DisplayLgStyle
                    .toModifier()
                    .fontSize(32.px)
                    .toAttrs(),
            ) {
                Text("The Realty Blog")
            }
            Loading(posts) {
                SimpleGrid(
                    numColumns(1, md = 3),
                    Modifier
                        .gap(AppSpacing.Gutter)
                        .padding(AppSpacing.Gutter),
                ) {
                    this.forEach { post ->
                        BlogCard(post)
                    }
                }
            }
            Link(
                "/blog/",
                ShowOnMdStyle
                    .toModifier()
                    .color(AppColors.Primary)
                    .fontWeight(FontWeight.Bold)
                    .textDecorationLine(TextDecorationLine.None),
            ) {
                Text("View All Stories")
            }
        }
    }

@Composable
fun BlogCard(post: BlogPostResponse) =
    Column(Modifier.fillMaxWidth().cursor(Cursor.Pointer)) {
        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(16, 9)
                .borderRadius(AppRadius.Md)
                .overflow(Overflow.Hidden)
                .margin(bottom = 16.px),
        ) {
            post.imageSrc?.let {
                Image(
                    post.imageSrc,
                    post.title,
                    Modifier.fillMaxSize().objectFit(ObjectFit.Cover),
                )
            }
            Row(
                Modifier
                    .position(Position.Absolute)
                    .top(16.px)
                    .left(16.px)
                    .backgroundColor(AppColors.SecondaryContainer)
                    .padding(topBottom = 4.px, leftRight = 12.px)
                    .borderRadius(AppRadius.Full),
            ) {
                post.tags.forEach {
                    Span(
                        LabelSmStyle
                            .toModifier()
                            .color(AppColors.OnSecondaryContainer)
                            .padding(2.px)
                            .toAttrs(),
                    ) { Text(it) }
                }
            }
        }
        H3(
            HeadlineSmStyle
                .toModifier()
                .margin(bottom = 8.px)
                .toAttrs(),
        ) {
            Text(post.title)
        }
        P(
            BodyMdStyle
                .toModifier()
                .color(AppColors.OnSurfaceVariant)
                .margin(bottom = 16.px)
                .toAttrs(),
        ) { Text(post.summary) }
        Link(
            "/blog/${post.id}",
            Modifier
                .color(AppColors.Secondary)
                .fontWeight(FontWeight.Bold)
                .textDecorationLine(TextDecorationLine.None),
        ) {
            Text("Read Story")
        }
    }

@Composable
fun ReviewSection(info: HomeInfo?) =
    Box(
        SectionStyle
            .toModifier()
            .position(Position.Relative)
            .overflow(Overflow.Hidden)
            .minHeight(60.vh)
            .padding(bottom = AppSpacing.SectionGap),
        Alignment.Center,
    ) {
        Loading(info?.testimonial) {
            Image(
                imageSrc ?: "https://upload.wikimedia.org/wikipedia/commons/7/70/Example.png",
                "Review Image",
                Modifier
                    .fillMaxSize()
                    .objectFit(ObjectFit.Cover)
                    .position(Position.Absolute)
                    .zIndex(1),
            )

            Box(
                ContainerStyle
                    .toModifier()
                    .fillMaxHeight()
                    .zIndex(2),
                Alignment.CenterStart,
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .maxWidth(800.px)
                        .padding(topBottom = AppSpacing.SectionGap)
                        .textAlign(TextAlign.Center)
                        .fillMaxWidth(50.percent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    H1(
                        HeadlineMdStyle
                            .toModifier()
                            .color(AppColors.Secondary)
                            .margin(bottom = 24.px)
                            .toAttrs(),
                    ) {
                        Text(quote)
                    }
                    P(
                        HeadlineSmStyle
                            .toModifier()
                            .color(AppColors.OnBackground)
                            .margin(bottom = 32.px)
                            .maxWidth(800.px)
                            .marginInline(autoLength)
                            .toAttrs(),
                    ) {
                        Text("- $author")
                    }
                }
            }
        }
    }
