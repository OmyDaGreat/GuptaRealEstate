package xyz.malefic.guptare.client.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.flexDirection
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getTestimonials
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.ContentCardStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import xyz.malefic.guptare.model.Testimonial
import kotlin.time.Duration.Companion.milliseconds

val MarqueeContainerStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Row)
                .gap(AppSpacing.S3)
                .padding(topBottom = AppSpacing.S2)
        }
    }

val MarqueeColumnStyle =
    CssStyle {
        base {
            Modifier
                .flex(1)
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Column)
                .padding(leftRight = AppSpacing.S2)
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

    LaunchedEffect(testimonials) {
        if (testimonials == null) return@LaunchedEffect

        while (true) {
            delay(16.milliseconds)
            val documentHeight = document.documentElement?.scrollHeight ?: 0
            val viewportHeight = window.innerHeight

            if (documentHeight <= viewportHeight) continue
            if (window.scrollY + viewportHeight >= documentHeight - 1) {
                window.scrollTo(0.0, 0.0)
            } else {
                window.scrollBy(0.0, 1.0)
            }
        }
    }

    Loading(testimonials) {
        val allTestimonials = this@Loading
        Row(MarqueeContainerStyle.toModifier()) {
            MarqueeColumn(allTestimonials, modifier = Column1Style.toModifier())
            MarqueeColumn(allTestimonials, modifier = Column2Style.toModifier())
            MarqueeColumn(allTestimonials, modifier = Column3Style.toModifier())
        }
    }
}

@Composable
fun MarqueeColumn(
    testimonials: List<Testimonial>,
    modifier: Modifier = Modifier,
) {
    Column(modifier.then(MarqueeColumnStyle.toModifier())) {
        Column(Modifier.minHeight(100.vh).gap(AppSpacing.S3)) {
            (testimonials + testimonials).forEach { testimonial ->
                Column(ContentCardStyle.toModifier()) {
                    Span(BodyMdStyle.toModifier().toAttrs()) { Text(testimonial.quote) }
                    Span(LabelSmStyle.toModifier().toAttrs()) { Text(" ") }
                    Span(LabelSmStyle.toModifier().toAttrs()) { Text(" ") }
                    Span(LabelSmStyle.toModifier().toAttrs()) { Text(testimonial.author) }
                }
            }
        }
    }
}
