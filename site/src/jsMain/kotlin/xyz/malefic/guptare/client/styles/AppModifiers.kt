package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backdropFilter
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.outlineOffset
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

object AppModifiers {
    // ── Glassmorphism — sticky nav, floating widgets ──────────────────────────
    //    Ivory Mist at ~82 % opacity + backdrop blur
    val Glass: Modifier =
        Modifier
            .backgroundColor(
                AppColors.Background
                    .withAlpha(0.82f),
            ).backdropFilter(blur(16.px))
            .styleModifier {
                property("-webkit-backdrop-filter", "blur(16px)")
            }

    // ── Ambient neutral shadow (low-opacity) ──────────────────────────────────
    val SoftShadow: Modifier =
        Modifier
            .boxShadow(
                0.px,
                4.px,
                24.px,
                0.px,
                AppColors.OnSurface
                    .withAlpha(0.06f),
            )

    val ElevatedShadow: Modifier =
        Modifier
            .boxShadow(
                0.px,
                8.px,
                32.px,
                0.px,
                AppColors.OnSurface
                    .withAlpha(0.12f),
            )

    // ── 1 px structural border for cards / inputs ────────────────────────────
    val CardBorder: Modifier =
        Modifier
            .border(1.px, LineStyle.Solid, AppColors.Outline)

    // ── Base card surface  (Ivory Low + border + soft shadow) ─────────────────
    val Card: Modifier =
        Modifier
            .backgroundColor(AppColors.SurfaceLow)
            .borderRadius(AppRadius.Lg)
            .then(CardBorder)
            .then(SoftShadow)

    // ── Keyboard-accessible focus ring ────────────────────────────────────────
    val FocusRing: Modifier =
        Modifier
            .outline {
                width(2.px)
                style(LineStyle.Solid)
                color(AppColors.Primary)
            }.outlineOffset(2.px)

    // ── Smooth color/bg transition for interactive elements ───────────────────
    val ColorTransition: Modifier =
        Modifier.transition {
            property("color", "background-color", "border-color")
            duration(0.2.s)
            timingFunction(AnimationTimingFunction.Ease)
        }

    // ── Button-level transition (color + shadow + lift) ───────────────────────
    val ButtonTransition: Modifier =
        Modifier.transition {
            property("color", "box-shadow", "transform", "border-color")
            duration(0.2.s, 0.2.s, 0.15.s, 0.2.s)
            timingFunction(AnimationTimingFunction.Ease)
        }

    // ── Centered, max-width content wrapper ───────────────────────────────────
    val Container: Modifier =
        Modifier
            .maxWidth(AppSpacing.ContainerMax)
            .fillMaxWidth()
            .styleModifier { property("margin-inline", "auto") }
            .padding(leftRight = AppSpacing.MarginDesktop)
}

val ShowOnMdStyle =
    CssStyle {
        base { Modifier.display(DisplayStyle.None) }
        Breakpoint.MD { Modifier.display(DisplayStyle.Flex) }
    }

val HideOnMdStyle =
    CssStyle {
        base { Modifier.display(DisplayStyle.Flex) }
        Breakpoint.MD { Modifier.display(DisplayStyle.None) }
    }
