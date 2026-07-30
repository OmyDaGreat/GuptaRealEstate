package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import kotlin.random.Random

val PolaroidCardStyle =
    CssStyle {
        base {
            Modifier
                .width(clamp(220.px, 80.vw, 260.px))
                .backgroundColor(AppColors.SurfaceVariant)
                .padding(1.cssRem, 1.cssRem, 1.75.cssRem)
                .borderRadius(AppRadius.Default)
                .outline {
                    width(2.px)
                    style(LineStyle.Solid)
                    color(AppColors.Outline)
                }
        }

        hover {
            Modifier
                .zIndex(1)
                .boxShadow(4.px, 4.px, 8.px, 0.px, AppColors.Outline)
                .transition {
                    property("box-shadow")
                    duration(0.2.s)
                    timingFunction(AnimationTimingFunction.Ease)
                }
        }

        cssRule(":nth-child(3n)") {
            Modifier.width(clamp(240.px, 85.vw, 290.px))
        }
        cssRule(":nth-child(5n+1)") {
            Modifier.width(clamp(200.px, 75.vw, 230.px))
        }
    }

@Composable
fun Polaroid(
    author: String,
    content: String,
    src: String? = null,
    modifier: Modifier = Modifier,
) = Column(
    PolaroidCardStyle
        .toModifier()
        .rotate(
            Random.nextDouble(-5.0, 5.0).deg,
        ).then(modifier),
) {
    src?.let {
        Image(
            it,
            "Review Image",
            Modifier
                .fillMaxWidth()
                .outline {
                    width(1.px)
                    style(LineStyle.Solid)
                    color(AppColors.Outline)
                }.margin(bottom = 0.75.cssRem),
        )
    }
    Span(
        BodyMdStyle
            .toAttrs(),
    ) { Text(content) }
    Span(
        LabelSmStyle
            .toModifier()
            .align(Alignment.End)
            .toAttrs(),
    ) { Text(author) }
}
