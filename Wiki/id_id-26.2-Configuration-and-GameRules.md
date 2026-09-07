# ⚙️ Konfigurasi & GameRules (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Penafian Kode Sumber Repositori**: Dokumentasi di Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

---

## 1. Kotak Info Teknis Resmi

| Parameter | Detail Teknis |
| :--- | :--- |
| **Arsitektur Sistem** | GameRules ber-namespace dinamis + JSON konfigurasi global persisten |
| **Implementasi Java** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **Implementasi GUI** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **Kategori GameRule** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **Lokasi Berkas Global** | `config/bettercrossbows.json` |
| **Namespace Perintah** | `/gamerule bettercrossbows:<rule> [value]` |
| **Invarian Kebebasan Pemain** | Rentang integer penuh tanpa batas buatan: `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. Alur Kerja Konfigurasi Langkah-demi-Langkah

### Administrasi Server Dalam Game (Perintah Brigadier)
Operator server (Tingkat Izin 2+) dapat menyesuaikan mekanika secara instan tanpa perlu memulai ulang server:

1. **Periksa Nilai Saat Ini**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **Ubah Parameter**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **Setel Ulang ke Standar**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### Konfigurasi Standar Global (`config/bettercrossbows.json`)
Saat membuat **dunia baru**, server menginisialisasi GameRules dari nilai yang ditentukan dalam `config/bettercrossbows.json`. Perubahan pada berkas JSON ini tidak menimpa dunia yang sudah ada; gunakan `/gamerule` untuk dunia aktif.

### Layar Pengaturan Klien Dalam Game (YACL + ModMenu)
Pemain dengan **ModMenu** dan **YetAnotherConfigLib (YACL v3)** dapat membuka antarmuka grafis interaktif:
1. Buka **Pengaturan** -> **Mod** -> **Better Crossbows** -> **Pengaturan (⚙️)**.
2. Sesuaikan penggeser interaktif untuk kecepatan panah, kembang api, tick pengisian, dan partikel.
3. Klik tombol donasi kreator Ko-fi opsional untuk mendukung pengembangan mandiri.

---

## 3. Batasan Matematika & Invarian Kebebasan Pemain

Sesuai dengan **Invarian Kebebasan Pemain & Anti-Nanny**, Better Crossbows **tidak pernah memaksakan batas atas permainan buatan atau batasan sewenang-wenang**:
- Semua GameRules integer didaftarkan dengan `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`.
- Jika administrator ingin anak panah melesat pada kecepatan $10.000\%$ ($100\times$) atau mengatur waktu isi ulang ke $1$ tick, mod akan mematuhinya sepenuhnya.
- Batas keamanan bawah diberlakukan secara ketat hanya jika diperlukan untuk mencegah crash JVM fatal (misalnya membatasi waktu isi ulang $\ge 1$ tick saat evaluasi matematika).

---

## 4. Prioritas Konfigurasi & Diagram Alur Siklus Hidup

```
           [ config/bettercrossbows.json ]
                         |
                         | (Read on mod startup)
                         v
           [ BetterCrossbowsConfig.load() ]
                         |
                         | (Supplies initial defaults)
                         v
           [ World Creation (Level.java) ]
                         |
                         | (GameRules initialized)
                         v
          +-------------------------------+
          |  ACTIVE LEVEL GAMERULE STATE  | <-----+ (/gamerule command)
          +-------------------------------+       |
                         |                        | (Admin modification)
                         v                        |
           [ DynamicGameRuleManager.get() ] ------+
                         |
                         +---> CrossbowItemMixin (Launch & Reload)
                         +---> AbstractArrowMixin (Gravity)
                         +---> CreativeModeTabsMixin (Tab Books)
```

---

## 5. Skema Konfigurasi JSON (`config/bettercrossbows.json`)

```json
{
  "configVersion": 1,
  "crossbowBallisticsMaxLevel": 5,
  "crossbowVelocityMultiplier": 150,
  "crossbowFireworkMultiplier": 100,
  "crossbowReloadTicks": 25,
  "crossbowEnableJuice": true
}
```

---

## 6. Tabel Referensi Lengkap GameRules

| Pengidentifikasi GameRule | Tipe | Standar | Rentang Valid | Satuan / Skala | Deskripsi |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` hingga `MAX_VALUE` | Level | Level maksimum yang dapat diperoleh untuk enchant Balistik di meja enchant, anvil, dan tab kreatif. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` hingga `MAX_VALUE` | Persen ($100 = 1.0\times$) | Pengali kecepatan yang diterapkan pada anak panah yang ditembakkan dari busur silang ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` hingga `MAX_VALUE` | Persen ($100 = 1.0\times$) | Pengali kecepatan yang diterapkan pada roket kembang api dari busur silang ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` hingga `MAX_VALUE` | Tick Game ($20\text{t} = 1\text{s}$) | Waktu dasar yang dibutuhkan untuk mengisi busur silang sebelum pengurangan Pengisian Cepat. |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | Saklar | Mengaktifkan suara dentuman sonik supersonik dan partikel gelombang kejut untuk tembakan berkecepatan tinggi. |

---

## 7. Kait Pengembang & API

### Membaca GameRules dari Mod Addon
Mod tambahan dapat menanyakan GameRules aktif langsung melalui `BetterCrossbowsGameRules`:

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// Mengembalikan pengali aktif sebagai float (misalnya 1.5f untuk 150)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// Mengembalikan tick reload aktif (misalnya 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// Memeriksa apakah efek partikel/suara aktif
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### Pendaftaran via `DynamicGameRuleManager` (DasikLibrary)
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 Halaman Terkait
- [[🚀 Balistik Benturan Berat|id_id-26.2-Heavy-Impact-Ballistics]]
- [[⚡ Mekanika Pengisian Cepat|id_id-26.2-Quick-Draw-Mechanics]]
- [[💻 Arsitektur & Mixin|id_id-26.2-Architecture-and-Mixins]]
- Kembali ke [[26.2 Portal Gambaran Umum|id_id-26.2-Home]]
