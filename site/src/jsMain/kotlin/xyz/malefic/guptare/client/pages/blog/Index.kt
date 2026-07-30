package xyz.malefic.guptare.client.pages.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.topBottom
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.ms.MsClose
import com.varabyte.kobweb.silk.components.icons.ms.MsSearch
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.SearchInput
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getBlog
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.BodyLgStyle
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.ContainerStyle
import xyz.malefic.guptare.client.styles.DisplayLgStyle
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle
import xyz.malefic.guptare.client.styles.PropertyCardStyle
import xyz.malefic.guptare.client.styles.SearchInputStyle
import xyz.malefic.guptare.client.styles.SectionStyle
import xyz.malefic.guptare.client.styles.StatusChipTertiaryStyle
import xyz.malefic.guptare.client.util.BlogSearch
import xyz.malefic.guptare.model.BlogPostResponse
import kotlin.collections.contains
import kotlin.time.Duration.Companion.milliseconds

@Page
@Composable
fun BlogIndexPage() {
    var posts by remember { mutableStateOf<List<BlogPostResponse>?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var debouncedQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val fetchedPosts =
            getBlog()
                .sortedByDescending { it.date }
        posts = fetchedPosts
        BlogSearch.build(fetchedPosts)
    }

    LaunchedEffect(searchQuery) {
        delay(300.milliseconds)
        debouncedQuery = searchQuery
    }

    val filteredPosts =
        remember(posts, debouncedQuery) {
            if (debouncedQuery.isBlank()) {
                posts
            } else {
                val results = BlogSearch.search(debouncedQuery)
                val scoreMap = results.associate { it.id to it.score }
                posts?.filter { it.id.toString() in scoreMap }?.sortedByDescending { scoreMap[it.id.toString()] ?: 0.0 }
            }
        }

    Column(Modifier.fillMaxSize()) {
        BlogHeroSection(searchQuery) { searchQuery = it }

        Box(
            SectionStyle.toModifier().backgroundColor(
                AppColors.SurfaceLowest,
            ),
            Alignment.Center,
        ) {
            Box(
                ContainerStyle
                    .toModifier(),
            ) {
                Loading(filteredPosts) {
                    if (this.isEmpty()) {
                        Box(Modifier.fillMaxWidth().padding(topBottom = 100.px), contentAlignment = Alignment.Center) {
                            P(
                                BodyLgStyle
                                    .toModifier()
                                    .toAttrs(),
                            ) {
                                Text(if (debouncedQuery.isEmpty()) "No blog posts found." else "No results matching \"$debouncedQuery\"")
                            }
                        }
                    } else {
                        SimpleGrid(
                            numColumns(1, sm = 2, md = 3),
                            Modifier
                                .gap(AppSpacing.Gutter)
                                .padding(topBottom = AppSpacing.SectionGap),
                        ) {
                            this.forEach { post ->
                                BlogCard(post)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogHeroSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
) {
    Box(
        SectionStyle.toModifier().backgroundColor(
            AppColors.SurfaceLow,
        ),
        Alignment.Center,
    ) {
        Box(
            ContainerStyle.toModifier().padding(
                topBottom = AppSpacing.SectionGap,
            ),
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Span(
                    LabelMdStyle
                        .toModifier()
                        .color(AppColors.Secondary)
                        .letterSpacing(0.2.em)
                        .textTransform(TextTransform.Uppercase)
                        .margin(bottom = 16.px)
                        .toAttrs(),
                ) {
                    Text("Insights & Updates")
                }
                H1(
                    DisplayLgStyle
                        .toModifier()
                        .textAlign(TextAlign.Center)
                        .margin(bottom = 24.px)
                        .toAttrs(),
                ) {
                    Text("The Realty Blog")
                }
                P(
                    BodyLgStyle
                        .toModifier()
                        .color(AppColors.OnSurfaceVariant)
                        .textAlign(TextAlign.Center)
                        .maxWidth(600.px)
                        .margin(bottom = 40.px)
                        .toAttrs(),
                ) {
                    Text("Expert guidance, market trends, and local lifestyle insights from Ruchika Gupta and the team.")
                }

                Box(Modifier.fillMaxWidth().maxWidth(500.px).position(Position.Relative), Alignment.CenterStart) {
                    MsSearch(
                        Modifier
                            .color(AppColors.OnSurfaceVariant)
                            .position(Position.Absolute)
                            .top(16.px)
                            .left(20.px)
                            .zIndex(1),
                    )

                    SearchInput(
                        searchQuery,
                        SearchInputStyle
                            .toModifier()
                            .padding {
                                left(52.px)
                                right(44.px)
                                topBottom(12.px)
                            }.toAttrs {
                                placeholder("Search articles by title, content or tags...")
                                onInput { onQueryChange(it.value) }
                            },
                    )

                    if (searchQuery.isNotEmpty()) {
                        Box(
                            Modifier
                                .position(Position.Absolute)
                                .top(16.px)
                                .right(20.px)
                                .zIndex(1)
                                .cursor(Cursor.Pointer)
                                .onClick { onQueryChange("") },
                        ) {
                            MsClose(
                                Modifier.color(AppColors.OnSurfaceVariant).size(18.px),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogCard(post: BlogPostResponse) {
    Link("/blog/${post.id}", Modifier.textDecorationLine(TextDecorationLine.None).fillMaxWidth()) {
        Column(
            PropertyCardStyle
                .toModifier()
                .fillMaxWidth(),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16, 9)
                    .borderRadius(AppRadius.Md)
                    .overflow(Overflow.Hidden),
            ) {
                Image(
                    post.imageSrc ?: "https://upload.wikimedia.org/wikipedia/commons/7/70/Example.png", // TODO: Better placeholder
                    post.title,
                    Modifier.fillMaxSize().objectFit(ObjectFit.Cover),
                )
            }

            Column(Modifier.padding(AppSpacing.S4)) {
                Row(Modifier.gap(8.px).margin(bottom = 12.px)) {
                    post.tags.take(3).forEach { tag ->
                        Span(
                            StatusChipTertiaryStyle
                                .toModifier()
                                .toAttrs(),
                        ) { Text(tag) }
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
                ) {
                    Text(post.summary)
                }

                Row(Modifier.gap(8.px), verticalAlignment = Alignment.CenterVertically) {
                    Span(
                        LabelMdStyle
                            .toModifier()
                            .color(
                                AppColors.Primary,
                            ).toAttrs(),
                    ) {
                        Text("Read More")
                    }
                }
            }
        }
    }
}
