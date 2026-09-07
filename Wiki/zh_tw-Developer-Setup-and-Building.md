# 🛠️ 開發者配置與建置指南

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代碼倉庫原始碼免責聲明**：本維基文件反映了**倉庫當前的原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新開發提交或未發布功能。

---

## 💻 環境準備與先決條件

在編譯或為 **Better Crossbows** 貢獻程式碼前，請確保開發環境符合以下要求：

- **Java 開發工具包 (JDK)**：**JDK 25** (Eclipse Adoptium Temurin 25 或 Oracle OpenJDK 25)。
- **建置自動化工具**：Gradle (透過存放庫自帶的 `./gradlew` 包裝器執行，Gradle 8.13)。
- **整合開發環境 (IDE)**：IntelliJ IDEA 2025+ 或安裝有 Fabric Loom 外掛模組的 Eclipse。
- **Git**：安裝有命令列工具的現代 Git 客戶端。

---

## 📂 存放庫子專案佈局

```
Vanilla-Outsider-Better-Crossbows/
├── Better Crossbows v26.2/
│   └── better-crossbows/         # Minecraft 26.2 subproject
│       ├── build.gradle
│       ├── gradle.properties
│       └── src/
├── Better Crossbows v26.3/
│   └── better-crossbows/         # Minecraft 26.3 subproject
│       ├── build.gradle
│       ├── gradle.properties
│       └── src/
├── Wiki/                         # Shared repository GitHub Wiki documentation
└── LICENSE                       # GNU General Public License v3.0
```

---

## 🔨 從原始碼建置

在執行 Gradle 命令前，請進入對應的目標版本目錄：

### For Minecraft 26.3:
```bash
cd "Better Crossbows v26.3/better-crossbows"
./gradlew build --no-daemon
```

### For Minecraft 26.2:
```bash
cd "Better Crossbows v26.2/better-crossbows"
./gradlew build --no-daemon
```

---

## 🧪 執行自動化單元測試

```bash
./gradlew test --no-daemon
```

- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 相依項整合

```properties
dasik_library_version=1.8.39
```

```groovy
dependencies {
    modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
    modImplementation "net.dasik:dasik-library:${project.dasik_library_version}"
    
    // Optional integrations
    modCompileOnly "dev.isxander:yet-another-config-lib-fabric:3.6.1+1.21-fabric"
    modCompileOnly "com.terraformersmc:modmenu:13.0.0-beta.1"
}
```

---

## 📜 程式碼規範與開源授權標頭

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.

---

## 🧭 導覽

- [[🏹 Minecraft {ver}|zh_tw-Home]]
- [[📋 版本相容性與工具鏈矩陣|zh_tw-Version-Compatibility]]
- [[MC 26.3 Overview|zh_tw-26.3-Home]]
- [[MC 26.2 Overview|zh_tw-26.2-Home]]
