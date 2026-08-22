package com.trrepo

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class CanliSource : MainAPI() {
    override var mainUrl = "https://CANLI-TV-SITE-BURAYA.COM/"
    override var name = "CanlıTV"
    override val supportedTypes = setOf(TvType.Live)
    override var lang = "tr"
    override val hasMainPage = true
    val hasSearch = true

    override val mainPage = mainPageOf(
        "$mainUrl" to "Tüm Kanallar",
        "$mainUrl/spor" to "Spor Kanalları",
        "$mainUrl/haber" to "Haber Kanalları",
        "$mainUrl/belgesel" to "Belgesel Kanalları"
    )

    override suspend fun search(query: String): List<SearchResponse> {
        // TODO: Kanal arama parse et
        // Örn: app.get("$mainUrl/ara?q=$query").document.select("div.channel-item").mapNotNull { it.toSearchResult() }
        return emptyList()
    }

    override suspend fun load(url: String): LoadResponse {
        // TODO: Kanal detay sayfasından m3u8 / stream linki çıkar
        // val doc = app.get(url).document
        // val title = doc.select("h1").text()
        // val streamUrl = doc.select("iframe, video source, script").attr("src").also { ... regex ile m3u8 bul ... }
        // return LiveStreamLoadResponse(title, url, url, streamUrl)
        throw ErrorLoadingException("Not implemented")
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        // Canlı yayında genellikle tek link (m3u8), loadExtractor ile de çalışır
        loadExtractor(data, referer = mainUrl, subtitleCallback = subtitleCallback, callback = callback)
        return true
    }

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        // TODO: Kanal listesi sayfası parse et
        // val doc = app.get(request.data + page).document
        // val items = doc.select("div.channel-item").mapNotNull { it.toSearchResult() }
        // return newHomePageResponse(request.name, items)
        return newHomePageResponse(request.name, emptyList())
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.select("h3, .title, .channel-name, a[title]").firstOrNull()?.attr("title")
            ?: this.select("img").firstOrNull()?.attr("alt")
            ?: this.text().takeIf { it.isNotBlank() }
            ?: return null
        val href = this.select("a").firstOrNull()?.attr("href") ?: return null
        val posterUrl = this.select("img").firstOrNull()?.attr("src")
        return newLiveSearchResponse(title, href, TvType.Live) {
            this.posterUrl = posterUrl
        }
    }
}