package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount.Companion.Infinite
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import org.jetbrains.compose.web.css.AnimationTimingFunction.Companion.Linear
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppSpacing

@Composable
fun <T> Loading(
    value: T?,
    content: @Composable T.() -> Unit = {},
) = value?.let { value.content() } ?: Box(
    Modifier.fillMaxWidth().padding(topBottom = AppSpacing.SectionGap),
    Alignment.Center,
) {
    Spinner()
}

val SpinKeyframes =
    Keyframes {
        to {
            Modifier.transform { rotate(360.deg) }
        }
    }

@Composable
fun Spinner() {
    Box(
        Modifier
            .size(48.px)
            .borderRadius(50.percent)
            .border(4.px, LineStyle.Solid, AppColors.Primary)
            .borderTop { color(Colors.Transparent) }
            .animation(
                SpinKeyframes
                    .toAnimation(0.8.s, Linear, iterationCount = Infinite),
            ),
    )
}
