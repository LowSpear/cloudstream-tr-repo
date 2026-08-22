package com.trrepo

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class DiziSource : MainAPI() {
    override var mainUrl = "https://DIZI-SITE-BURAYA.COM/"
    override var name = "DiziTR"
    override val supportedTypes = setOf(TvType.TvSeries)
    override var lang = "tr"
    override val hasMainPage = true
    override val hasSearch = true

    override val mainPage = mainPageOf(
        "$mainUrl" to "Ana Sayfa",
        "$mainUrl/diziler" to "Tüm Diziler",
        "$mainUrl/yeni-bolumler" to "Yeni Bölümler"
    )

    override suspend fun search(query: String): List<SearchResponse> {
        // TODO: Arama sayfası HTML'i parse et
        // Örn: app.get("$mainUrl/ara?q=$query").document.select("div.dizi-item").mapNotNull { it.toSearchResult() }
        return emptyList()
    }

    override suspend fun load(url: String): LoadResponse {
        // TODO: Dizi detay sayfası parse et
        // val doc = app.get(url).document
        // val title = doc.select("h1").text()
        // val seasons = doc.select("ul.season li").map { it.text() }
        // return newTvSeriesLoadResponse(title, url, url) { this.addSeasons(episodesBySeason) }
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
        // TODO: Listeleme sayfası parse et
        // val doc = app.get(request.data + page).document
        // val items = doc.select("div.dizi-item").mapNotNull { it.toSearchResult() }
        // return newHomePageResponse(request.name, items)
        return newHomePageResponse(request.name, emptyList())
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.select("h3, .title, a[title]").firstOrNull()?.attr("title")
            ?: this.select("img").firstOrNull()?.attr("alt")
            ?: return null
        val href = this.select("a").firstOrNull()?.attr("href") ?: return null
        val posterUrl = this.select("img").firstOrNull()?.attr("src")
        return newTvSeriesSearchResponse(title, href, TvType.TvSeries) {
            this.posterUrl = posterUrl
        }
    }
}