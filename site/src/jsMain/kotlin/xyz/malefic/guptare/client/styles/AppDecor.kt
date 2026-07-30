package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.filter
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px

/** Horizontal rule using the Lavender outline-variant color. */
val DividerStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .height(1.px)
                .backgroundColor(AppColors.OutlineVariant)
                .styleModifier { property("border", "none") }
        }
    }

/**
 * Decorative "blob" accent shape.
 * Position absolutely behind hero text or images; keep z-index below content.
 * Color via backgroundColor(AppColors.PrimaryFixed) or SecondaryFixed.
 */
val BlobAccentStyle =
    CssStyle {
        base {
            Modifier
                .styleModifier {
                    property("border-radius", "60% 40% 30% 70% / 60% 30% 70% 40%")
                }.filter(blur(40.px))
                .opacity(0.45)
                .position(Position.Absolute)
                .pointerEvents(PointerEvents.None)
                .zIndex(0)
        }
    }
