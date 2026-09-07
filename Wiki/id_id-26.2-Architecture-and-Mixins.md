# 💻 Penjelasan Arsitektur & Mixin (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Penafian Kode Sumber Repositori**: Dokumentasi di Wiki ini mencerminkan **status kode sumber saat ini di repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

---

## 1. Kotak Info Arsitektur Resmi

| Parameter | Detail Teknis |
| :--- | :--- |
| **Paket Utama** | `net.vanillaoutsider.bettercrossbows` |
| **Platform Java** | **Java 25** (`JAVA_25`) |
| **Penginisialisasi Mod** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **Penginisialisasi Klien** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Konfigurasi Mixin** | `bettercrossbows.mixins.json` |
| **Refmap Mixin** | `bettercrossbows-refmap.json` |
| **Kelas Sasaran Mixin** | 6 Kelas (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. Organisasi Paket & Prinsip "1 Berkas, 1 Tujuan"

Better Crossbows mengikuti pemisahan tanggung jawab yang ketat, memisahkan bantuan UI klien, registri otoritatif server, persistensi konfigurasi, dan mixin bytecode:

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Penginisialisasi mod & titik masuk logger SLF4J
├── client/
│   └── BetterCrossbowsClientHelper.java # Kueri dunia pemain tunggal khusus klien (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # POJO konfigurasi persisten (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # Penyedia pabrik ModMenu (@Environment)
│   └── YaclScreenHelper.java            # Pembangun layar YetAnotherConfigLib v3 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # Menyesuaikan gravitasi berdasarkan skala kecepatan
│   ├── AnvilMenuMixin.java              # Menerapkan batas level GameRule pada penggabungan anvil
│   ├── CreativeModeTabsMixin.java       # Menyaring buku sihir tab kreatif & membersihkan cache
│   ├── CrossbowItemMixin.java           # Penskalaan daya panah, waktu reload, efek partikel sonik
│   ├── EnchantmentMenuMixin.java        # Membatasi penawaran Balistik di meja enchant
│   └── ItemCombinerMenuAccessor.java    # Aksesor untuk bidang player pada penggabungan item
└── registry/
    ├── BetterCrossbowsEnchantments.java # Konstanta pengidentifikasi registri
    └── BetterCrossbowsGameRules.java    # Pendaftaran GameRules dinamis ber-namespace
```

---

## 3. Ketergantungan Subsistem & Diagram Arsitektur

```
           +---------------------------------------------+
           |         BetterCrossbows (Initializer)       |
           +---------------------------------------------+
                   |                             |
                   v                             v
+-----------------------------------+   +-----------------------------------+
|     BetterCrossbowsConfig         |   |      BetterCrossbowsGameRules     |
|   - JSON loading & saving         |   |   - DynamicGameRuleManager        |
|   - Anti-nanny bounds             |   |   - Categories & live getters     |
+-----------------------------------+   +-----------------------------------+
                   |                                     ^
                   v                                     |
+-----------------------------------+                    |
|       YaclScreenHelper (GUI)      |                    |
|   - Optional YACL v3 screens      |                    |
|   - Ko-fi creator support button  |                    |
+-----------------------------------+                    |
                                                         |
         +-----------------------------------------------+
         |
         v
+-------------------------------------------------------------------------------+
|                                MIXIN LAYER                                    |
|                                                                               |
|  [CrossbowItemMixin]       --> Scaled power launch & custom reload ticks     |
|  [AbstractArrowMixin]      --> Gravity scaling for flat trajectory arc       |
|  [AnvilMenuMixin]          --> Caps output enchantments to live GameRule     |
|  [EnchantmentMenuMixin]    --> Caps table rolls to live GameRule             |
|  [CreativeModeTabsMixin]   --> Caps books & flushes cached item parameters   |
|  [ItemCombinerMenuAccessor]--> Accesses protected player context              |
+-------------------------------------------------------------------------------+
```

---

## 4. Matriks Referensi Injeksi Mixin Lengkap

| Kelas Mixin | Kelas Sasaran Minecraft | Titik Injeksi / Metode | Tipe Injektor | Prioritas | Deskripsi |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | Mengganti durasi pengisian menggunakan `BetterCrossbowsGameRules.getReloadTicks()` dan mengurangkan reduksi Pengisian Cepat. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | Membungkus panggilan tembakan proyektil, mengalikan daya dasar dengan $M_{\text{shot}}$. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | Mengevaluasi rasio kecepatan $R > 1.2$; memicu audio dentuman sonik dan partikel gelombang kejut. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | Jika anak panah ditembakkan oleh busur silang, membagi gravitasi ($0.05$) dengan pengali kecepatan ($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | Membatasi level Balistik item keluaran ke `CROSSBOW_BALLISTICS_MAX_LEVEL` via `DynamicEnchantmentManager`. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | Mengakses bidang terlindungi `player` dari kelas induk secara aman tanpa konflik `@Shadow`. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | Membatasi level penawaran sihir Balistik di meja enchant. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | Mendeteksi perubahan GameRule saat runtime dan mengosongkan `CACHED_PARAMETERS`, memaksa pembentukan ulang tab. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | Membatasi buku Balistik level tertinggi di tab Bahan. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | Membatasi semua tingkatan buku Balistik di tab Pencarian. |

---

## 5. Sorotan Teknis & Praktik Terbaik

### A. MixinExtras `@WrapOperation` vs `@ModifyVariable` yang Rapuh
Pada versi terdahulu, daya peluncuran dimodifikasi menggunakan `@ModifyVariable` ordinal:
```java
// Injeksi warisan yang rapuh (rentan terhadap perubahan bytecode):
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
Pada rilis modern, ini telah direfaktor menggunakan MixinExtras `@WrapOperation` yang menargetkan `shootProjectile`:
```java
@WrapOperation(
    method = "performShooting",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shootProjectile(...)V")
)
private void bettercrossbows$wrapShootProjectile(..., Operation<Void> original) {
    float multiplier = bettercrossbows$getShotMultiplier(level, weapon);
    original.call(..., power * multiplier, ...);
}
```
Ini menjamin keandalan 100%, kebal terhadap pengurutan ulang variabel lokal oleh Loom atau optimasi kompilator.

### B. Protokol Isolasi Layar untuk Server Khusus (Dedicated Server)
Server khusus Minecraft tidak menyertakan kelas klien seperti `net.minecraft.client.Minecraft` atau `Screen`. Memanggil API klien dalam kode umum memicu crash fatal seketika:
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
Untuk mengeliminasi bahaya ini:
1. `ModMenuIntegration` dan `YaclScreenHelper` ditandai dengan `@Environment(EnvType.CLIENT)`.
2. `BetterCrossbowsClientHelper` mengenkapsulasi `Minecraft.getInstance().getSingleplayerServer()` di balik pelindung klien.
3. Titik masuk ModMenu menggunakan `GuiHelper.getOptionalYaclFactory(...)` dari DasikLibrary, yang menunda pemuatan kelas hingga pemanggilan GUI nyata.

---

## 🔗 Halaman Terkait
- [[🚀 Balistik Benturan Berat|id_id-26.2-Heavy-Impact-Ballistics]]
- [[⚡ Mekanika Pengisian Cepat|id_id-26.2-Quick-Draw-Mechanics]]
- [[⚙️ Konfigurasi & GameRules|id_id-26.2-Configuration-and-GameRules]]
- Kembali ke [[26.2 Portal Gambaran Umum|id_id-26.2-Home]]
