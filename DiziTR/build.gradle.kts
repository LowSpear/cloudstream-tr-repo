version = 1

cloudstream {
    description = "Türkiye dizi kaynağı şablonu"
    authors = listOf("kullanici")
    status = 3
    tvTypes = listOf("TvSeries")
    language = "tr"
    requiresResources = false
    iconUrl = "https://raw.githubusercontent.com/recloudstream/extensions/master/TvSeries/TvSeriesLogo.png"
}

android {
    buildFeatures {
        buildConfig = true
    }
}