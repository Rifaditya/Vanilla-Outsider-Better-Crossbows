# ⚡ Mekanisme Tarikan Cepat (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Sumber Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini di repositori**, yang mungkin memuat komit atau fitur pengembangan yang belum dirilis di CurseForge atau Modrinth.

---

## 1. Kotak Info Teknis Resmi

| Parameter | Technical Details |
| :--- | :--- |
| **Mechanic Name** | Crossbow Tension & Reload Timing |
| **Java Mixin Implementation** | `CrossbowItemMixin.java` |
| **Target Method** | `CrossbowItem#getChargeDuration(ItemStack, LivingEntity)` |
| **Injection Point** | `@Inject(method = "getChargeDuration", at = @At("RETURN"), cancellable = true)` |
| **Controlling GameRule** | `bettercrossbows:crossbow_reload_ticks` (Default: `25` ticks / $1.25\text{s}$) |
| **Controlling Config Key** | `crossbowReloadTicks` in `config/bettercrossbows.json` |
| **Range Bounds** | `[Integer.MIN_VALUE, Integer.MAX_VALUE]` (Clamped safely at $\ge 1$ tick during evaluation) |
| **Translation Keys** | `gamerule.bettercrossbows.crossbow_reload_ticks`, `config.bettercrossbows.crossbowReloadTicks` |

---

## 2. Panduan Alur Gameplay Pemain

### Charging the Crossbow
1. **Initiate Tension**: Hold **Right-Click (Use)** while holding a crossbow in either the main hand or offhand.
2. **Audio Indicators**:
   - **Tick 0**: Initial tension start sound (`ITEM_CROSSBOW_LOADING_START`).
   - **Mid-Charge**: Secondary ratchet tension sound (`ITEM_CROSSBOW_LOADING_MIDDLE`).
   - **Completion**: High-pitch snap lock sound (`ITEM_CROSSBOW_LOADING_END`).
3. **Storage & Instant Release**: Once loaded, release the right-click button. Unlike standard bows, the loaded projectile remains primed indefinitely inside the `minecraft:charged_projectiles` data component until the player clicks again to fire.

### Quick Charge Synergy
The mod preserves complete compatibility with the vanilla **Quick Charge** enchantment (`minecraft:quick_charge`). Quick Charge dynamically reduces the tension duration while respecting the server's configured base reload time.

---

## 3. Formula Matematika & Kurva Tarikan

### A. Quick Charge Reduction Extraction
In vanilla Minecraft, the base charge duration is hardcoded to $25\text{ ticks}$ ($1.25\text{s}$), and each level of Quick Charge reduces the duration by $5\text{ ticks}$ ($0.25\text{s}$):

$$R_{\text{QC}} = 25 - T_{\text{vanilla}} = 5 \times L_{\text{QC}}$$

Where $L_{\text{QC}}$ is the level of the Quick Charge enchantment.

### B. Modified Charge Duration
Better Crossbows replaces the hardcoded base ticks with the world's live GameRule `bettercrossbows:crossbow_reload_ticks` ($T_{\text{base}}$):

$$T_{\text{charge}} = \max\left(1, T_{\text{base}} - R_{\text{QC}}\right) = \max\left(1, T_{\text{base}} - 5 \times L_{\text{QC}}\right)$$

### C. Real-Time Duration Conversion
To convert game ticks into real-world seconds (under standard $20\text{ TPS}$ conditions):

$$t_{\text{seconds}} = \frac{T_{\text{charge}}}{20.0}$$

### D. Hard Boundary Safety Guard
Even if an administrator sets `crossbow_reload_ticks` to `0` or negative numbers per the *Player Agency & Anti-Nanny Invariant*, the evaluation is clamped via `Math.max(1, ...)`:
- Guarantees the charge duration never becomes $\le 0$, which would cause division-by-zero or animation freeze in Minecraft's client rendering loops.
- At $1\text{ tick}$ ($0.05\text{s}$), the crossbow effectively becomes an **instant semi-automatic rifle**.

---

## 4. Diagram Mesin Keadaan Tarikan

```
       [ UNCHARGED STATE ]
               |
               | (Player holds Right-Click)
               v
       [ TENSION PHASE ]
       Plays: ITEM_CROSSBOW_LOADING_START
               |
               | Elapsed ticks >= (T_charge * 0.5)
               v
       [ MID-TENSION RATCHET ]
       Plays: ITEM_CROSSBOW_LOADING_MIDDLE
               |
               | Elapsed ticks >= T_charge
               v
       [ FULLY LOADED & LOCKED ]
       Plays: ITEM_CROSSBOW_LOADING_END
       Data Component: minecraft:charged_projectiles populated
               |
               +---> Released: Stored ready for combat
               +---> Fired: Projectile launched, transitions back to UNCHARGED
```

