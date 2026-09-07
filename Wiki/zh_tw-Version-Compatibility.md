# 📋 版本相容性與工具鏈矩陣

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代碼倉庫原始碼免責聲明**：本維基文件反映了**倉庫當前的原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新開發提交或未發布功能。

---

## 🏛️ 「單版本單 Jar」 政策 (1 Jar 1 Version)

**Better Crossbows** 嚴格踐行 **單版本單 Jar 政策**。每個目標 Minecraft 版本均在獨立的子專案目錄中進行建置，擁有主權依賴綁定、編譯器映射以及建置產物：
- **拒絕拼湊型通用 Jar**：摒棄跨版本執行階段反射與脆弱的位元組碼適配，為每個版本錨點提供嚴格驗證的獨立建置。
- **獨立發行**：模組產物按版本錨點發行（如 `better-crossbows-1.0.14+26.3.jar`）。
- **封存保留**：編譯完成的製品統一存檔於 `Archive Jar of all versions/`。

---

## 📊 完整技術相容性矩陣

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **目前主版本** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **對齊版本** |

---

## 🧩 可選與推薦相依項

Better Crossbows 設計為除 Fabric API 與 DasikLibrary 外零強制客戶端依賴。但在安裝了可選模組時，將動態啟用豐富的圖形化設定介面：

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 伺服端與客戶端安全性架構

所有客戶端 GUI 程式碼均在**螢幕隔離協定 (Screen Isolation Protocol)** 保護之下：
- `ModMenuIntegration` 與 `YaclScreenHelper` 均顯式標註 `@Environment(EnvType.CLIENT)`。
- 進入點透過 DasikLibrary 的 `GuiHelper.getOptionalYaclFactory(...)` 進行解析，將 YACL 類別參照完全隔離在反射與客戶端守衛之後。
- 在**獨立專用伺服端 (Dedicated Server)** 上執行 Better Crossbows 時，確保 100% 杜絕 `ClassNotFoundException` 或 `NoClassDefFoundError: net/minecraft/client/Minecraft` 崩潰問題。

---

## 🗄️ 歷史建置與封存製品

過往發布版本的封存 JAR 存放於：
- `Archive Jar of all versions/`
- 本地子專案發布暫存：`<Subproject>/releases/`
- 官方 Modrinth 發行清單：[Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 導覽

- 返回 [[🏹 Minecraft {ver}|zh_tw-Home]]
- [[MC 26.3 Overview|zh_tw-26.3-Home]]
- [[MC 26.2 Overview|zh_tw-26.2-Home]]
- [[Developer Setup|zh_tw-Developer-Setup-and-Building]]
