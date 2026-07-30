package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.active
import com.varabyte.kobweb.silk.style.selectors.focus
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

/**
 * Primary CTA — Tangerine Dream fill, white label.
 * Use for: "Schedule a Consultation", "Search Homes", primary form submits.
 */
val PrimaryButtonStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(AppColors.Secondary)
                .color(AppColors.OnSecondary)
                .padding(topBottom = 12.px, leftRight = AppSpacing.S3)
                .borderRadius(AppRadius.Default)
                .border(0.px, LineStyle.Solid, Colors.Transparent)
                .cursor(Cursor.Pointer)
                .then(AppModifiers.ButtonTransition)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .letterSpacing(0.05.em)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
                .gap(8.px)
        }
        hover {
            Modifier
                .backgroundColor(AppColors.SecondaryFixedDim)
                .boxShadow(
                    0.px,
                    4.px,
                    12.px,
                    0.px,
                    AppColors.Secondary
                        .withAlpha(0.20f),
                ).transform {
                    translateY((-1).px)
                }
        }
        active {
            Modifier.transform { translateY(0.px) }
        }
        focus {
            Modifier.then(AppModifiers.FocusRing)
        }
    }

/**
 * Secondary action — Blue Violet outline on transparent ground.
 * Use alongside a Primary button for secondary paths.
 */
val SecondaryButtonStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(Colors.Transparent)
                .color(AppColors.Primary)
                .padding(topBottom = 10.px, leftRight = AppSpacing.S3)
                .borderRadius(AppRadius.Default)
                .border(2.px, LineStyle.Solid, AppColors.Primary)
                .cursor(Cursor.Pointer)
                .then(AppModifiers.ButtonTransition)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .letterSpacing(0.05.em)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
                .gap(8.px)
        }
        hover {
            Modifier
                .backgroundColor(AppColors.PrimaryFixedDim)
                .color(AppColors.Primary)
                .border(2.px, LineStyle.Solid, AppColors.Primary)
        }
        focus {
            Modifier.then(AppModifiers.FocusRing)
        }
    }

/**
 * Tertiary utility action — soft Lavender fill, Blue Violet text.
 * Use for: filter chips, sort controls, secondary utilities.
 */
val TertiaryButtonStyle =
    CssStyle {
        base {
            Modifier
                .backgroundColor(AppColors.PrimaryFixed)
                .color(AppColors.Primary)
                .padding(topBottom = 12.px, leftRight = AppSpacing.S3)
                .borderRadius(AppRadius.Default)
                .border(0.px, LineStyle.Solid, Colors.Transparent)
                .cursor(Cursor.Pointer)
                .then(AppModifiers.ButtonTransition)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .letterSpacing(0.05.em)
                .display(DisplayStyle.LegacyInlineFlex)
                .alignItems(AlignItems.Center)
                .gap(8.px)
        }
        hover {
            Modifier.backgroundColor(
                AppColors.PrimaryFixedDim,
            )
        }
        focus {
            Modifier.then(AppModifiers.FocusRing)
        }
    }
