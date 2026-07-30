package xyz.malefic.guptare.client.styles

import org.jetbrains.compose.web.css.px

object AppSpacing {
    // Semantic
    val ContainerMax = 1280.px
    val Gutter = 24.px // 1.5 rem
    val MarginMobile = 16.px // 1 rem
    val MarginDesktop = 40.px // 2.5 rem
    val SectionGap = 80.px // 5 rem

    // 8 px scale
    val S1 = 8.px
    val S2 = 16.px
    val S3 = 24.px
    val S4 = 32.px
    val S5 = 40.px
    val S6 = 48.px
    val S8 = 64.px
    val S10 = 80.px
    val S12 = 96.px

    infix fun s(n: Int) = (8 * n).px
}
