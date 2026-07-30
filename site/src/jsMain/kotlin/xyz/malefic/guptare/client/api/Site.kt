package xyz.malefic.guptare.client.api

import xyz.malefic.guptare.client.util.getApi
import xyz.malefic.guptare.client.util.postApi
import xyz.malefic.guptare.model.SiteInfo

suspend fun getSiteSettings() = getApi<SiteInfo>("site")

suspend fun postSiteSettings(
    settings: SiteInfo,
    token: String,
) = postApi("site", settings, token)
