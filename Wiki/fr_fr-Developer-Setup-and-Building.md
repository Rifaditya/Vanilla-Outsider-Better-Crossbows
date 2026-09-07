# 🛠️ Guide de configuration et de compilation développeur

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Clause de non-responsabilité relative au code source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, incluant d'éventuels commits récents non publiés sur CurseForge ou Modrinth.

---

## 💻 Prérequis et environnement de développement

Pour compiler et contribuer à **Better Crossbows**, assurez-vous que votre environnement respecte les prérequis suivants :

- **Kit de développement Java (JDK)** : **JDK 25** (Eclipse Adoptium Temurin 25 ou Oracle OpenJDK 25).
- **Automatisation de la compilation** : Gradle (fourni via le wrapper `./gradlew`, Gradle 8.13).
- **IDE** : IntelliJ IDEA 2025+ ou Eclipse avec le plugin Fabric Loom installé.
- **Git** : Client Git moderne avec outils en ligne de commande.

---

## 📂 Organisation des sous-projets du dépôt

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

## 🔨 Compiler à partir des sources

Naviguez vers le dossier de la version ciblée avant de lancer les commandes Gradle :

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

## 🧪 Exécution des tests unitaires automatisés

```bash
./gradlew test --no-daemon
```

- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 Intégration des dépendances

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

## 📜 Conventions de code et en-tête de licence

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.

---

## 🧭 Navigation

- [[🏹 Minecraft {ver}|fr_fr-Home]]
- [[📋 Compatibilité des versions et matrice des outils|fr_fr-Version-Compatibility]]
- [[MC 26.3 Overview|fr_fr-26.3-Home]]
- [[MC 26.2 Overview|fr_fr-26.2-Home]]
