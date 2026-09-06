# 🛠️ Developer Setup & Building Guide

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 💻 Environment Prerequisites

To compile and contribute to **Better Crossbows**, ensure your environment meets the following specifications:

- **Java Development Kit (JDK)**: **JDK 25** (Eclipse Adoptium Temurin 25 or Oracle OpenJDK 25).
- **Build Automation**: Gradle (included via `./gradlew` wrapper, Gradle 8.13).
- **IDE**: IntelliJ IDEA 2025+ or Eclipse with the Fabric Loom plugin installed.
- **Git**: Modern Git client with command-line tools.

---

## 📂 Repository Subproject Layout

The repository utilizes independent subproject directories per Minecraft version anchor:

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

## 🔨 Building from Source

Navigate to the targeted version directory before running Gradle commands:

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

Compiled mod JARs will be generated in `build/libs/`:
- `better-crossbows-<version>.jar` (Production remapped JAR)
- `better-crossbows-<version>-sources.jar` (Decompiled source artifact)

---

## 🧪 Running Automated Unit Tests

Automated tests are located in `src/test/java/` verifying configuration serialization, defaults, and boundary math:

```bash
./gradlew test --no-daemon
```

Test classes:
- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 Dependency Integration

Better Crossbows relies on **DasikLibrary** for namespaced GameRule registration and config persistence. When declaring local dependencies or testing with dev builds, specify in `gradle.properties`:

```properties
dasik_library_version=1.8.39
```

And in `build.gradle`:
```groovy
repositories {
    mavenCentral()
    maven { name = "Shedaniel"; url = "https://maven.shedaniel.me/" }
    maven { name = "TerraformersMC"; url = "https://maven.terraformersmc.com/" }
    maven { name = "Xander Maven"; url = "https://maven.isxander.dev/releases" }
    flatDir { dirs 'libs', '../../libs' }
}

dependencies {
    modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
    modImplementation "net.dasik:dasik-library:${project.dasik_library_version}"
    
    // Optional integrations
    modCompileOnly "dev.isxander:yet-another-config-lib-fabric:3.6.1+1.21-fabric"
    modCompileOnly "com.terraformersmc:modmenu:13.0.0-beta.1"
}
```

---

## 📜 Coding Conventions & License Header

Every `.java` file in `src/main/java/` and `src/test/java/` must include the standard single-line GPLv3 license header:
```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.
