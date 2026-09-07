# 🛠️ 개발자 환경 설정 및 빌드 가이드

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 본 위키 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 미출시 커밋이나 개발 중인 기능을 포함할 수 있습니다.

---

## 💻 개발 환경 전제 조건

**Better Crossbows**를 컴파일하고 기여하려면 로컬 환경이 다음 사양을 충족하는지 확인하세요:

- **Java Development Kit (JDK)**: **JDK 25** (Eclipse Adoptium Temurin 25 또는 Oracle OpenJDK 25 권장).
- **빌드 자동화 도구**: Gradle (저장소에 포함된 `./gradlew` 래퍼, Gradle 8.13).
- **IDE**: IntelliJ IDEA 2025+ 또는 Fabric Loom 플러그인이 설치된 Eclipse.
- **Git**: 명령줄 도구가 구성된 최신 Git 클라이언트.

---

## 📂 저장소 하위 프로젝트 구조

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

## 🔨 소스 코드에서 빌드

Gradle 명령을 실행하기 전에 대상 버전 디렉터리로 이동하세요:

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

## 🧪 자동 단위 테스트 실행

```bash
./gradlew test --no-daemon
```

- `net.vanillaoutsider.bettercrossbows.BetterCrossbowsConfigTest`: Validates default config properties, JSON schema round-trip loading, and anti-nanny integer boundary validation.

---

## 🧱 종속성 통합

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

## 📜 코딩 규칙 및 라이선스 헤더

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
```
- **Indentation**: 4 spaces for Java, 2 spaces for JSON and Markdown.
- **Encoding**: UTF-8 without BOM.
- **Mixins**: Use MixinExtras `@WrapOperation` for method call overrides instead of fragile ordinal `@ModifyVariable`.

---

## 🧭 네비게이션

- [[🏹 Minecraft {ver}|ko_kr-Home]]
- [[📋 버전 호환성 및 툴체인 매트릭스|ko_kr-Version-Compatibility]]
- [[MC 26.3 Overview|ko_kr-26.3-Home]]
- [[MC 26.2 Overview|ko_kr-26.2-Home]]
