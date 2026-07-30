package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.InputLabelStyle
import kotlin.time.Instant

@Composable
fun DateTimeSelector(
    label: String,
    instant: Instant,
    onValueChange: (Instant) -> Unit,
) {
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    Column(Modifier.fillMaxWidth().margin(bottom = AppSpacing.S2)) {
        Label(
            attrs =
                InputLabelStyle
                    .toModifier()
                    .toAttrs(),
        ) { Text(label) }
        Row(
            Modifier.gap(AppSpacing.S1),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StyledNumberInput(dateTime.year, 2024..2100, width = 80.px) { year ->
                onValueChange(
                    LocalDateTime(
                        year,
                        dateTime.month,
                        dateTime.day.coerceIn(1, YearMonth(year, dateTime.month).numberOfDays),
                        dateTime.hour,
                        dateTime.minute,
                    ).toInstant(TimeZone.currentSystemDefault()),
                )
            }
            Text("-")
            StyledNumberInput(
                dateTime.month.number,
                1..12,
                width = 64.px,
            ) { month ->
                onValueChange(
                    LocalDateTime(
                        dateTime.year,
                        month,
                        dateTime.day.coerceIn(1, YearMonth(dateTime.year, month).numberOfDays),
                        dateTime.hour,
                        dateTime.minute,
                    ).toInstant(TimeZone.currentSystemDefault()),
                )
            }
            Text("-")
            StyledNumberInput(dateTime.day, 1..31, width = 64.px) { day ->
                onValueChange(
                    LocalDateTime(
                        dateTime.year,
                        dateTime.month,
                        day.coerceIn(1, YearMonth(dateTime.year, dateTime.month).numberOfDays),
                        dateTime.hour,
                        dateTime.minute,
                    ).toInstant(TimeZone.currentSystemDefault()),
                )
            }
            Box(
                Modifier.width(
                    AppSpacing.S2,
                ),
            )
            StyledNumberInput(dateTime.hour, 0..23, width = 64.px) { hour ->
                onValueChange(
                    LocalDateTime(
                        dateTime.year,
                        dateTime.month,
                        dateTime.day,
                        hour,
                        dateTime.minute,
                    ).toInstant(TimeZone.currentSystemDefault()),
                )
            }
            Text(":")
            StyledNumberInput(dateTime.minute, 0..59, width = 64.px) { minute ->
                onValueChange(
                    LocalDateTime(
                        dateTime.year,
                        dateTime.month,
                        dateTime.day,
                        dateTime.hour,
                        minute,
                    ).toInstant(TimeZone.currentSystemDefault()),
                )
            }
        }
    }
}
