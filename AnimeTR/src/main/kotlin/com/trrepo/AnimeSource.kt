package com.trrepo

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class AnimeSource : MainAPI() {
    override var mainUrl = "https://ANIME-SITE-BURAYA.COM/"
    override var name = "AnimeTR"
    override val supportedTypes = setOf(TvType.Anime)
    override var lang = "tr"
    override val hasMainPage = true
    override val hasSearch = true

    override val mainPage = mainPageOf(
        "$mainUrl" to "Ana Sayfa",
        "$mainUrl/yeni" to "Yeni Bölümler"
    )

    override suspend fun search(query: String): List<SearchResponse> {
        // TODO: Arama sayfası HTML'i parse et
        // Örn: app.get("$mainUrl/ara?q=$query").document.select("div.anime-item").mapNotNull { it.toSearchResult() }
        return emptyList()
    }

    override suspend fun load(url: String): LoadResponse {
        // TODO: Anime detay sayfası parse et
        // val doc = app.get(url).document
        // val title = doc.select("h1").text()
        // return newAnimeLoadResponse(title, url, url) { this.addEpisodes(DubStatus.Subbed, episodes) }
        throw ErrorLoadingException("Not implemented")
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        loadExtractor(data, referer = mainUrl, subtitleCallback = subtitleCallback, callback = callback)
        return true
    }

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        // TODO: Anasayfa/listeleme sayfası parse et
        // val doc = app.get(request.data + page).document
        // val items = doc.select("div.anime-item").mapNotNull { it.toSearchResult() }
        // return newHomePageResponse(request.name, items)
        return newHomePageResponse(request.name, emptyList())
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.select("h3, .title, a[title]").firstOrNull()?.attr("title")
            ?: this.select("img").firstOrNull()?.attr("alt")
            ?: return null
        val href = this.select("a").firstOrNull()?.attr("href") ?: return null
        val posterUrl = this.select("img").firstOrNull()?.attr("src")
        return newAnimeSearchResponse(title, href, TvType.Anime) {
            this.posterUrl = posterUrl
        }
    }
}