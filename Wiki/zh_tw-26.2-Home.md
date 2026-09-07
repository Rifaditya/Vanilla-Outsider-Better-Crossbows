# 🏹 Minecraft 26.2 — 更好弩 (Better Crossbows) 概覽門戶

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代碼倉庫原始碼免責聲明**：本維基文件反映了**倉庫當前的原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新開發提交或未發布功能。

---

## 🎯 歡迎來到 Minecraft 26.2 控制中心

本文件詳細闡述針對 **Minecraft 26.2** (`1.0.14+26.2`) 的 **Better Crossbows** 機制。

Minecraft 26.2 代表了 26.x 戰鬥機制的基準對齊版本，符合 Java 25 執行階段規範與穩定版 Fabric API (`0.149.0+26.2`)。Better Crossbows 將十字弓重塑為專業的**重型衝擊彈道平台**，帶來更高的箭矢初速、平直彈道與伺服端遊戲規則。

---

## 🧭 MC 26.2 子系統文件樹

探索本版本專屬子系統文件：

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|zh_tw-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|zh_tw-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|zh_tw-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|zh_tw-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 MC 26.2 建置技術規格

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

## 🌟 核心亮點與特性 (26.2)

1. **Mixin 現代化改造**：全面採用 MixinExtras `@WrapOperation` 包裝 `shootProjectile`，徹底消除脆弱的局部變數序號注入。
2. **完全沙盒自由**：遵循玩家能動性與反保姆原則，徹底解除遊戲規則上限，數值可自由設至 `Integer.MAX_VALUE`。
3. **專用伺服端加固**：單人模式邏輯使用 `@Environment(EnvType.CLIENT)` 嚴密隔離，保證 Linux 無頭伺服端執行零崩潰。
4. **創造模式物品欄快取即時重新整理**：修改遊戲規則時即時使 `CreativeModeTabs` 快取失效，無需重開世界即可重新整理附魔書。

---

## 🧭 Navigation
- [[Master Home Portal|zh_tw-Home]]
- [[Version Compatibility|zh_tw-Version-Compatibility]]
- [[Developer Setup|zh_tw-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|zh_tw-26.3-Home]]
