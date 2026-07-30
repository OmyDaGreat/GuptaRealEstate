package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.InputStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle

@Composable
fun AdminField(
    label: String,
    value: String,
    link: String? = null,
    onValueChange: (String) -> Unit,
) {
    Column(Modifier.fillMaxWidth().margin(bottom = AppSpacing.S2)) {
        Label(
            attrs =
                LabelMdStyle
                    .toModifier()
                    .margin(bottom = AppSpacing.S1)
                    .toAttrs(),
        ) {
            link?.let { Link(it, label) } ?: Text(label)
        }
        TextInput(
            value,
            InputStyle.toModifier().toAttrs {
                onInput { onValueChange(it.value) }
            },
        )
    }
}

@Composable
fun AdminFieldNull(
    label: String,
    value: String?,
    link: String? = null,
    onValueChange: (String?) -> Unit,
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
            link?.let { Link(it, label) } ?: Text(label)
        }
        TextInput(
            value ?: "",
            InputStyle
                .toModifier()
                .toAttrs {
                    placeholder("Optional")
                    onInput { onValueChange(it.value.ifBlank { null }) }
                },
        )
    }
}
