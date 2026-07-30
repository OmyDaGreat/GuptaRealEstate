package xyz.malefic.guptare.client.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun Instant.toDisplayString(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val dt = toLocalDateTime(timeZone)

    val dayName =
        dt.dayOfWeek.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }
    val monthName =
        dt.month.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }

    val day = dt.day
    val ordinal =
        when {
            day % 100 in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }

    val hour = dt.hour
    val minute = dt.minute
    val amPm = if (hour < 12) "AM" else "PM"
    val displayHour = if (hour % 12 == 0) 12 else hour % 12
    val displayMinute = minute.toString().padStart(2, '0')

    return "$dayName, $monthName $day$ordinal at $displayHour:$displayMinute $amPm"
}
