# ⚙️ 설정 및 게임 규칙 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최근 미출시 커밋 또는 개발 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

## 1. 공식 기술 정보 상자

| 매개변수 | 기술적 세부사항 |
| :--- | :--- |
| **시스템 아키텍처** | 동적 네임스페이스 게임 규칙 + 영구 전역 설정 JSON |
| **Java 구현체** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **GUI 구현체** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **게임 규칙 카테고리** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **전역 파일 위치** | `config/bettercrossbows.json` |
| **명령어 네임스페이스** | `/gamerule bettercrossbows:<rule> [value]` |
| **플레이어 자율성 불변 법칙** | 전체 정수 범위 완전 개방: `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. 단계별 설정 워크플로

### 게임 내 서버 관리 (Brigadier 명령어)
서버 관리자(권한 레벨 2 이상)는 서버를 재시작하지 않고도 즉시 적용되는 방식으로 메커니즘을 변경할 수 있습니다:

1. **현재 수치 확인**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **매개변수 수정**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **기본값으로 초기화**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### 전역 기본 설정 (`config/bettercrossbows.json`)
**새로운 월드**를 생성할 때 서버는 `config/bettercrossbows.json`에 정의된 값으로 GameRules를 초기화합니다. 이 JSON 파일을 수정해도 기존 월드는 덮어쓰이지 않으므로, 활성 월드에서는 `/gamerule`을 사용하세요.

### 게임 내 클라이언트 설정 화면 (YACL + ModMenu)
**ModMenu**와 **YetAnotherConfigLib (YACL v3)** 가 설치된 플레이어는 대화형 GUI를 열 수 있습니다:
1. **설정** -> **모드** -> **Better Crossbows** -> **설정 (⚙️)** 으로 이동합니다.
2. 투사체 속도, 폭죽 속도, 장전 틱, 파티클 토글 슬라이더를 조정합니다.
3. 독립 모드 개발을 후원하기 위한 선택적 Ko-fi 창작자 후원 버튼을 클릭할 수 있습니다.

---

## 3. 수학적 한계와 플레이어 자율성 불변 법칙

**플레이어 자율성 및 반과잉보호 불변 법칙(Anti-Nanny Invariant)** 에 따라 Better Crossbows는 **인위적인 상한선이나 불필요한 제약을 일절 부과하지 않습니다**:
- 모든 정수형 게임 규칙은 `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`로 등록됩니다.
- 관리자가 화살을 $10,000\%$ 속도($100\times$)로 발사하기를 원하거나 장전 틱을 $1$로 설정하더라도 모드는 명령을 온전히 따릅니다.
- 치명적인 JVM 충돌을 방지하기 위해 기술적으로 필수적인 경우에만 하한선 안전 장치가 엄격하게 적용됩니다(예: 수학 연산 중 장전 틱을 $\ge 1$ 틱으로 고정).

---

## 4. 설정 우선순위 및 라이프사이클 순서도

```
           [ config/bettercrossbows.json ]
                         |
                         | (Read on mod startup)
                         v
           [ BetterCrossbowsConfig.load() ]
                         |
                         | (Supplies initial defaults)
                         v
           [ World Creation (Level.java) ]
                         |
                         | (GameRules initialized)
                         v
          +-------------------------------+
          |  ACTIVE LEVEL GAMERULE STATE  | <-----+ (/gamerule command)
          +-------------------------------+       |
                         |                        | (Admin modification)
                         v                        |
           [ DynamicGameRuleManager.get() ] ------+
                         |
                         +---> CrossbowItemMixin (Launch & Reload)
                         +---> AbstractArrowMixin (Gravity)
                         +---> CreativeModeTabsMixin (Tab Books)
```

---

## 5. JSON 설정 스키마 (`config/bettercrossbows.json`)

```json
{
  "configVersion": 1,
  "crossbowBallisticsMaxLevel": 5,
  "crossbowVelocityMultiplier": 150,
  "crossbowFireworkMultiplier": 100,
  "crossbowReloadTicks": 25,
  "crossbowEnableJuice": true
}
```

---

## 6. 완전한 게임 규칙 참조 표

| 게임 규칙 식별자 | 타입 | 기본값 | 유효 범위 | 단위 / 스케일 | 설명 |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` ~ `MAX_VALUE` | 레벨 | 마법 부여대, 모루 및 크리에이티브 탭에서 얻을 수 있는 탄도학 마법 부여의 최대 레벨. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` ~ `MAX_VALUE` | 백분율 ($100 = 1.0\times$) | 쇠뇌에서 발사된 화살에 적용되는 속도 배율 ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` ~ `MAX_VALUE` | 백분율 ($100 = 1.0\times$) | 쇠뇌에서 발사된 폭죽 로켓에 적용되는 속도 배율 ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` ~ `MAX_VALUE` | 게임 틱 ($20\text{t} = 1\text{s}$) | 빠른 장전 감소가 적용되기 전 쇠뇌 장전에 필요한 기본 시간. |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | 토글 | 고속 발사 시 초음속 소닉붐 음향 및 충격파 파티클 활성화. |

---

## 7. 개발자 및 API 훅

### 애드온 모드에서 게임 규칙 읽기
애드온 모드는 `BetterCrossbowsGameRules`를 통해 활성 게임 규칙을 직접 조회할 수 있습니다:

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// 실시간 배율을 float로 반환 (예: 150인 경우 1.5f)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// 실시간 장전 틱 반환 (예: 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// 시각/음향 효과 활성화 여부 확인
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### `DynamicGameRuleManager` (DasikLibrary)를 통한 등록
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 관련 페이지
- [[🚀 강력한 충격 탄도학|ko_kr-26.2-Heavy-Impact-Ballistics]]
- [[⚡ 빠른 장전 메커니즘|ko_kr-26.2-Quick-Draw-Mechanics]]
- [[💻 아키텍처 및 Mixin 분석|ko_kr-26.2-Architecture-and-Mixins]]
- [[26.2 개요 포털|ko_kr-26.2-Home]] 로 돌아가기
