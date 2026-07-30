package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.BoxSizing
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.boxSizing
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@InitSilk
fun initSerenHearth(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyle("html, body") {
        base { Modifier.height(100.percent).margin(0.px) }
    }

    ctx.stylesheet.registerStyle("body") {
        base {
            Modifier
                .backgroundColor(AppColors.Background)
                .color(AppColors.OnBackground)
                .fontFamily(*AppFonts.BODY_STACK)
                .fontSize(16.px)
                .lineHeight(1.6)
                .styleModifier {
                    property("-webkit-font-smoothing", "antialiased")
                    property("-moz-osx-font-smoothing", "grayscale")
                }
        }
    }

    ctx.stylesheet.registerStyle("*, *::before, *::after") {
        base {
            Modifier.boxSizing(BoxSizing.BorderBox)
        }
    }

    ctx.stylesheet.registerStyle("img, video") {
        base {
            Modifier.maxWidth(100.percent).display(DisplayStyle.Block)
        }
    }
}
