package xyz.malefic.guptare.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.await

private const val DEPLOY_VERSION_STORAGE_KEY = "deploy_version"
private const val DEPLOY_REFRESH_HANDLED_KEY = "deploy_refresh_handled_for"

private suspend fun refreshIfDeployChanged() {
    val response = window.fetch("/api/version").await()
    if (!response.ok) return
    val currentVersion = response.text().await().trim()
    if (currentVersion.isBlank() || currentVersion == "unknown") return

    val previousVersion = localStorage.getItem(DEPLOY_VERSION_STORAGE_KEY)
    if (previousVersion == null) {
        localStorage.setItem(DEPLOY_VERSION_STORAGE_KEY, currentVersion)
        return
    }

    if (previousVersion == currentVersion) return
    val handledVersion = localStorage.getItem(DEPLOY_REFRESH_HANDLED_KEY)
    if (handledVersion == currentVersion) return

    window.navigator.serviceWorker.getRegistrations().await().forEach { registration ->
        registration.unregister()
    }
    window.caches.keys().await().forEach { cacheName ->
        window.caches.delete(cacheName).await()
    }

    localStorage.setItem(DEPLOY_VERSION_STORAGE_KEY, currentVersion)
    localStorage.setItem(DEPLOY_REFRESH_HANDLED_KEY, currentVersion)
    window.location.reload()
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        refreshIfDeployChanged()
    }

    SilkApp {
        Surface(SmoothColorStyle.toModifier().fillMaxSize()) {
            content()
        }
    }
}
