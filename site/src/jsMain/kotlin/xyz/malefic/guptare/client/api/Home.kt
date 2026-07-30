package xyz.malefic.guptare.client.api

import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyOf
import kotlinx.browser.window
import org.w3c.files.File
import org.w3c.xhr.FormData
import xyz.malefic.guptare.client.util.getApi
import xyz.malefic.guptare.client.util.getApiList
import xyz.malefic.guptare.client.util.postApi
import xyz.malefic.guptare.model.HomeInfo

suspend fun getHomeInfo() = getApi<HomeInfo>("home")

suspend fun postHomeSettings(
    settings: HomeInfo,
    token: String,
) = postApi("home", settings, token)

suspend fun getAssetList(token: String) = getApiList<String>("assets", token)

suspend fun uploadAsset(
    file: File,
    token: String,
) {
    val formData = FormData()
    formData.append("file", file)

    window.api.post("assets", bodyOf(formData), mapOf("Authorization" to "Bearer $token"))
}
