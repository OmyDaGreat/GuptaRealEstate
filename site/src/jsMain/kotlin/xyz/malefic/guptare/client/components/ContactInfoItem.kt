package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.BodyMdStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle

@Composable
fun ContactInfoItem(
    label: String,
    value: String,
    icon: @Composable (Modifier) -> Unit,
) {
    Row(Modifier.gap(12.px), verticalAlignment = Alignment.Top) {
        icon(
            Modifier
                .color(
                    AppColors.Secondary,
                ).translateY(25.percent),
        )
        Column {
            Span(
                LabelMdStyle
                    .toAttrs(),
            ) { Text(label) }
            Span(
                BodyMdStyle
                    .toModifier()
                    .color(
                        AppColors.OnSurfaceVariant,
                    ).toAttrs(),
            ) {
                Text(value)
            }
        }
    }
}
