# CloudStream Türkiye Repo Şablonu

Bu depo, CloudStream 3 için Türkçe içerik sağlayıcıları (anime, dizi, canlı TV/spor) geliştirmek ve dağıtmak amacıyla hazırlanmış bir **repo şablonudur**. Resmi [recloudstream/testplugins](https://github.com/recloudstream/testplugins) şablonundan çatallanmıştır.

> ⚠️ **Önemli:** Bu repo çalışır durumda gelen **örnek eklentiler** (AnimeTR, DiziTR, CanliTV) içerir. Bu eklentiler *boş/placeholder* kaynaklardır — gerçek siteleri çekmezler. `mainUrl` alanlarını ve `search`/`load`/`getMainPage` fonksiyonlarını hedeflediğiniz sitelerin HTML yapısına göre doldurmanız gerekir.

---

## 📦 İçerik

| Modül | Kategori | Açıklama |
|-------|----------|----------|
| `AnimeTR` | Anime | Anime arama, listeleme, bölüm linkleri |
| `DiziTR` | TV Series | Dizi arama, sezon/bölüm yapısı, linkler |
| `CanliTV` | Live (Canlı) | Spor/haber/belgesel kanalları, m3u8 yayınları |
| `ExampleProvider` | Film | Resmi şablondan kalan referans eklentisi |

---

## 🚀 Hızlı Başlangıç

### 1. Bu repoyu forklayın
GitHub'da bu repoyu kendi hesabınıza **fork**layın (Settings → Forks → "Include all branches" işaretli olmalı).

### 2. `repo.json` dosyasını düzenleyin
`repo.json` içindeki `KULLANICIADI` yerine kendi GitHub kullanıcı adınızı yazın:
```json
"pluginLists": [
  "https://raw.githubusercontent.com/KULLANICIADI/cloudstream-tr-repo/builds/plugins.json"
]
```

### 3. Eklenti kodlarını düzenleyin
Her modülün `src/main/kotlin/com/trrepo/*Source.kt` dosyasında:
- `mainUrl` = hedef sitenin ana URL'si
- `mainPage` = anasayfa/kategori URL'leri
- `search()` = arama sonuçlarını parse eden kod
- `load()` = detay sayfasından sezon/bölüm/stream linklerini çıkaran kod
- `getMainPage()` = sayfalama destekli listeleme

CSS seçicileri (Jsoup) kullanarak HTML parse edin. Yardımcı `toSearchResult()` fonksiyonu hazır.

### 4. GitHub Actions'ı etkinleştirin
Repo Settings → Actions → General → **"Allow all actions and reusable workflows"** seçili olmalı.

İlk `push` yaptığınızda workflow:
1. `./gradlew make makePluginsJson` çalıştırır
2. `.cs3` dosyalarını ve `plugins.json` üretir
3. `builds` branch'ine force-push eder

### 5. CloudStream'e ekleyin
Uygulama: **Ayarlar → Eklentiler → Depo Ekle → Depo URL'si**
```
https://raw.githubusercontent.com/KULLANICIADI/cloudstream-tr-repo/main/repo.json
```
veya kısayol (shortcode) oluşturun: repo.json raw linkini [cutt.ly](https://cutt.ly) ile kısaltın.

---

## 🛠 Yerel Derleme (İsteğe Bağlı)

Java 17 + Android SDK gerektirir:
```bash
./gradlew AnimeTR:make DiziTR:make CanliTV:make makePluginsJson
```
Üretilen `.cs3` dosyaları `build/` klasöründe, `plugins.json` kök dizinde olur.

Cihaza doğrudan yüklemek için (USB debugging açık):
```bash
./gradlew AnimeTR:deployWithAdb
```

---

## 📚 Kaynaklar

- **Eklenti geliştirme dokümantasyonu:** https://recloudstream.github.io/csdocs/devs/
- **Resmi eklenti listesi:** https://cloudstream.miraheze.org/wiki/List_of_extensions
- **CloudStream Discord:** https://discord.gg/5Hus6fM
- **API referansı (MainAPI, LoadResponse, ExtractorLink vb.):** `com.lagradost.cloudstream3` paketi

---

## ⚖️ Yasal Uyarı

Bu şablon **sadece teknik iskelet** sağlar. Hangi siteleri ekleyeceğiniz sizin sorumluluğunuzdadır. Telif hakkı korumalı içerikleri yetkisiz dağıtan kaynaklar eklemek yasal sorunlara yol açabilir. Resmi/yasal kaynakları (TRT Canlı, kanalların kendi YouTube yayını vb.) tercih edin.

---

## 📄 Lisans

Bu şablon kamu malıdır (Public Domain / Unlicense). İstediğiniz gibi kullanın, değiştirin, dağıtın.