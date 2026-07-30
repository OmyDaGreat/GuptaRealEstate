package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.focus
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

/**
 * Sticky glassmorphic nav bar.
 * Floats over page content; uses subtle bottom border as visual anchor.
 */
val NavBarStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .padding(
                    topBottom = AppSpacing.S2,
                    leftRight = AppSpacing.Gutter,
                ).then(AppModifiers.Glass)
                .position(Position.Sticky)
                .top(0.px)
                .zIndex(100)
                .borderBottom {
                    width(1.px)
                    style(LineStyle.Solid)
                    color(
                        AppColors.OutlineVariant
                            .withAlpha(0.5f),
                    )
                }
        }
    }

/** Default nav link — displays as label, highlights to Primary on hover. */
val NavLinkStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnSurface)
                .textDecorationLine(TextDecorationLine.None)
                .padding(
                    topBottom = AppSpacing.S1,
                    leftRight = AppSpacing.S2,
                ).borderRadius(AppRadius.Default)
                .then(AppModifiers.ColorTransition)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .letterSpacing(0.05.em)
        }
        hover {
            Modifier
                .color(
                    AppColors.Primary,
                ).backgroundColor(AppColors.PrimaryFixed)
        }
        focus {
            Modifier.then(AppModifiers.FocusRing)
        }
    }

/** Active / current page nav link — Tangerine Dream underline indicator. */
val NavLinkActiveStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.Secondary)
                .textDecorationLine(TextDecorationLine.None)
                .padding(
                    topBottom = AppSpacing.S1,
                    leftRight = AppSpacing.S2,
                ).borderRadius(AppRadius.Default)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .letterSpacing(0.05.em)
                .borderBottom {
                    width(2.px)
                    style(LineStyle.Solid)
                    color(AppColors.Secondary)
                }
        }
    }
