# 🏹 Minecraft 26.3 — Better Crossbows 공식 포털

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 본 위키 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 미출시 커밋이나 개발 중인 기능을 포함할 수 있습니다.

---

## 🎯 Minecraft 26.3 제어 센터에 오신 것을 환영합니다

본 문서는 **Minecraft 26.3** (`1.0.14+26.3`) 환경의 **Better Crossbows** 가이드입니다.

Minecraft 26.3은 현대화된 전투 메커니즘, Java 25 런타임 및 최신 Fabric API (`0.156.1+26.3`)를 탑재했습니다. Better Crossbows는 쇠뇌에 고속 탄도학, 저신장 비행 궤적, 소닉 붐 효과 및 서버 게임룰을 제공합니다.

---

## 🧭 MC 26.3 서브시스템 문서 트리

이 버전을 위한 서브시스템 가이드를 살펴보세요:

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|ko_kr-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|ko_kr-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|ko_kr-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|ko_kr-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 MC 26.3 빌드 기술 사양

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.3` / `26.3-snapshot-6` (`>=26.3-`) |
| **Mod Version** | `1.0.14+26.3` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.01.22` |
| **Subproject Source** | `Better Crossbows v26.3/better-crossbows/` |

---

## 🌟 핵심 하이라이트 및 새로운 기능 (26.3)

1. **믹스인 현대화**: `shootProjectile`에 MixinExtras `@WrapOperation`을 적용하여 불안정한 서수 인젝션 완전 제거.
2. **진정한 샌드박스 자유**: 플레이어 자율성 원칙에 따라 모든 GameRules 제한을 해제하고 `Integer.MAX_VALUE`까지 허용.
3. **전용 서버 안정성 강화**: 싱글플레이어 로직을 `@Environment(EnvType.CLIENT)` 뒤로 격리하여 리눅스 전용 서버 무결성 보장.
4. **크리에이티브 탭 캐시 즉각 갱신**: 게임룰 변경 시 인챈트 책 목록이 월드 재접속 없이 실시간 갱신.

---

## 🧭 Navigation
- [[Master Home Portal|ko_kr-Home]]
- [[Version Compatibility|ko_kr-Version-Compatibility]]
- [[Developer Setup|ko_kr-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|ko_kr-26.2-Home]]
