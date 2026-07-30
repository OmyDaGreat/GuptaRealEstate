package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

/** 36 px mobile → 48 px desktop / Bold / Cinzel — hero headings */
val DisplayLgStyle =
    CssStyle {
        base {
            Modifier
                .color(
                    AppColors.OnBackground,
                ).fontFamily(*AppFonts.DISPLAY_STACK)
                .fontSize(36.px)
                .fontWeight(700)
                .lineHeight(1.2)
                .letterSpacing((-0.01).em)
        }
        Breakpoint.MD {
            Modifier.fontSize(48.px).letterSpacing((-0.02).em)
        }
    }

/** 26 px mobile → 32 px desktop / SemiBold / Cinzel — section headings */
val HeadlineMdStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnBackground)
                .fontFamily(*AppFonts.DISPLAY_STACK)
                .fontSize(26.px)
                .fontWeight(600)
                .lineHeight(1.3)
        }
        Breakpoint.MD {
            Modifier.fontSize(32.px)
        }
    }

/** 24 px / SemiBold / Cinzel — card headings, subsections */
val HeadlineSmStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnBackground)
                .fontFamily(*AppFonts.DISPLAY_STACK)
                .fontSize(24.px)
                .fontWeight(600)
                .lineHeight(1.4)
        }
    }

/** 18 px / Regular / Plus Jakarta Sans — editorial / hero body */
val BodyLgStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnSurface)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(18.px)
                .fontWeight(400)
                .lineHeight(1.6)
        }
    }

/** 16 px / Regular / Plus Jakarta Sans — default body text */
val BodyMdStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnSurface)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(16.px)
                .fontWeight(400)
                .lineHeight(1.6)
        }
    }

/** 14 px / SemiBold / Plus Jakarta Sans — nav links, button text, labels */
val LabelMdStyle =
    CssStyle {
        base {
            Modifier
                .color(
                    AppColors.OnSurface,
                ).fontFamily(*AppFonts.BODY_STACK)
                .fontSize(14.px)
                .fontWeight(600)
                .lineHeight(1.2)
                .letterSpacing(0.05.em)
        }
    }

/** 12 px / Medium / Plus Jakarta Sans — status chips, DRE numbers, fine print */
val LabelSmStyle =
    CssStyle {
        base {
            Modifier
                .color(AppColors.OnSurface)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(12.px)
                .fontWeight(500)
                .lineHeight(1.2)
        }
    }
