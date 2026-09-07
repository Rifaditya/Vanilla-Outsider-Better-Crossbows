# 📋 Kompatibilitas Versi & Matriks Toolchain

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Sumber Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini di repositori**, yang mungkin memuat komit atau fitur pengembangan yang belum dirilis di CurseForge atau Modrinth.

---

## 🏛️ Kebijakan «1 Jar 1 Versi» (1 Jar 1 Version Policy)

**Better Crossbows** menerapkan **Kebijakan 1 Jar 1 Versi** secara ketat. Setiap versi target Minecraft diisolasi dalam subproyek independen dengan pustaka dan artefak build mandiri:
- **Bebas Jar Universal Frankenstein**: Menghindari refleksi bytecode runtime yang rentan, setiap versi memiliki build terverifikasi.
- **Rilis Khusus**: Berkas mod dirilis per versi target (contoh: `better-crossbows-1.0.14+26.3.jar`).
- **Pengarsipan**: Build yang selesai diarsipkan di folder `Archive Jar of all versions/`.

---

## 📊 Matriks Kompatibilitas Teknis Lengkap

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Aktif Utama** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Jangkar Paritas** |

---

## 🧩 Dependensi Opsional & Rekomendasi

Better Crossbows tidak membutuhkan dependensi klien wajib selain Fabric API dan DasikLibrary. Namun, mod opsional berikut membuka layar pengaturan grafis interaktif:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Arsitektur Keamanan Sisi Klien & Server

Seluruh kode GUI klien dilindungi oleh **Protokol Isolasi Layar (Screen Isolation Protocol)**:
- `ModMenuIntegration` dan `YaclScreenHelper` dianotasi secara eksplisit dengan `@Environment(EnvType.CLIENT)`.
- Titik masuk dimuat melalui `GuiHelper.getOptionalYaclFactory(...)` dari DasikLibrary.
- Menjalankan mod di **Dedicated Server** dijamin 100% bebas dari galat `ClassNotFoundException` atau `NoClassDefFoundError: net/minecraft/client/Minecraft`.

---

## 🗄️ Riwayat Build & Berkas Arsip

Berkas JAR terarsip untuk versi terdahulu tersimpan di:
- `Archive Jar of all versions/`
- Direktori rilis subproyek lokal: `<Subproject>/releases/`
- Rilis resmi di Modrinth: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 Navigasi

- Kembali ke [[Portal Beranda|id_id-Home]]
- [[MC 26.3 Overview|id_id-26.3-Home]]
- [[MC 26.2 Overview|id_id-26.2-Home]]
- [[Developer Setup|id_id-Developer-Setup-and-Building]]
