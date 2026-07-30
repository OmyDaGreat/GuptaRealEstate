package xyz.malefic.guptare.client.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.translate
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.unaryMinus
import org.jetbrains.compose.web.css.vh
import xyz.malefic.guptare.client.api.getTestimonials
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.components.Polaroid
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.model.Testimonial
import kotlin.math.max
import kotlin.math.sqrt
import kotlin.random.Random

@Page
@Composable
fun TestimonialsPage() {
    var testimonials by remember { mutableStateOf<List<Testimonial>?>(null) }
    val playgroundHeight = remember(testimonials?.size) { max(testimonials?.let { 15.0 * it.size } ?: 100.0, 100.0) }

    LaunchedEffect(Unit) {
        testimonials =
            getTestimonials()
    }

    Loading(testimonials) {
        val positions =
            remember(this@Loading) {
                val result = mutableListOf<Pair<Double, Double>>()
                val minDist = 16.0

                repeat(this@Loading.size) {
                    var pos: Pair<Double, Double>
                    var attempts = 0
                    do {
                        pos = Random.nextDouble(5.0, 95.0) to Random.nextDouble(5.0, 95.0)
                        attempts++
                    } while (attempts < 50 &&
                        result.any { (ox, oy) ->
                            val dx = pos.first - ox
                            val dy = pos.second - oy
                            sqrt(dx * dx + dy * dy) < minDist
                        }
                    )
                    result += pos
                }
                result
            }

        Box(
            Modifier
                .fillMaxSize()
                .minHeight(playgroundHeight.vh)
                .padding(topBottom = AppSpacing.S2)
                .position(Position.Relative),
        ) {
            this@Loading.forEachIndexed { index, testimonial ->
                val (x, y) = positions[index]
                Polaroid(
                    testimonial.author,
                    testimonial.quote,
                    testimonial.imageSrc,
                    Modifier
                        .position(Position.Absolute)
                        .left(x.percent)
                        .top(y.percent)
                        .translate(-x.percent, -y.percent),
                )
            }
        }
    }
}
