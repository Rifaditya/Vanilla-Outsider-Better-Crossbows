# 📋 版本兼容性与工具链矩阵

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代码仓库源码免责声明**：本维基文档反映了**仓库当前的源代码状态**，可能包含领先于 CurseForge 与 Modrinth 上公开发布版本的最新开发提交或未发布功能。

---

## 🏛️ “单版本单 Jar” 政策 (1 Jar 1 Version)

**Better Crossbows** 严格践行 **单版本单 Jar 政策**。每个目标 Minecraft 版本均在独立的子项目目录中进行构建，拥有主权依赖绑定、编译器映射以及构建产物：
- **拒绝拼凑型通用 Jar**：摒弃跨版本运行时反射与脆弱的字节码适配，为每个版本锚点提供严格验证的独立构建。
- **独立发行**：模组产物按版本锚点发行（如 `better-crossbows-1.0.14+26.3.jar`）。
- **归档保留**：编译完成的制品统一存档于 `Archive Jar of all versions/`。

---

## 📊 完整技术兼容性矩阵

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **当前主版本** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **对齐版本** |

---

## 🧩 可选与推荐依赖项

Better Crossbows 设计为除 Fabric API 与 DasikLibrary 外零强制客户端依赖。但在安装了可选模组时，将动态启用丰富的图形化配置界面：

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 服务端与客户端安全性架构

所有客户端 GUI 代码均在**屏幕隔离协议 (Screen Isolation Protocol)** 保护之下：
- `ModMenuIntegration` 与 `YaclScreenHelper` 均显式标注 `@Environment(EnvType.CLIENT)`。
- 入口点通过 DasikLibrary 的 `GuiHelper.getOptionalYaclFactory(...)` 进行解析，将 YACL 类引用完全隔离在反射与客户端守卫之后。
- 在**独立专用服务端 (Dedicated Server)** 上运行 Better Crossbows 时，确保 100% 杜绝 `ClassNotFoundException` 或 `NoClassDefFoundError: net/minecraft/client/Minecraft` 崩溃。

---

## 🗄️ 历史构建与归档制品

过往发布版本的归档 JAR 存放于：
- `Archive Jar of all versions/`
- 本地子项目发布暂存：`<Subproject>/releases/`
- 官方 Modrinth 发行列表：[Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 导航

- 返回 [[🏹 Minecraft {ver}|zh_cn-Home]]
- [[MC 26.3 Overview|zh_cn-26.3-Home]]
- [[MC 26.2 Overview|zh_cn-26.2-Home]]
- [[Developer Setup|zh_cn-Developer-Setup-and-Building]]
