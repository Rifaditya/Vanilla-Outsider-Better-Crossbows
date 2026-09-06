# 🏹 Vanilla Outsider: 更好弩 (Better Crossbows) 官方维基

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代码仓库源码免责声明**：本维基文档反映了**仓库当前的源代码状态**，可能包含领先于 CurseForge 与 Modrinth 上公开发布版本的最新开发提交或未发布功能。

---

## 🎯 欢迎来到官方文档

**Better Crossbows** 是 **Vanilla Outsider 系列** 中的一款精准战斗与射击机制模组，由 **Dasik (Rifaditya)** 打造。该模组将弩重塑为专业的**重型弹道发射平台**，专注于弹丸动能速度、平直弹道下坠以及模块化装填拉弦时间，而非单纯堆砌数值伤害。

无论你是在生存模式中校准远距离弹道落点的神射手，还是希望接入发射速度倍率的附属模组开发者，本文档均提供详尽的技术细节、数学公式与内部架构说明。

---

## 🧭 活动版本选择传送门

请选择你的目标 Minecraft 版本以进入对应独立文档树：

| 目标 Minecraft 版本 | 模组发行定位 | 运行时工具链 | 状态 | 文档传送入口 |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 当前主版本 | [[👉 进入 MC 26.3 维基|26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 对齐版本 | [[👉 进入 MC 26.2 维基|26.2-Home]] |

> [!NOTE]
> 遵循模组的 **单版本单 Jar 策略 (1 Jar 1 Version Policy)**，每个版本分支均作为具有独立依赖项映射的完备制品进行构建。

---

## 🌟 核心子系统概览

- **[[重型冲击弹道|26.3-Heavy-Impact-Ballistics]]**：
  - 默认弩箭发射初速提升为 **1.5× 动能倍率** ($150\%$)。
  - 重力加速度伴随速度动态按比例缩减 ($g_{\text{eff}} = g_0 / M$)，带来平直的狙击弹道。
  - **弹道学附魔 (`bettercrossbows:ballistics`)**：每级提升 +25% 速度（V 级达到 +125%），与多重射击互斥。
  - 音效与粒子冲击波：超音速发射伴随深沉的破空爆破声与定向粒子冲击波。
- **[[快速拉弦机制|26.3-Quick-Draw-Mechanics]]**：
  - 通过游戏规则自定义装填刻数，并完美兼容快速装填 (Quick Charge) 附魔。
- **[[配置与游戏规则|26.3-Configuration-and-GameRules]]**：
  - 基于 DasikLibrary 动态注册的游戏规则 (`bettercrossbows:better_crossbows`)。
  - 彻底解锁全部整数空间 (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`)，践行沙盒玩家自由与反保姆原则。
  - 选配 YACL v3 客户端 GUI，无服务端类加载隐患。
- **[[架构与 Mixin 剖析|26.3-Architecture-and-Mixins]]**：
  - 深入剖析包含 `CrossbowItemMixin` 与 `AbstractArrowMixin` 在内的所有注入节点。

---

## 📜 制作署名与许可协议

- **作者与维护者**: **Dasik (Rifaditya)**
- **许可协议**: **GNU General Public License v3.0 (GPLv3)**
- **代码仓库**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
