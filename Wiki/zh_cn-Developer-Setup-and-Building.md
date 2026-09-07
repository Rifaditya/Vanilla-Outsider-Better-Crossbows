# 🛠️ 开发者配置与构建指南

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代码仓库源码免责声明**：本维基文档反映了**仓库当前的源代码状态**，可能包含领先于 CurseForge 与 Modrinth 上公开发布版本的最新开发提交或未发布功能。

---

## 💻 环境准备与先决条件

在编译或为 **Better Crossbows** 贡献代码前，请确保开发环境满足以下要求：

- **Java 开发工具包 (JDK)**：**JDK 25** (Eclipse Adoptium Temurin 25 或 Oracle OpenJDK 25)。
- **构建自动化工具**：Gradle (通过仓库自带的 `./gradlew` 包装器运行，Gradle 8.13)。
- **集成开发环境 (IDE)**：IntelliJ IDEA 2025+ 或安装有 Fabric Loom 插件的 Eclipse。
- **Git**：安装有命令行工具的现代 Git 客户端。

---

## 📂 仓库子项目布局

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

## 🔨 从源码构建

在执行 Gradle 命令前，请进入对应的目标版本目录：

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

## 🧪 运行自动化单元测试

```bash
./gradlew test --no-daemon
```

- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 依赖项集成

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

## 📜 代码规范与开源许可证标头

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.

---

## 🧭 导航

- [[🏹 Minecraft {ver}|zh_cn-Home]]
- [[📋 版本兼容性与工具链矩阵|zh_cn-Version-Compatibility]]
- [[MC 26.3 Overview|zh_cn-26.3-Home]]
- [[MC 26.2 Overview|zh_cn-26.2-Home]]
