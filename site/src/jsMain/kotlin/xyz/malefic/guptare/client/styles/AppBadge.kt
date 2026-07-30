package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px

/** "Just Listed", "Open House", "Price Reduced" — Tangerine Dream pill */
val StatusChipAccentStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(AppColors.SecondaryContainer)
                .color(AppColors.OnSecondaryContainer)
                .padding(topBottom = 4.px, leftRight = 10.px)
                .borderRadius(AppRadius.Full)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .fontWeight(500)
                .lineHeight(1.2)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
        }
    }

/** DRE numbers, info labels, featured badges — Lavender neutral pill */
val StatusChipNeutralStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(AppColors.PrimaryContainer)
                .color(AppColors.OnPrimaryContainer)
                .padding(topBottom = 4.px, leftRight = 10.px)
                .borderRadius(AppRadius.Full)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .lineHeight(1.2)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
        }
    }

/** Amenity / feature tags — Warm Olive neutral pill */
val StatusChipTertiaryStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(AppColors.TertiaryContainer)
                .color(AppColors.OnTertiaryContainer)
                .padding(topBottom = 4.px, leftRight = 10.px)
                .borderRadius(AppRadius.Full)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .lineHeight(1.2)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
        }
    }

/** Error / expired status pill */
val StatusChipErrorStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(AppColors.ErrorContainer)
                .color(AppColors.OnErrorContainer)
                .padding(topBottom = 4.px, leftRight = 10.px)
                .borderRadius(AppRadius.Full)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .fontWeight(500)
                .lineHeight(1.2)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
        }
    }
