package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.InputStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle

@Composable
fun AdminTextArea(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(Modifier.fillMaxWidth().margin(bottom = AppSpacing.S2)) {
        Label(
            attrs =
                LabelMdStyle
                    .toModifier()
                    .margin(
                        bottom = AppSpacing.S1,
                    ).toAttrs(),
        ) {
            Text(label)
        }
        TextArea(
            value,
            InputStyle
                .toModifier()
                .height(150.px)
                .borderRadius(AppRadius.Default)
                .toAttrs {
                    onInput { onValueChange(it.value) }
                },
        )
    }
}
