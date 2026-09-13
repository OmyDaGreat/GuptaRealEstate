package xyz.malefic.guptare.client.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.css.vh
import xyz.malefic.guptare.client.api.getTestimonials
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.components.Polaroid
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.model.Testimonial

val ScrollUpKeyframes =
    Keyframes {
        from { Modifier.translateY(0.percent) }
        to { Modifier.translateY((-50).percent) }
    }

val ScrollDownKeyframes =
    Keyframes {
        from { Modifier.translateY((-50).percent) }
        to { Modifier.translateY(0.percent) }
    }

val MarqueeContainerStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxSize()
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Row)
                .gap(AppSpacing.S3)
                .padding(topBottom = AppSpacing.S2)
                .overflow(Overflow.Hidden)
        }
    }

val MarqueeColumnStyle =
    CssStyle {
        base {
            Modifier
                .flex(1)
                .height(100.vh)
                .overflow(Overflow.Hidden)
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Column)
        }
    }

val MarqueeContentStyle =
    CssStyle {
        hover {
            Modifier.styleModifier {
                property("animation-play-state", "paused")
            }
        }
    }

val Column1Style = CssStyle { base { Modifier.display(DisplayStyle.Flex) } }
val Column2Style =
    CssStyle {
        base { Modifier.display(DisplayStyle.None) }
        Breakpoint.MD { Modifier.display(DisplayStyle.Flex) }
    }
val Column3Style =
    CssStyle {
        base { Modifier.display(DisplayStyle.None) }
        Breakpoint.LG { Modifier.display(DisplayStyle.Flex) }
    }

@Page
@Composable
fun TestimonialsPage() {
    var testimonials by remember { mutableStateOf<List<Testimonial>?>(null) }

    LaunchedEffect(Unit) {
        testimonials = getTestimonials()
    }

    Loading(testimonials) {
        val allTestimonials = this@Loading
        Row(MarqueeContainerStyle.toModifier()) {
            MarqueeColumn(allTestimonials, isUp = true, delay = 0, modifier = Column1Style.toModifier())
            MarqueeColumn(allTestimonials, isUp = false, delay = -10, modifier = Column2Style.toModifier())
            MarqueeColumn(allTestimonials, isUp = true, delay = -20, modifier = Column3Style.toModifier())
        }
    }
}

@Composable
fun MarqueeColumn(
    testimonials: List<Testimonial>,
    isUp: Boolean,
    delay: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier.then(MarqueeColumnStyle.toModifier())) {
        val animation = if (isUp) ScrollUpKeyframes else ScrollDownKeyframes
        Column(
            MarqueeContentStyle
                .toModifier()
                .animation(
                    animation.toAnimation(
                        duration = 40.s,
                        timingFunction = AnimationTimingFunction.Linear,
                        iterationCount = AnimationIterationCount.Infinite,
                        delay = delay.s,
                    ),
                ).gap(AppSpacing.S3),
        ) {
            (testimonials + testimonials).forEach { testimonial ->
                Polaroid(
                    testimonial.author,
                    testimonial.quote,
                    testimonial.imageSrc,
                    Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