---

## 5. Skema Data SNBT Amunisi Terisi

When a crossbow completes its reload cycle, Minecraft stores the loaded ammunition inside the `charged_projectiles` data component:

```snbt
{
  "minecraft:charged_projectiles": [
    {
      "id": "minecraft:arrow",
      "count": 1
    }
  ]
}
```

For Firework Rockets:
```snbt
{
  "minecraft:charged_projectiles": [
    {
      "id": "minecraft:firework_rocket",
      "count": 1,
      "components": {
        "minecraft:fireworks": {
          "flight_duration": 1,
          "explosions": [
            {
              "shape": "small_ball",
              "colors": [11743532]
            }
          ]
        }
      }
    }
  ]
}
```

---

## 6. Matriks Referensi Tick & Waktu Pengisian

Comparison of total reload duration across various base GameRule settings and Quick Charge levels:

| Base GameRule Setting ($T_{\text{base}}$) | No Quick Charge | Quick Charge I ($-5\text{t}$) | Quick Charge II ($-10\text{t}$) | Quick Charge III ($-15\text{t}$) | Quick Charge V ($-25\text{t}$) |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **$40\text{ ticks}$ ($2.00\text{s}$)** *(Heavy Siege)* | $40\text{t}$ ($2.00\text{s}$) | $35\text{t}$ ($1.75\text{s}$) | $30\text{t}$ ($1.50\text{s}$) | $25\text{t}$ ($1.25\text{s}$) | $15\text{t}$ ($0.75\text{s}$) |
| **$25\text{ ticks}$ ($1.25\text{s}$)** *(Default)* | **$25\text{t}$ ($1.25\text{s}$)** | **$20\text{t}$ ($1.00\text{s}$)** | **$15\text{t}$ ($0.75\text{s}$)** | **$10\text{t}$ ($0.50\text{s}$)** | **$1\text{t}$ ($0.05\text{s}$)** |
| **$20\text{ ticks}$ ($1.00\text{s}$)** *(Nimble)* | $20\text{t}$ ($1.00\text{s}$) | $15\text{t}$ ($0.75\text{s}$) | $10\text{t}$ ($0.50\text{s}$) | $5\text{t}$ ($0.25\text{s}$) | $1\text{t}$ ($0.05\text{s}$)** |
| **$15\text{ ticks}$ ($0.75\text{s}$)** *(Skirmish)* | $15\text{t}$ ($0.75\text{s}$) | $10\text{t}$ ($0.50\text{s}$) | $5\text{t}$ ($0.25\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) |
| **$5\text{ ticks}$ ($0.25\text{s}$)** *(Rapid Fire)* | $5\text{t}$ ($0.25\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) |
| **$1\text{ tick}$ ($0.05\text{s}$)** *(Machine Gun)* | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) | $1\text{t}$ ($0.05\text{s}$) |

---

## 7. Kait Pengembang & Injeksi Mixin

```java
@Inject(method = "getChargeDuration", at = @At("RETURN"), cancellable = true)
private static void bettercrossbows$modifyChargeDuration(
    ItemStack crossbow, 
    LivingEntity user, 
    CallbackInfoReturnable<Integer> cir
) {
    int vanillaTicks = cir.getReturnValue();
    int baseTicks = BetterCrossbowsGameRules.getReloadTicks(user.level());
    int quickChargeReduction = 25 - vanillaTicks; // Vanilla base is 25
    cir.setReturnValue(Math.max(1, baseTicks - quickChargeReduction));
}
```

> ☕ *Catatan Pengembang Solo*: Jika Anda menyukai balistik tembakan busur silang berhantaman berat ini, dukung pengembangan saya di [Ko-fi](https://ko-fi.com/dasikigaijin)!

---

## 🔗 Halaman Terkait
- [[Heavy-Impact Ballistics|id_id-26.3-Heavy-Impact-Ballistics]]
- [[Configuration & Dynamic GameRules|id_id-26.3-Configuration-and-GameRules]]
- [[Architecture & Mixins|id_id-26.3-Architecture-and-Mixins]]
- [[Return to Overview Portal|id_id-26.3-Home]]
