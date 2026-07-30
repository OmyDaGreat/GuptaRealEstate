package xyz.malefic.guptare.client.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.deleteBlog
import xyz.malefic.guptare.client.api.getBlog
import xyz.malefic.guptare.client.api.postBlog
import xyz.malefic.guptare.client.api.putBlog
import xyz.malefic.guptare.client.components.AdminField
import xyz.malefic.guptare.client.components.AdminTextArea
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.components.MarkdownEditor
import xyz.malefic.guptare.client.components.layouts.AdminLayoutData
import xyz.malefic.guptare.client.components.layouts.AdminLayoutScope
import xyz.malefic.guptare.client.components.layouts.AdminPage
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppModifiers
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.HeadlineMdStyle
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle
import xyz.malefic.guptare.model.BlogPostRequest
import xyz.malefic.guptare.model.BlogPostResponse

@InitRoute
fun initBlogPage(ctx: InitRouteContext) {
    ctx.data.add(
        AdminLayoutData(
            AdminPage.BLOG,
        ),
    )
}

@Page
@Composable
fun AdminLayoutScope.BlogPage() {
    val scope = rememberCoroutineScope()
    var posts by remember { mutableStateOf<List<BlogPostResponse>?>(null) }
    var editingPost by remember { mutableStateOf<BlogPostResponse?>(null) }
    var isCreating by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        posts =
            getBlog()
                .sortedByDescending { it.date }
    }

    Column(Modifier.fillMaxSize().overflow(Overflow.Auto).padding(AppSpacing.S4)) {
        H2(
            HeadlineMdStyle
                .toModifier()
                .margin(
                    bottom = AppSpacing.S4,
                ).toAttrs(),
        ) {
            Text("Blog Management")
        }

        if (editingPost != null || isCreating) {
            BlogEditor(
                post = editingPost,
                onSave = { request ->
                    scope.launch {
                        if (isCreating) {
                            postBlog(request, token)
                        } else {
                            putBlog(editingPost!!.id, request, token)
                        }
                        editingPost = null
                        isCreating = false
                        posts =
                            getBlog()
                                .sortedByDescending { it.date }
                    }
                },
                onCancel = {
                    editingPost = null
                    isCreating = false
                },
            )
        } else {
            Button(
                Modifier
                    .backgroundColor(AppColors.Secondary)
                    .color(AppColors.OnSecondary)
                    .padding(
                        AppSpacing.S2,
                        AppSpacing.S4,
                    ).borderRadius(50.px)
                    .border(0.px)
                    .cursor(Cursor.Pointer)
                    .margin(bottom = AppSpacing.S4)
                    .toAttrs {
                        onClick { isCreating = true }
                    },
            ) { Text("Create New Post") }

            Column(Modifier.fillMaxWidth().gap(AppSpacing.S2)) {
                Loading(posts) {
                    this.forEach { post ->
                        Row(
                            AppModifiers.Card
                                .padding(
                                    AppSpacing.S3,
                                ).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(Modifier.weight(1f)) {
                                H2(
                                    HeadlineSmStyle
                                        .toModifier()
                                        .toAttrs(),
                                ) { Text(post.title) }
                                P(
                                    LabelMdStyle
                                        .toModifier()
                                        .color(AppColors.OnSurfaceVariant)
                                        .toAttrs(),
                                ) {
                                    Text("${post.date} | ${post.tags.joinToString(", ")}")
                                }
                            }
                            Row(Modifier.gap(AppSpacing.S2)) {
                                Button(
                                    Modifier
                                        .backgroundColor(AppColors.Primary)
                                        .color(AppColors.OnPrimary)
                                        .padding(
                                            AppSpacing.S1,
                                            AppSpacing.S3,
                                        ).borderRadius(4.px)
                                        .border(0.px)
                                        .cursor(Cursor.Pointer)
                                        .toAttrs { onClick { editingPost = post } },
                                ) { Text("Edit") }
                                Button(
                                    Modifier
                                        .backgroundColor(Colors.Transparent)
                                        .color(AppColors.Error)
                                        .padding(
                                            AppSpacing.S1,
                                            AppSpacing.S3,
                                        ).borderRadius(4.px)
                                        .border(1.px, LineStyle.Solid, AppColors.Error)
                                        .cursor(Cursor.Pointer)
                                        .toAttrs {
                                            onClick {
                                                scope.launch {
                                                    deleteBlog(post.id, token)
                                                    posts =
                                                        getBlog()
                                                            .sortedByDescending { it.date }
                                                }
                                            }
                                        },
                                ) { Text("Delete") }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogEditor(
    post: BlogPostResponse?,
    onSave: (BlogPostRequest) -> Unit,
    onCancel: () -> Unit,
) {
    var title by remember { mutableStateOf(post?.title ?: "") }
    var summary by remember { mutableStateOf(post?.summary ?: "") }
    var content by remember { mutableStateOf(post?.content ?: "") }
    var imageUrl by remember { mutableStateOf(post?.imageSrc ?: "") }
    var tagsString by remember { mutableStateOf(post?.tags?.joinToString(", ") ?: "") }

    Column(
        AppModifiers.Card
            .padding(
                AppSpacing.S4,
            ).fillMaxWidth(),
    ) {
        H2(
            HeadlineSmStyle
                .toModifier()
                .margin(
                    bottom = AppSpacing.S3,
                ).toAttrs(),
        ) {
            Text(if (post == null) "New Blog Post" else "Edit Blog Post")
        }

        AdminField("Title", title) { title = it }
        AdminTextArea("Summary", summary) { summary = it }
        AdminField("Image URL", imageUrl) { imageUrl = it }
        AdminField("Tags (comma separated)", tagsString) { tagsString = it }

        Label(
            attrs =
                LabelMdStyle
                    .toModifier()
                    .margin(
                        bottom = AppSpacing.S1,
                    ).toAttrs(),
        ) {
            Text("Content")
        }
        MarkdownEditor(
            content,
            onChanged = { content = it },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .margin(bottom = AppSpacing.S3),
        )

        Row(Modifier.gap(AppSpacing.S2)) {
            Button(
                Modifier
                    .backgroundColor(AppColors.Primary)
                    .color(AppColors.OnPrimary)
                    .padding(
                        AppSpacing.S2,
                        AppSpacing.S4,
                    ).borderRadius(50.px)
                    .border(0.px)
                    .cursor(Cursor.Pointer)
                    .fontWeight(FontWeight.Bold)
                    .toAttrs {
                        onClick {
                            onSave(
                                BlogPostRequest(
                                    title = title,
                                    content = content,
                                    summary = summary,
                                    imageSrc = imageUrl,
                                    tags = tagsString.split(",").map { it.trim() }.filter { it.isNotBlank() },
                                ),
                            )
                        }
                    },
            ) { Text("Save Post") }

            Button(
                Modifier
                    .backgroundColor(Colors.Transparent)
                    .color(AppColors.OnSurfaceVariant)
                    .padding(
                        AppSpacing.S2,
                        AppSpacing.S4,
                    ).borderRadius(50.px)
                    .border(1.px, LineStyle.Solid, AppColors.OutlineVariant)
                    .cursor(Cursor.Pointer)
                    .toAttrs { onClick { onCancel() } },
            ) { Text("Cancel") }
        }
    }
}
