package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.bottom
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px

val FloatingContactStyle =
    CssStyle {
        base {
            Modifier
                .then(AppModifiers.Glass)
                .borderRadius(AppRadius.Xl)
                .then(AppModifiers.CardBorder)
                .padding(AppSpacing.S3)
                .boxShadow(
                    0.px,
                    8.px,
                    32.px,
                    0.px,
                    AppColors.OnSurface
                        .withAlpha(0.12f),
                ).position(Position.Fixed)
                .bottom(24.px)
                .right(24.px)
                .zIndex(90)
                .minWidth(220.px)
        }
    }
