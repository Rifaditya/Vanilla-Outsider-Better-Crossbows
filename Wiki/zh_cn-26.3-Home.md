# 🏹 Minecraft 26.3 — 更好弩 (Better Crossbows) 概览门户

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代码仓库源码免责声明**：本维基文档反映了**仓库当前的源代码状态**，可能包含领先于 CurseForge 与 Modrinth 上公开发布版本的最新开发提交或未发布功能。

---

## 🎯 欢迎来到 Minecraft 26.3 控制中心

本文档详细阐述针对 **Minecraft 26.3** (`1.0.14+26.3`) 的 **Better Crossbows** 机制。

Minecraft 26.3 引入了现代化的战斗机制、Java 25 运行时规范以及更新的 Fabric API (`0.156.1+26.3`)。Better Crossbows 赋予弩更强的高速动能弹道、平直飞行轨迹、动态音效反馈以及服务端可配置的游戏规则。

---

## 🧭 MC 26.3 子系统文档树

探索本版本专属子系统文档：

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|zh_cn-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|zh_cn-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|zh_cn-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|zh_cn-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 MC 26.3 构建技术规格

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

## 🌟 核心亮点与特性 (26.3)

1. **Mixin 现代化改造**：全面采用 MixinExtras `@WrapOperation` 包装 `shootProjectile`，彻底消除脆弱的局部变量序号注入。
2. **完全沙盒自由**：遵循玩家能动性与反保姆原则，彻底解除游戏规则上限，数值可自由设至 `Integer.MAX_VALUE`。
3. **专用服务端加固**：单人模式逻辑使用 `@Environment(EnvType.CLIENT)` 严密隔离，保证 Linux 无头服务端运行零崩溃。
4. **创造模式物品栏缓存即时刷新**：修改游戏规则时实时使 `CreativeModeTabs` 缓存失效，无需重开世界即可刷新附魔书。

---

## 🧭 Navigation
- [[Master Home Portal|zh_cn-Home]]
- [[Version Compatibility|zh_cn-Version-Compatibility]]
- [[Developer Setup|zh_cn-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|zh_cn-26.2-Home]]
