# 🏹 Minecraft 26.3 — Portal Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Sumber Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini di repositori**, yang mungkin memuat komit atau fitur pengembangan yang belum dirilis di CurseForge atau Modrinth.

---

## 🎯 Selamat Datang di Pusat Dokumentasi Minecraft 26.3

Pusat dokumentasi ini mencakup **Better Crossbows** untuk **Minecraft 26.3** (`1.0.14+26.3`).

Minecraft 26.3 menghadirkan mekanika tempur modern, Java 25, dan Fabric API (`0.156.1+26.3`). Better Crossbows memperbarui busur silang dengan balistik berkecepatan tinggi, lintasan tembak mendatar, dan GameRules server yang fleksibel.

---

## 🧭 Struktur Dokumentasi Subsistem MC 26.3

Jelajahi panduan subsistem khusus versi ini:

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|id_id-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|id_id-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|id_id-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|id_id-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Spesifikasi Teknis Build MC 26.3

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.3` / `26.3-snapshot-6` (`>=26.3-`) |
| **Mod Version** | `1.0.14+26.3` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.01.22` |
| **Subproject Source** | `Better Crossbows v26.3/better-crossbows/` |

---

## 🌟 Sorotan Utama & Pembaruan (26.3)

1. **Modernisasi Mixin**: Menerapkan MixinExtras `@WrapOperation` pada `shootProjectile` untuk stabilitas injeksi bytecode maksimal.
2. **Kebebasan Sandbox Nyata**: Menghapus batas artifisial pada GameRules hingga `Integer.MAX_VALUE` sesuai prinsip kebebasan pemain.
3. **Ketahanan Dedicated Server**: Mengisolasi penuh kode klien dengan `@Environment(EnvType.CLIENT)` untuk server Linux headless.
4. **Pembersihan Cache Tab Kreatif Seketika**: Perubahan GameRule langsung memperbarui buku sihir tanpa perlu memuat ulang dunia.

---

## 🧭 Navigation
- [[Master Home Portal|id_id-Home]]
- [[Version Compatibility|id_id-Version-Compatibility]]
- [[Developer Setup|id_id-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|id_id-26.2-Home]]
