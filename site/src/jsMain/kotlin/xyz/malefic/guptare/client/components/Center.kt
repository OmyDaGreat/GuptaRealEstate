package xyz.malefic.guptare.client.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize

@Composable
fun Center(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        content()
    }
}
