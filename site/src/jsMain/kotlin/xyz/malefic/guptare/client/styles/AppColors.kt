package xyz.malefic.guptare.client.styles

import com.varabyte.kobweb.compose.ui.graphics.Colors
import xyz.malefic.kutint.kutint
import xyz.malefic.kutint.parseHex

object AppColors {
    // ── Surfaces (Ivory Mist family) ─────────────────────────────────────────
    val Surface = parseHex("#fdf9e9")
    val SurfaceDim = parseHex("#dedacb")
    val SurfaceBright = parseHex("#fdf9e9")
    val SurfaceLowest = parseHex("#ffffff")
    val SurfaceLow = parseHex("#f8f4e4")
    val SurfaceContainer = parseHex("#f2eede")
    val SurfaceHigh = parseHex("#ece8d9")
    val SurfaceHighest = parseHex("#e6e3d3")

    // ── Text ─────────────────────────────────────────────────────────────────
    val OnSurface = parseHex("#1c1c13") // Deep Text — default
    val OnSurfaceVariant = parseHex("#4b4453")

    // ── Inverse ──────────────────────────────────────────────────────────────
    val InverseSurface = parseHex("#323126")
    val InverseOnSurface = parseHex("#f5f1e1")

    // ── Outline / Dividers ───────────────────────────────────────────────────
    val Outline = parseHex("#7c7484")
    val OutlineVariant = parseHex("#cdc3d5")

    // ── Primary — Blue Violet ────────────────────────────────────────────────
    val Primary = parseHex("#682db9")
    val OnPrimary = parseHex("#ffffff")
    val PrimaryContainer = parseHex("#ecdcff")
    val OnPrimaryContainer = parseHex("#280057")
    val InversePrimary = parseHex("#d6baff")
    val PrimaryFixed = PrimaryContainer
    val PrimaryFixedDim = parseHex("#d6baff")
    val OnPrimaryFixed = OnPrimaryContainer
    val OnPrimaryFixedVariant = parseHex("#5c1ead")

    // ── Secondary — Burnt Sienna (primary CTA) ────────────────────────────
    val Secondary = parseHex("#904c33")
    val OnSecondary = parseHex("#ffffff")
    val SecondaryContainer = parseHex("#fea687")
    val OnSecondaryContainer = parseHex("#783922")
    val SecondaryFixed = parseHex("#ffdbcf")
    val SecondaryFixedDim = parseHex("#ffb59c")
    val OnSecondaryFixed = parseHex("#390c00")
    val OnSecondaryFixedVariant = parseHex("#73351e")

    // ── Tertiary — Warm Olive ────────────────────────────────────────────────
    val Tertiary = parseHex("#665f39")
    val OnTertiary = parseHex("#ffffff")
    val TertiaryContainer = parseHex("#b6ac7f")
    val OnTertiaryContainer = parseHex("#46401d")
    val TertiaryFixed = parseHex("#eee3b3")
    val TertiaryFixedDim = parseHex("#d2c799")
    val OnTertiaryFixed = parseHex("#211c00")
    val OnTertiaryFixedVariant = parseHex("#4e4723")

    // ── Error ────────────────────────────────────────────────────────────────
    val Error = parseHex("#ba1a1a")
    val OnError = parseHex("#ffffff")
    val ErrorContainer = parseHex("#ffdad6")
    val OnErrorContainer = parseHex("#93000a")

    // ── Background / Root ────────────────────────────────────────────────────
    val Background = Colors.White.kutint
    val OnBackground = parseHex("#1c1c13")
    val SurfaceVariant = parseHex("#e6e3d3")
}
