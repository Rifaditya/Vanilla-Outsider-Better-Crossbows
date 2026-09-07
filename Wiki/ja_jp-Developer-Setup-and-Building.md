# 🛠️ 開発者環境構築およびビルドガイド

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: 本Wikiドキュメントは**リポジトリの最新ソースコード状態**を反映しており、CurseForgeやModrinthで公開されているリリース版に含まれない開発中機能を含む場合があります。

---

## 💻 開発環境の前提条件

**Better Crossbows** のビルドや開発を行う前に、開発環境が以下の要件を満たしていることを確認してください：

- **Java Development Kit (JDK)**: **JDK 25** (Eclipse Adoptium Temurin 25 または Oracle OpenJDK 25 推奨)。
- **ビルドツール**: Gradle (リポジトリ同梱の `./gradlew` ラッパー、Gradle 8.13)。
- **IDE**: IntelliJ IDEA 2025+ または Fabric Loom プラグインを導入した Eclipse。
- **Git**: コマンドラインツールを備えた最新の Git クライアント。

---

## 📂 リポジトリのサブプロジェクト構造

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

## 🔨 ソースコードからのビルド

Gradleコマンドを実行する前に、対象のバージョンディレクトリに移動します：

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

## 🧪 自動ユニットテストの実行

```bash
./gradlew test --no-daemon
```

- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 依存関係のインテグレーション

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

## 📜 コーディング規約とライセンスヘッダー

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.

---

## 🧭 ナビゲーション

- [[🏹 Minecraft {ver}|ja_jp-Home]]
- [[📋 バージョン互換性およびツールチェーンマトリクス|ja_jp-Version-Compatibility]]
- [[MC 26.3 Overview|ja_jp-26.3-Home]]
- [[MC 26.2 Overview|ja_jp-26.2-Home]]
