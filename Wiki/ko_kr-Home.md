# 🏹 Vanilla Outsider: Better Crossbows 공식 위키

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 고지 사항**: 본 위키 문서에는 CurseForge 및 Modrinth의 공개 빌드보다 앞선 최신 개발 커밋 또는 미출시 기능이 포함된 **저장소의 현재 소스 코드 상태**가 반영되어 있습니다.

---

## 🎯 공식 문서에 오신 것을 환영합니다

**Better Crossbows**는 **Dasik (Rifaditya)**가 제작한 **Vanilla Outsider Collection** 소속의 정밀 전투 및 탄도 메커니즘 모드입니다. 쇠뇌를 단순한 수치적 피해량 증가가 아닌 운동 속도, 평평한 탄도 궤적, 모듈식 장전 텐션을 갖춘 전용 **중충격 발사체 플랫폼**으로 탈바꿈시킵니다.

---

## 🧭 버전 선택 포털

목표하는 마인크래프트 버전을 선택하여 전용 문서 트리에 접속하세요:

| 마인크래프트 버전 | 모드 릴리스 | 런타임 툴체인 | 상태 | 바로가기 포털 |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 최신 활성 | [[👉 MC 26.3 위키 열기|ko_kr-26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 패리티 버전 | [[👉 MC 26.2 위키 열기|ko_kr-26.2-Home]] |

---

## 🌟 핵심 메커니즘 개요

- **[[중충격 탄도학|ko_kr-26.3-Heavy-Impact-Ballistics]]**:
  - 기본 쇠뇌 화살 속도가 **1.5배** ($150\%$)로 비약적 증가.
  - 탄도 낙차 보정: 화살에 작용하는 중력이 발사 속도에 비례하여 반비례 감소 ($g_{\text{eff}} = g_0 / M$).
  - **탄도학 마법부여 (`bettercrossbows:ballistics`)**: 레벨당 +25% 속도 보너스 (V 레벨 시 최대 +125%); 다중 발사(Multishot)와 상호 배타적.
  - 초음속 발사 시 묵직한 소닉 붐 폭음 및 충격파 입자 효과.
- **[[신속 장전 메커니즘|ko_kr-26.3-Quick-Draw-Mechanics]]**:
  - 게임룰을 통한 장전 틱 조절 지원 및 빠른 장전(Quick Charge) 마법부여와 완벽 호환.
- **[[설정 및 게임룰|ko_kr-26.3-Configuration-and-GameRules]]**:
  - DasikLibrary 기반의 동적 게임룰 (`bettercrossbows:better_crossbows`).
  - 샌드박스 플레이어의 자율성을 존중하여 정수 범위 전면 개방 (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`).
  - 클라이언트 전용 YACL v3 GUI 설정 지원.
- **[[아키텍처 및 믹스인 분석|ko_kr-26.3-Architecture-and-Mixins]]**:
  - `CrossbowItemMixin`, `AbstractArrowMixin` 등 모든 인젝션 포인트 상세 분석.

---

## 📜 크레딧 및 라이선스

- **제작자**: **Dasik (Rifaditya)**
- **라이선스**: **GNU General Public License v3.0 (GPLv3)**
- **소스 코드**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
