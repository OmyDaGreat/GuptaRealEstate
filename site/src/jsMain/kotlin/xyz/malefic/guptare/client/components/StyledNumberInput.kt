package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.CSSNumericValue
import org.jetbrains.compose.web.css.CSSUnitLengthOrPercentage
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.NumberInput
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.InputStyle

@Composable
fun StyledNumberInput(
    value: Int,
    range: IntRange,
    width: CSSNumericValue<out CSSUnitLengthOrPercentage>,
    onValueChange: (Int) -> Unit,
) {
    NumberInput(
        value,
        range.first,
        range.last,
        InputStyle
            .toModifier()
            .width(width)
            .padding(topBottom = 8.px, leftRight = AppSpacing.S1)
            .toAttrs {
                onInput { it.value?.toInt()?.let { v -> onValueChange(v.coerceIn(range)) } }
            },
    )
}
