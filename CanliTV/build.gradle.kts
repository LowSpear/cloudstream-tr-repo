version = 1

cloudstream {
    description = "Türkiye canlı TV / spor kanalları şablonu"
    authors = listOf("kullanici")
    status = 3
    tvTypes = listOf("Live")
    language = "tr"
    requiresResources = false
    iconUrl = "https://raw.githubusercontent.com/recloudstream/extensions/master/Live/LiveLogo.png"
}

android {
    buildFeatures {
        buildConfig = true
    }
}