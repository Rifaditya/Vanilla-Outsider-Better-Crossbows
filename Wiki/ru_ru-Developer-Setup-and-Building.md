# 🛠️ Настройка среды разработки и сборка

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности относительно исходного кода**: Документация в этой вики отражает **текущее состояние исходного кода в репозитории**, которое может содержать недавние невыпущенные коммиты или разрабатываемые функции, опережающие общедоступные сборки на CurseForge и Modrinth.

---

## 💻 Требования к среде разработки

Для компиляции и участия в разработке **Better Crossbows** убедитесь, что ваша система соответствует следующим требованиям:

- **Java Development Kit (JDK)**: **JDK 25** (Eclipse Adoptium Temurin 25 или Oracle OpenJDK 25).
- **Система автоматизации сборки**: Gradle (поставляется через wrapper `./gradlew`, Gradle 8.13).
- **Среда разработки (IDE)**: IntelliJ IDEA 2025+ или Eclipse с плагином Fabric Loom.
- **Git**: современный клиент Git с инструментами командной строки.

---

## 📂 Структура подпроектов репозитория

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

## 🔨 Сборка из исходного кода

Перед выполнением команд Gradle перейдите в каталог целевой версии:

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

## 🧪 Запуск автоматизированных модульных тестов

```bash
./gradlew test --no-daemon
```

- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 Интеграция зависимостей

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

## 📜 Стандарты кода и заголовок лицензии

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.

---

## 🧭 Навигация

- [[🏹 Minecraft {ver}|ru_ru-Home]]
- [[📋 Совместимость версий и матрица инструментария|ru_ru-Version-Compatibility]]
- [[MC 26.3 Overview|ru_ru-26.3-Home]]
- [[MC 26.2 Overview|ru_ru-26.2-Home]]
