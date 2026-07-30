package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.graphics.Image

@Composable
fun Logo(
    modifier: Modifier,
    url: String? = null,
) = Image(url ?: "/Logo.jpg", "Logo", modifier)
