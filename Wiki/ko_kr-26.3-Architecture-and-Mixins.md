# 💻 아키텍처 및 Mixin 분석 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최근 미출시 커밋 또는 개발 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

---

## 1. 공식 아키텍처 정보 상자

| 매개변수 | 기술적 세부사항 |
| :--- | :--- |
| **루트 패키지** | `net.vanillaoutsider.bettercrossbows` |
| **Java 플랫폼** | **Java 25** (`JAVA_25`) |
| **모드 초기화 클래스** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **클라이언트 초기화 클래스** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Mixin 설정** | `bettercrossbows.mixins.json` |
| **Mixin Refmap** | `bettercrossbows-refmap.json` |
| **Mixin 대상 클래스** | 6개 클래스 (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. 패키지 구조 및 "1 파일 1 목적(1 File, 1 Purpose)" 원칙

Better Crossbows는 관심사의 명확한 분리 원칙을 준수하여, 클라이언트 전용 UI 헬퍼, 서버 권한 레지스트리, 영구 설정 및 바이트코드 믹스인을 체계적으로 격리합니다:

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # 모드 초기화 클래스 및 SLF4J 로거 진입점
├── client/
│   └── BetterCrossbowsClientHelper.java # 클라이언트 전용 싱글플레이 월드 쿼리 (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # 영구 설정 POJO (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # ModMenu 설정 팩토리 제공자 (@Environment)
│   └── YaclScreenHelper.java            # YetAnotherConfigLib v3 화면 빌더 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # 발사 속도 배율에 따른 중력 반전/스케일링
│   ├── AnvilMenuMixin.java              # 모루 조합 시 게임 규칙 최대 레벨 한도 적용
│   ├── CreativeModeTabsMixin.java       # 크리에이티브 탭 마법이 부여된 책 필터링 및 캐시 갱신
│   ├── CrossbowItemMixin.java           # 속도 스케일링, 장전 타이밍, 소닉붐 파티클 연출
│   ├── EnchantmentMenuMixin.java        # 마법 부여대 탄도학 제공 레벨 클램핑
│   └── ItemCombinerMenuAccessor.java    # 모루 합성 시 player 필드 접근을 위한 Accessor
└── registry/
    ├── BetterCrossbowsEnchantments.java # 레지스트리 식별자 상수
    └── BetterCrossbowsGameRules.java    # 동적 네임스페이스 게임 규칙 등록
```

---

## 3. 서브시스템 종속성 및 아키텍처 다이어그램

```
           +---------------------------------------------+
           |         BetterCrossbows (Initializer)       |
           +---------------------------------------------+
                   |                             |
                   v                             v
+-----------------------------------+   +-----------------------------------+
|     BetterCrossbowsConfig         |   |      BetterCrossbowsGameRules     |
|   - JSON loading & saving         |   |   - DynamicGameRuleManager        |
|   - Anti-nanny bounds             |   |   - Categories & live getters     |
+-----------------------------------+   +-----------------------------------+
                   |                                     ^
                   v                                     |
+-----------------------------------+                    |
|       YaclScreenHelper (GUI)      |                    |
|   - Optional YACL v3 screens      |                    |
|   - Ko-fi creator support button  |                    |
+-----------------------------------+                    |
                                                         |
         +-----------------------------------------------+
         |
         v
+-------------------------------------------------------------------------------+
|                                MIXIN LAYER                                    |
|                                                                               |
|  [CrossbowItemMixin]       --> Scaled power launch & custom reload ticks     |
|  [AbstractArrowMixin]      --> Gravity scaling for flat trajectory arc       |
|  [AnvilMenuMixin]          --> Caps output enchantments to live GameRule     |
|  [EnchantmentMenuMixin]    --> Caps table rolls to live GameRule             |
|  [CreativeModeTabsMixin]   --> Caps books & flushes cached item parameters   |
|  [ItemCombinerMenuAccessor]--> Accesses protected player context              |
+-------------------------------------------------------------------------------+
```

---

## 4. 완전한 Mixin 인젝션 참조 매트릭스

| Mixin 클래스 | 대상 마인크래프트 클래스 | 인젝션 지점 / 메서드 | 인젝터 타입 | 우선순위 | 설명 |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | `BetterCrossbowsGameRules.getReloadTicks()`를 통해 장전 시간을 재정의하고 빠른 장전 감소치를 차감. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | 투사체 발사 호출을 래핑하여 기본 발사 위력에 $M_{\text{shot}}$을 곱함. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | 속도 비율 $R > 1.2$를 평가하여 소닉붐 음향 및 충격파 파티클 트리거. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | 화살이 쇠뇌에서 발사된 경우 기본 중력($0.05$)을 속도 배율로 나눔($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | `DynamicEnchantmentManager`를 통해 결과 아이템의 탄도학 레벨을 `CROSSBOW_BALLISTICS_MAX_LEVEL`로 제한. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | `@Shadow` 상속 충돌 없이 부모 클래스의 보호된 `player` 필드에 안전하게 접근. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | 마법 부여대에서 제공되는 탄도학 마법 부여 레벨 클램핑. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | 런타임 시 게임 규칙 변경을 감지하고 `CACHED_PARAMETERS`를 비워 탭 재생성을 강제. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | 재료 탭에 표시되는 최대 레벨 탄도학 마법이 부여된 책 제한. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | 검색 탭에 표시되는 모든 단계의 탄도학 마법이 부여된 책 제한. |

---

## 5. 기술적 특징 및 모범 사례

### A. MixinExtras `@WrapOperation` 대 취약한 `@ModifyVariable`
이전 버전에서는 발사 위력이 서수 기반 `@ModifyVariable`을 통해 수정되었습니다:
```java
// 취약한 레거시 인젝션 (바이트코드 변경 시 깨지기 쉬움):
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
최신 버전에서는 `shootProjectile`을 대상으로 하는 MixinExtras `@WrapOperation`으로 전면 리팩토링되었습니다:
```java
@WrapOperation(
    method = "performShooting",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shootProjectile(...)V")
)
private void bettercrossbows$wrapShootProjectile(..., Operation<Void> original) {
    float multiplier = bettercrossbows$getShotMultiplier(level, weapon);
    original.call(..., power * multiplier, ...);
}
```
이를 통해 Loom 재정렬이나 컴파일러의 로컬 변수 최적화에 영향을 받지 않는 100% 안정성을 보장합니다.

### B. 전용 서버를 위한 화면 격리 프로토콜
마인크래프트 전용 서버(Dedicated Server)에는 `net.minecraft.client.Minecraft`나 `Screen`과 같은 클라이언트 클래스가 포함되어 있지 않습니다. 공통 코드에서 클라이언트 API를 호출하면 즉시 크래시가 발생합니다:
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
이 위험을 제거하기 위해:
1. `ModMenuIntegration` 및 `YaclScreenHelper`는 `@Environment(EnvType.CLIENT)`로 표시됩니다.
2. `BetterCrossbowsClientHelper`는 클라이언트 가드 뒤에서 `Minecraft.getInstance().getSingleplayerServer()`를 안전하게 캡슐화합니다.
3. ModMenu 진입점은 DasikLibrary의 `GuiHelper.getOptionalYaclFactory(...)`를 사용하여 GUI가 실제로 호출될 때까지 클래스 로딩을 지연합니다.

---

## 🔗 관련 페이지
- [[🚀 강력한 충격 탄도학|ko_kr-26.3-Heavy-Impact-Ballistics]]
- [[⚡ 빠른 장전 메커니즘|ko_kr-26.3-Quick-Draw-Mechanics]]
- [[⚙️ 설정 및 게임 규칙|ko_kr-26.3-Configuration-and-GameRules]]
- [[26.3 개요 포털|ko_kr-26.3-Home]] 로 돌아가기
