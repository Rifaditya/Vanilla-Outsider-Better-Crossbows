# 🏹 Vanilla Outsider: Better Crossbows Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Sumber Kode Repositori**: Dokumentasi dalam Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam tahap pengembangan mendahului versi rilis publik di CurseForge dan Modrinth.

---

## 🎯 Selamat Datang di Dokumentasi Resmi

**Better Crossbows** adalah mod mekanik tempur presisi dari **Vanilla Outsider Collection**, dibuat oleh **Dasik (Rifaditya)**. Mod ini mengubah busur silang (crossbow) menjadi **Platform Proyektil Berdaya Hantam Berat**, memprioritaskan kecepatan kinetik, lintasan balistik datar, dan durasi kokang modular daripada sekadar penambahan angka kerusakan artifisial.

---

## 🧭 Portal Pemilihan Versi

Pilih versi Minecraft target Anda untuk membuka pohon dokumentasi terisolasi:

| Versi Minecraft | Rilis Mod | Toolchain Eksekusi | Status | Portal Dokumentasi Langsung |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 Aktif Utama | [[👉 Buka Wiki MC 26.3|id_id-26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 Paritas | [[👉 Buka Wiki MC 26.2|id_id-26.2-Home]] |

> [!NOTE]
> Berdasarkan kebijakan **1 Jar 1 Version Policy**, setiap versi dibangun sebagai artefak mandiri yang berdaulat.

---

## 🌟 Ringkasan Subsistem Utama

- **[[Balistik Hantaman Berat|id_id-26.3-Heavy-Impact-Ballistics]]**:
  - Kecepatan awal panah meningkat secara bawaan sebesar **1.5×** ($150\%$).
  - Kelandaian lintasan dinamis: gravitasi panah berkurang secara proporsional terhadap kecepatan ($g_{\text{eff}} = g_0 / M$).
  - **Sihir Balistik (`bettercrossbows:ballistics`)**: Menambahkan +25% kecepatan per level (hingga +125% di Level V); eksklusif dan tidak dapat digabung dengan Multishot.
  - Efek ledakan sonik (sonic crack) dan gelombang partikel kejut.
- **[[Mekanik Tarikan Cepat|id_id-26.3-Quick-Draw-Mechanics]]**:
  - Waktu kokang dalam tick yang dapat disesuaikan melalui GameRule, kompatibel penuh dengan sihir Quick Charge.
- **[[Konfigurasi & GameRules|id_id-26.3-Configuration-and-GameRules]]**:
  - GameRules dinamis berbasis DasikLibrary (`bettercrossbows:better_crossbows`).
  - Rentang angka integer terbuka penuh (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`) menjunjung tinggi kebebasan pemain dan prinsip Anti-Nanny.
  - Menu GUI klien opsional berbasis YACL v3.
- **[[Arsitektur & Uraian Mixin|id_id-26.3-Architecture-and-Mixins]]**:
  - Dokumentasi teknis titik injeksi `@Mixin` di `CrossbowItemMixin`, `AbstractArrowMixin`, dan lainnya.

---

## 📜 Kredit dan Lisensi

- **Pembuat**: **Dasik (Rifaditya)**
- **Lisensi**: **GNU General Public License v3.0 (GPLv3)**
- **Kode Sumber**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
- **Dukungan Lokal**: [Saweria](https://saweria.co/DasikIgaijinn) | [SocioBuzz](https://sociabuzz.com/dasikigaijin/tribe)
