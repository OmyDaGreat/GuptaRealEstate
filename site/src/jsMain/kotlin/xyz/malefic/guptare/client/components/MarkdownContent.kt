package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderLeft
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import js.objects.unsafeJso
import kotlinx.browser.document
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppFonts
import xyz.malefic.guptare.client.util.js.Editor
import kotlin.random.Random

val MarkdownContentStyle =
    CssStyle {
        cssRule(" .toastui-editor-contents") {
            Modifier
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(18.px)
                .lineHeight(1.6)
                .color(AppColors.OnSurface)
        }

        cssRule(" .toastui-editor-contents h1, .toastui-editor-contents h2, .toastui-editor-contents h3") {
            Modifier
                .fontFamily(*AppFonts.DISPLAY_STACK)
                .color(AppColors.OnBackground)
                .fontWeight(700)
                .margin(top = 1.5.em, bottom = 0.5.em)
        }

        cssRule(" .toastui-editor-contents blockquote") {
            Modifier
                .borderLeft(4.px, LineStyle.Solid, AppColors.OutlineVariant)
                .padding(left = 1.em)
                .margin(left = 0.px, top = 1.em, bottom = 1.em)
                .color(AppColors.OnSurfaceVariant)
        }

        cssRule(" .toastui-editor-contents hr") {
            Modifier
                .border(0.px)
                .borderTop(1.px, LineStyle.Solid, AppColors.OutlineVariant)
                .margin(topBottom = 2.em)
        }

        cssRule(" .toastui-editor-contents img") {
            Modifier
                .maxWidth(100.percent)
                .borderRadius(8.px)
        }
    }

@Composable
fun MarkdownContent(
    markdown: String,
    modifier: Modifier = Modifier,
) {
    val mountId = remember { "tui-viewer-${(Random.nextDouble() * 1_000_000).toInt()}" }
    var viewer by remember { mutableStateOf<dynamic>(null) }

    Div(
        MarkdownContentStyle
            .toModifier()
            .then(modifier)
            .id(mountId)
            .toAttrs(),
    )

    LaunchedEffect(mountId) {
        val el = document.getElementById(mountId) ?: return@LaunchedEffect
        viewer =
            Editor.factory(
                unsafeJso {
                    this.el = el
                    this.viewer = true
                    this.initialValue = markdown
                },
            )
    }

    LaunchedEffect(markdown) {
        viewer?.setMarkdown(markdown)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewer?.destroy()
        }
    }
}
