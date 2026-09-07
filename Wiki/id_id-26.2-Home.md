# 🏹 Minecraft 26.2 — Portal Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Sumber Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini di repositori**, yang mungkin memuat komit atau fitur pengembangan yang belum dirilis di CurseForge atau Modrinth.

---

## 🎯 Selamat Datang di Pusat Dokumentasi Minecraft 26.2

Pusat dokumentasi ini mencakup **Better Crossbows** untuk **Minecraft 26.2** (`1.0.14+26.2`).

Minecraft 26.2 merupakan jangkar paritas tempur 26.x berbasis Java 25 dan Fabric API (`0.149.0+26.2`). Mengubah busur silang menjadi senjata proyektil berkecepatan tinggi dengan lintasan mendatar.

---

## 🧭 Struktur Dokumentasi Subsistem MC 26.2

Jelajahi panduan subsistem khusus versi ini:

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|id_id-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|id_id-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|id_id-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|id_id-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Spesifikasi Teknis Build MC 26.2

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.2` (`>=26.2-`) |
| **Mod Version** | `1.0.14+26.2` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.149.0+26.2` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.02.15` |
| **Subproject Source** | `Better Crossbows v26.2/better-crossbows/` |

---

## 🌟 Sorotan Utama & Pembaruan (26.2)

1. **Modernisasi Mixin**: Menerapkan MixinExtras `@WrapOperation` pada `shootProjectile` untuk stabilitas injeksi bytecode maksimal.
2. **Kebebasan Sandbox Nyata**: Menghapus batas artifisial pada GameRules hingga `Integer.MAX_VALUE` sesuai prinsip kebebasan pemain.
3. **Ketahanan Dedicated Server**: Mengisolasi penuh kode klien dengan `@Environment(EnvType.CLIENT)` untuk server Linux headless.
4. **Pembersihan Cache Tab Kreatif Seketika**: Perubahan GameRule langsung memperbarui buku sihir tanpa perlu memuat ulang dunia.

---

## 🧭 Navigation
- [[Master Home Portal|id_id-Home]]
- [[Version Compatibility|id_id-Version-Compatibility]]
- [[Developer Setup|id_id-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|id_id-26.3-Home]]
