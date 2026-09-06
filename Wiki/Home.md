# 🏹 Vanilla Outsider: Better Crossbows Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 🎯 Welcome to the Official Documentation

**Better Crossbows** is a precision combat and mechanics mod in the **Vanilla Outsider Collection**, engineered by **Dasik (Rifaditya)**. It redesigns the crossbow into a dedicated **Heavy-Impact Projectile Platform**, prioritizing kinetic velocity, flattened ballistic arcs, and modular tension over arbitrary damage inflation.

Whether you are a sniper calibrating long-distance projectile drop in Survival or an addon developer hooking into custom projectile velocities, this wiki provides internet-grade encyclopedic coverage of mechanics, mathematical formulas, and internal architectures.

---

## 🧭 Active Version Selector Portal

Select your targeted Minecraft version to access the isolated documentation tree:

| Target Minecraft Version | Mod Release Anchor | Runtime Toolchain | Status | Direct Documentation Portal |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 Active Current | [[👉 Enter MC 26.3 Wiki Portal|26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 Parity Anchor | [[👉 Enter MC 26.2 Wiki Portal|26.2-Home]] |

> [!NOTE]
> Under the project's **1 Jar 1 Version Policy**, each version branch is built as a self-contained sovereign artifact with dedicated dependency mappings.

---

## 🌟 Core Subsystem Overviews

```
                  +-----------------------------------+
                  |         CROSSBOW PLATFORM         |
                  +-----------------------------------+
                                    |
          +-------------------------+-------------------------+
          |                                                   |
          v                                                   v
+-------------------+                               +-------------------+
|  HEAVY BALLISTICS |                               |    QUICK DRAW     |
|  - 1.5x Velocity  |                               |  - Custom Ticks   |
|  - Arc Flattening |                               |  - Quick Charge   |
|  - Sonic Juice    |                               |  - Tension Curves |
+-------------------+                               +-------------------+
          |                                                   |
          +-------------------------+-------------------------+
                                    |
                                    v
                  +-----------------------------------+
                  |     DYNAMIC GAMERULES & ENGINE    |
                  |     - Server-side Live Tuning     |
                  |     - YACL v3 Client Config GUI   |
                  +-----------------------------------+
```

### 1. [[Heavy-Impact Ballistics|26.3-Heavy-Impact-Ballistics]]
- **Kinetic Velocity Multiplier**: Crossbow arrows fire with a baseline **1.5× launch velocity** ($150\%$), substantially extending effective range.
- **Dynamic Arc Flattening**: Arrow gravity dynamically scales down as velocity increases ($g_{\text{eff}} = g_0 / M$), creating a realistic, flat sniper trajectory.
- **Ballistics Enchantment (`bettercrossbows:ballistics`)**: A specialized, non-stacking enchantment mutually exclusive with Multishot. Grants **+25% velocity per level** (up to +125% at Level V).
- **Sonic Juice Visual & Acoustic Feedback**: High-velocity projectiles trigger deep sonic boom cracks (`FIREWORK_ROCKET_BLAST_FAR`) and directional cloud/gust shockwave particle cones.

### 2. [[Quick Draw Mechanics|26.3-Quick-Draw-Mechanics]]
- **Modular Reload Ticks**: Replace hardcoded reload times with server-tunable tick thresholds.
- **Quick Charge Integration**: Fully preserves vanilla Quick Charge decrements without clipping or negative integer freezing.

### 3. [[Configuration & Dynamic GameRules|26.3-Configuration-and-GameRules]]
- **Dynamic In-Game GameRules**: Powered by DasikLibrary's `DynamicGameRuleManager` under category `bettercrossbows:better_crossbows`.
- **Player Agency & Anti-Nanny Invariant**: Full unrestricted integer ranges (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`), giving server administrators absolute sandbox control.
- **Optional Client GUI**: Seamless integration with ModMenu and YetAnotherConfigLib (YACL v3) with zero server classloading overhead.

### 4. [[Architecture & Mixins Breakdown|26.3-Architecture-and-Mixins]]
- Detailed documentation of `@Mixin` injection targets: `CrossbowItemMixin`, `AbstractArrowMixin`, `AnvilMenuMixin`, `CreativeModeTabsMixin`, `EnchantmentMenuMixin`, and `ItemCombinerMenuAccessor`.

---

## 📚 General Documentation & Developer Reference

- [[Version Compatibility & Toolchain Matrix|Version-Compatibility]]: Exhaustive breakdown of Loom, Fabric Loader, Parchment, and Java version requirements.
- [[Developer Setup & Building Guide|Developer-Setup-and-Building]]: Instructions for compiling the repository, setting up Gradle, and hooking into DasikLibrary.

---

## 📜 Credits & Licensing

- **Author & Maintainer**: **Dasik (Rifaditya)**
- **License**: **GNU General Public License v3.0 (GPLv3)**
- **Modrinth Repository**: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows)
- **Source Code**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
