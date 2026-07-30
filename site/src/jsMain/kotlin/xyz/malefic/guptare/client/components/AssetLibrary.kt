package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement
import xyz.malefic.guptare.client.api.getAssetList
import xyz.malefic.guptare.client.api.uploadAsset
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppModifiers
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle

@Composable
fun AssetLibrary(
    token: String,
    onSelect: (String) -> Unit,
) {
    var assets by remember { mutableStateOf<List<String>?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        assets =
            getAssetList(token)
    }

    Column(
        AppModifiers.Card
            .padding(
                AppSpacing.S4,
            ).fillMaxWidth(),
    ) {
        Row(
            Modifier.fillMaxWidth().margin(bottom = AppSpacing.S3),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            H3(
                HeadlineSmStyle
                    .toModifier()
                    .toAttrs(),
            ) { Text("Image Library") }
            Box(Modifier.weight(1f))
            Button(
                Modifier
                    .onClick {
                        val input = document.createElement("input") as HTMLInputElement
                        input.type = "file"
                        input.accept = "image/*"
                        input.onchange = {
                            val file = input.files?.item(0)
                            if (file != null) {
                                scope.launch {
                                    uploadAsset(file, token)
                                    assets =
                                        getAssetList(token)
                                }
                            }
                        }
                        input.click()
                    }.toAttrs(),
            ) { Text("Upload New") }
        }

        Loading(assets) {
            if (this.isEmpty()) {
                P { Text("No images uploaded yet.") }
            } else {
                SimpleGrid(
                    numColumns(2, sm = 3, md = 4, lg = 6),
                    Modifier.gap(AppSpacing.S2),
                ) {
                    this.forEach { fileName ->
                        val url = "/assets/$fileName"
                        Column(
                            Modifier
                                .cursor(Cursor.Pointer)
                                .onClick { onSelect(url) }
                                .padding(AppSpacing.S1)
                                .border(
                                    1.px,
                                    LineStyle.Solid,
                                    AppColors.OutlineVariant,
                                ).borderRadius(8.px),
                        ) {
                            Box(Modifier.fillMaxWidth().aspectRatio(1f)) {
                                Image(
                                    url,
                                    fileName,
                                    Modifier.fillMaxSize().objectFit(ObjectFit.Cover).borderRadius(4.px),
                                )
                            }
                            P(
                                LabelSmStyle
                                    .toModifier()
                                    .margin(top = AppSpacing.S1)
                                    .maxWidth(100.px)
                                    .toAttrs(),
                            ) {
                                Text(fileName)
                            }
                        }
                    }
                }
            }
        }
    }
}
