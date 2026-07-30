package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.focus
import com.varabyte.kobweb.silk.style.selectors.placeholder
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

/**
 * Standard text input / select / textarea.
 * Ivory Mist background, Lavender border → Blue Violet on focus.
 */
val InputStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .backgroundColor(AppColors.SurfaceLowest)
                .color(AppColors.OnSurface)
                .padding(topBottom = 12.px, leftRight = AppSpacing.S2)
                .borderRadius(AppRadius.Default)
                .border(1.px, LineStyle.Solid, AppColors.Outline)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(16.px)
                .fontWeight(400)
                .lineHeight(1.6)
                .styleModifier {
                    property("outline", "none")
                }.transition {
                    property("border-color", "box-shadow")
                    duration(0.2.s)
                    timingFunction(AnimationTimingFunction.Ease)
                }
        }
        focus {
            Modifier
                .border(1.px, LineStyle.Solid, AppColors.Primary)
                .boxShadow(
                    0.px,
                    0.px,
                    0.px,
                    3.px,
                    AppColors.Primary
                        .withAlpha(0.12f),
                )
        }
        placeholder {
            Modifier.color(
                AppColors.OnSurfaceVariant,
            )
        }
    }

/**
 * Hero search bar — pill-shaped, 18 px text, primary action for property search.
 * Add a magnifier icon as a sibling; reserve 48 px left padding in your layout.
 */
val SearchInputStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .backgroundColor(AppColors.SurfaceLowest)
                .color(AppColors.OnSurface)
                .padding(
                    topBottom = AppSpacing.S2,
                    leftRight = AppSpacing.S4,
                ).borderRadius(AppRadius.Full)
                .border(1.px, LineStyle.Solid, AppColors.Outline)
                .then(AppModifiers.SoftShadow)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(18.px)
                .fontWeight(400)
                .lineHeight(1.6)
                .styleModifier {
                    property("outline", "none")
                }.transition {
                    property("border-color", "box-shadow")
                    duration(0.2.s)
                    timingFunction(AnimationTimingFunction.Ease)
                }
        }
        focus {
            Modifier
                .border(1.px, LineStyle.Solid, AppColors.Primary)
                .boxShadow(
                    0.px,
                    0.px,
                    0.px,
                    4.px,
                    AppColors.Primary
                        .withAlpha(0.14f),
                )
        }
        placeholder {
            Modifier.color(
                AppColors.OnSurfaceVariant,
            )
        }
    }

/** Label above an input field. */
val InputLabelStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnSurfaceVariant)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .letterSpacing(0.05.em)
                .display(DisplayStyle.Block)
                .margin(bottom = 6.px)
        }
    }

/** Inline helper / error text beneath an input field. */
val InputHelperStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.Outline)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .fontWeight(400)
                .margin(top = 4.px)
                .display(DisplayStyle.Block)
        }
    }

val InputErrorStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.Error)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .fontWeight(500)
                .margin(top = 4.px)
                .display(DisplayStyle.Block)
        }
    }
