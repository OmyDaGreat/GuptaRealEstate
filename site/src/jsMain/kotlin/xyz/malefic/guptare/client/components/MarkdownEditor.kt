package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.toAttrs
import js.objects.unsafeJso
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Div
import xyz.malefic.guptare.client.util.js.Editor
import kotlin.random.Random

@Composable
fun MarkdownEditor(
    initialValue: String = "",
    height: String = "500px",
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val mountId = remember { "tui-editor-${(Random.nextDouble() * 1_000_000).toInt()}" }
    var editor by remember { mutableStateOf<Editor?>(null) }

    Div(attrs = modifier.id(mountId).toAttrs())

    LaunchedEffect(mountId) {
        val el = document.getElementById(mountId) ?: return@LaunchedEffect
        editor =
            Editor(
                unsafeJso {
                    this.el = el
                    this.initialEditType = "wysiwyg"
                    this.initialValue = initialValue
                    this.height = height
                    this.toolbarItems =
                        arrayOf(
                            arrayOf("heading", "bold", "italic", "strike"),
                            arrayOf("hr", "quote"),
                            arrayOf("ul", "ol"),
                            arrayOf("image", "link"),
                        )
                    this.events =
                        unsafeJso {
                            this.change = {
                                editor?.let { onChanged(it.getMarkdown()) }
                            }
                        }
                },
            )
    }

    DisposableEffect(Unit) {
        onDispose { editor?.destroy() }
    }
}
