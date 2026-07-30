package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.autoLength
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint

/**
 * Full-bleed page section with 80 px vertical breathing room.
 * Wrap every logical page section (hero, listings, about, etc.) with this.
 */
val SectionStyle =
    CssStyle {
        base {
            Modifier.fillMaxWidth().padding(topBottom = AppSpacing.Gutter)
        }
    }

/**
 * Centered max-width wrapper — 16 px horizontal margin on mobile,
 * 40 px on desktop. Apply inside SectionStyle.
 */
val ContainerStyle =
    CssStyle {
        base {
            Modifier
                .maxWidth(AppSpacing.ContainerMax)
                .fillMaxWidth()
                .marginInline(autoLength)
                .padding(leftRight = AppSpacing.MarginMobile)
        }
        Breakpoint.LG {
            Modifier.padding(leftRight = AppSpacing.MarginDesktop)
        }
    }

/** Alternating section wash — Lavender tint for variety between sections. */
val SectionLavenderStyle =
    CssStyle {
        base {
            Modifier.backgroundColor(
                AppColors.PrimaryFixed,
            )
        }
    }

/** Alternating section wash — warm Ivory Container tint. */
val SectionWarmStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .padding(
                    topBottom = AppSpacing.SectionGap,
                ).backgroundColor(AppColors.SurfaceContainer)
        }
    }

/** Recessed section wash — muted Ivory Dim tint for footers. */
val SectionDimStyle =
    CssStyle {
        base {
            Modifier.backgroundColor(
                AppColors.SurfaceDim,
            )
        }
    }

/** Dark inverse surface section. */
val SectionInverseStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .backgroundColor(AppColors.InverseSurface)
                .color(AppColors.InverseOnSurface)
        }
    }
