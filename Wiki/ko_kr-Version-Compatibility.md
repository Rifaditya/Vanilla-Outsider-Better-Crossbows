# 📋 버전 호환성 및 툴체인 매트릭스

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 본 위키 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 미출시 커밋이나 개발 중인 기능을 포함할 수 있습니다.

---

## 🏛️ «1 Jar 1 버전» 정책 (1 Jar 1 Version Policy)

**Better Crossbows**는 엄격한 **1 Jar 1 버전 정책**을 준수합니다. 각 대상 마인크래프트 버전은 독립된 하위 프로젝트 디렉터리에서 독자적인 종속성, 컴파일러 매핑 및 빌드 아티팩트로 관리됩니다:
- **불안정한 범용 Jar 배제**: 런타임 리플렉션 대신 각 버전 앵커마다 엄격하게 검증된 빌드를 제공합니다.
- **전용 릴리스**: 버전 앵커별로 모드 아티팩트 배포 (예: `better-crossbows-1.0.14+26.3.jar`).
- **아카이브 보존**: 빌드된 릴리스는 `Archive Jar of all versions/` 디렉터리에 보관됩니다.

---

## 📊 완전한 기술 호환성 매트릭스

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **현재 활성** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **패리티 앵커** |

---

## 🧩 선택적 및 권장 종속성

Better Crossbows는 Fabric API와 DasikLibrary 외에 필수 클라이언트 종속성이 없습니다. 다만 선택적 모드가 설치된 경우 풍부한 그래픽 설정 화면이 활성화됩니다:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 서버 및 클라이언트 사이드 안전성 아키텍처

모든 클라이언트 GUI 코드는 **화면 격리 프로토콜 (Screen Isolation Protocol)** 에 따라 보호됩니다:
- `ModMenuIntegration` 및 `YaclScreenHelper`에는 명시적으로 `@Environment(EnvType.CLIENT)`가 지정되어 있습니다.
- 진입점은 DasikLibrary의 `GuiHelper.getOptionalYaclFactory(...)`를 통해 지연 로딩됩니다.
- **전용 서버 (Dedicated Server)** 에서 실행할 때 `ClassNotFoundException` 또는 `NoClassDefFoundError: net/minecraft/client/Minecraft` 충돌을 100% 방지합니다.

---

## 🗄️ 이전 빌드 및 아카이브 파일

이전 릴리스 빌드의 아카이브 JAR는 다음 위치에 보관됩니다:
- `Archive Jar of all versions/`
- 하위 프로젝트 로컬 릴리스 스테이징: `<Subproject>/releases/`
- 공식 Modrinth 릴리스 목록: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 네비게이션

- [[메인 포털|ko_kr-Home]] 로 돌아가기
- [[MC 26.3 Overview|ko_kr-26.3-Home]]
- [[MC 26.2 Overview|ko_kr-26.2-Home]]
- [[Developer Setup|ko_kr-Developer-Setup-and-Building]]
