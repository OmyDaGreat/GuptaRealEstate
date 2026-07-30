package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.toAttrs
import xyz.malefic.guptare.client.styles.AppRadius

@Composable
fun Iframe(
    src: String,
    modifier: Modifier = Modifier,
) = org.jetbrains.compose.web.dom.Iframe(
    Modifier
        .borderRadius(AppRadius.Lg)
        .overflow(Overflow.Hidden)
        .then(modifier)
        .toAttrs {
            attr("src", src)
            attr("frameborder", "0")
            attr("scrolling", "no")
            attr("allowtransparency", "true")
            attr("allow", "encrypted-media")
        },
)
