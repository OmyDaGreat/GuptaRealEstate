package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

/**
 * Property listing card — image-centric, lifts 3 px on hover.
 * Structure: PropertyCardStyle > PropertyImageStyle + CardBodyStyle.
 */
val PropertyCardStyle =
    CssStyle {
        base {
            Modifier
                .then(AppModifiers.Card)
                .overflow(Overflow.Hidden)
                .transition {
                    property("box-shadow", "transform")
                    duration(0.25.s)
                    timingFunction(AnimationTimingFunction.Ease)
                }
        }
        hover {
            Modifier
                .then(AppModifiers.ElevatedShadow)
                .transform { translateY((-3).px) }
        }
    }

/** Property photo — fills card width, crops to 16 : 9. */
val PropertyImageStyle =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .borderRadius(AppRadius.Md)
                .aspectRatio(16, 9)
                .objectFit(ObjectFit.Cover)
                .display(DisplayStyle.Block)
        }
    }

/** Padding area below the property photo. */
val CardBodyStyle =
    CssStyle {
        base {
            Modifier.padding(
                AppSpacing.S3,
            )
        }
    }

/**
 * Generic content / info card (webinar, agent bio, feature highlight).
 * Includes padding; no image slot assumed.
 */
val ContentCardStyle =
    CssStyle {
        base {
            Modifier
                .then(AppModifiers.Card)
                .padding(AppSpacing.S4)
        }
        hover {
            Modifier.then(AppModifiers.ElevatedShadow)
        }
    }
