# 🚀 중충격 탄도학 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 본 위키 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 미출시 커밋이나 개발 중인 기능을 포함할 수 있습니다.

---

## 1. 공식 기술 정보 패널

| Parameter | Technical Details |
| :--- | :--- |
| **Enchantment Identifier** | `bettercrossbows:ballistics` |
| **Java Mixin Implementations** | `CrossbowItemMixin.java`, `AbstractArrowMixin.java` |
| **Registry Type** | `BuiltInRegistries.ENCHANTMENT` (Data-driven registry) |
| **Data Component / Codec** | `minecraft:enchantments` (`net.minecraft.world.item.enchantment.ItemEnchantments`) |
| **Exclusive Set Tag** | `#minecraft:exclusive_set/multishot` (Incompatible with Multishot) |
| **Supported Item Tag** | `#minecraft:enchantable/crossbow` |
| **Controlling GameRules** | `bettercrossbows:crossbow_velocity_multiplier` (Arrows, default: `150`), `bettercrossbows:crossbow_firework_multiplier` (Rockets, default: `100`), `bettercrossbows:crossbow_ballistics_max_level` (default: `5`), `bettercrossbows:crossbow_enable_juice` (default: `true`) |
| **Translation Keys** | `enchantment.bettercrossbows.ballistics`, `enchantment.bettercrossbows.ballistics.desc` |

---

## 2. 플레이어 단계별 게임플레이 워크플로

### Obtaining the Ballistics Enchantment
1. **Enchanting Table**: Place a Crossbow on an Enchanting Table surrounded by 15 bookshelves. Ballistics has an enchantment weight of `2` (rare, on par with Infinity and Channeling).
2. **Anvil Combination**: Combine lower-tier Ballistics books (e.g. Ballistics I + Ballistics I = Ballistics II) up to the world's configured maximum level (default: `V`).
3. **Exclusivity Choice**: Ballistics cannot be combined with **Multishot** (`minecraft:multishot`). Players must choose between **Area Suppression** (Multishot) and **Long-Range Sniper Precision** (Ballistics).

### Precision Shooting
1. **Load Crossbow**: Hold right-click with arrows or firework rockets in the offhand or inventory until fully charged.
2. **Aim & Fire**: Notice that arrows fly on a significantly flatter arc with dramatically less bullet drop.
3. **Sonic Feedback**: When firing with velocity ratio exceeding $1.2\times$, the weapon emits a distinctive supersonic crack (`FIREWORK_ROCKET_BLAST_FAR`) accompanied by expanding vapor cones.

---

## 3. 수학 공식 및 운동학 계산

### A. Shot Velocity Multiplier
The projectile launch speed is scaled dynamically at the moment of firing:

$$M_{\text{shot}} = \frac{G_{\text{multiplier}}}{100.0} \times \left(1.0 + 0.25 \times L_{\text{ballistics}}\right)$$

Where:
- $G_{\text{multiplier}}$ is `crossbow_velocity_multiplier` for arrows (default: `150`) or `crossbow_firework_multiplier` for fireworks (default: `100`).
- $L_{\text{ballistics}}$ is the integer level of the Ballistics enchantment on the weapon.

### B. Dynamic Arc Flattening (Gravity Inversion)
In vanilla Minecraft, projectiles suffer vertical gravity deceleration of $g_0 = 0.05\text{ blocks/tick}^2$. Better Crossbows intercepts `AbstractArrow#getDefaultGravity()` and scales it down:

$$g_{\text{eff}} = \frac{g_0}{M_{\text{shot}}} = \frac{0.05}{M_{\text{shot}}}$$

For example, with a default $1.5\times$ multiplier and Ballistics V ($M_{\text{shot}} = 1.5 \times 2.25 = 3.375$):

$$g_{\text{eff}} = \frac{0.05}{3.375} \approx 0.0148\text{ blocks/tick}^2$$

This produces a true **flat sniper arc**, allowing accurate targeting at 100+ blocks without needing extreme vertical elevation.

### C. Sonic Crack Audio & Shockwave Particles
When `bettercrossbows:crossbow_enable_juice` is `true` and power ratio $R = M_{\text{shot}} > 1.2$:

$$\text{Sound Pitch} = \max\left(0.2, 0.8 - (R - 1.2) \times 0.15\right)$$

- **Cloud Particles (`ParticleTypes.CLOUD`)**: Count $N = \lfloor 5 \times R \rfloor$, Speed $V = 0.05 \times R$.
- **Supersonic Gusts (`ParticleTypes.GUST`)**: If $R > 2.0$, 2 additional high-velocity gust particles are emitted at shooter eye height.

---

## 4. 탄도 궤적 시각 비교 다이어그램

```
Height (Y)
 ^
 |  Vanilla Arc: High arc, rapid drop, short effective range (~40 blocks)
 |   .-'""'-.
 |  /        \
 | /          \
 |/            v
 +--------------------------------------------------------------------> Distance (X)
 |
 |  Better Crossbows (1.5x) + Ballistics V: Flat sniper trajectory (>120 blocks)
 |  .---------------------------------------------------.
 | /                                                     \
 |/                                                       v
 +--------------------------------------------------------------------> Distance (X)
```

### Execution Flowchart
```
[ Player Releases Crossbow ]
             |
             v
[ CrossbowItemMixin#performShooting ]
             |
             +---> Calculate Multiplier: M = BaseMult * (1.0 + 0.25 * Level)
             |
             +---> Wrap shootProjectile(power * M)
             |
             +---> Check Juice: Is M > 1.2?
                     |
                     +-- Yes --> Play Sound: FIREWORK_ROCKET_BLAST_FAR (Pitch down to 0.2)
                     |           Spawn Cloud & Gust Particles
                     |
                     +-- No  --> Standard Vanilla Audio
             |
             v
[ Arrow Spawned in World ]
             |
             v
[ AbstractArrowMixin#getDefaultGravity ]
             |
             +---> Check Weapon: Is Crossbow?
                     |
                     +-- Yes --> Scale Gravity: g = 0.05 / M
                     +-- No  --> Return Vanilla 0.05
```

---

## 5. SNBT 및 Codec 데이터 스키마

### Crossbow Custom Item Enchantment Component (SNBT)
```snbt
{
  "minecraft:enchantments": {
    "levels": {
      "bettercrossbows:ballistics": 5
    }
  }
}
```

### Data-Driven Enchantment Definition (`data/bettercrossbows/enchantment/ballistics.json`)
```json
{
  "description": {
    "translate": "enchantment.bettercrossbows.ballistics"
  },
  "exclusive_set": "#minecraft:exclusive_set/multishot",
  "supported_items": "#minecraft:enchantable/crossbow",
  "primary_items": "#minecraft:enchantable/crossbow",
  "weight": 2,
  "max_level": 255,
  "min_cost": {
    "base": 15,
    "per_level_above_first": 9
  },
  "max_cost": {
    "base": 65,
    "per_level_above_first": 9
  },
  "anvil_cost": 4,
  "slots": [
    "mainhand"
  ]
}
```

---

## 6. 종합 탄도학 참조 데이터표

Values calculated at default settings (`crossbow_velocity_multiplier = 150`):

| Ballistics Level | Bonus Multiplier | Total Velocity ($M_{\text{shot}}$) | Effective Gravity ($g_{\text{eff}}$) | Sonic Crack Pitch | Gust Shockwave? |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **None (0)** | $+0\%$ | **$1.50\times$** | $0.0333\text{ b/t}^2$ | $0.755$ | ❌ |
| **Level I** | $+25\%$ | **$1.875\times$** | $0.0267\text{ b/t}^2$ | $0.699$ | ❌ |
| **Level II** | $+50\%$ | **$2.250\times$** | $0.0222\text{ b/t}^2$ | $0.643$ | ✅ (Supersonic) |
| **Level III** | $+75\%$ | **$2.625\times$** | $0.0190\text{ b/t}^2$ | $0.586$ | ✅ (Supersonic) |
| **Level IV** | $+100\%$ | **$3.000\times$** | $0.0167\text{ b/t}^2$ | $0.530$ | ✅ (Supersonic) |
| **Level V** | $+125\%$ | **$3.375\times$** | $0.0148\text{ b/t}^2$ | $0.474$ | ✅ (Supersonic) |
| **Level X** *(Custom)* | $+250\%$ | **$5.250\times$** | $0.0095\text{ b/t}^2$ | $0.200$ *(Clamped)* | ✅ (Hypersonic) |

---

## 7. 개발자 훅 및 믹스인 인젝션 포인트

### `CrossbowItemMixin.java`
```java
@WrapOperation(
    method = "performShooting",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shootProjectile(...)V")
)
private void bettercrossbows$wrapShootProjectile(
    CrossbowItem instance, Level level, LivingEntity shooter,
    InteractionHand hand, ItemStack weapon, ItemStack projectile,
    float soundPitch, boolean isCreative, float power, float uncertainty,
    float soundAngle, @Nullable LivingEntity targetOverride, Operation<Void> original
) {
    float multiplier = bettercrossbows$getShotMultiplier(level, weapon);
    original.call(instance, level, shooter, hand, weapon, projectile,
                  soundPitch, isCreative, power * multiplier, uncertainty, soundAngle, targetOverride);
}
```

### `AbstractArrowMixin.java`
```java
@Inject(method = "getDefaultGravity", at = @At("RETURN"), cancellable = true)
private void bettercrossbows$adjustCrossbowArrowGravity(CallbackInfoReturnable<Double> cir) {
    AbstractArrow arrow = (AbstractArrow) (Object) this;
    ItemStack weapon = arrow.getWeaponItem();
    if (weapon != null && weapon.is(Items.CROSSBOW)) {
        double baseMultiplier = BetterCrossbowsGameRules.getVelocityMultiplier(arrow.level());
        double speedMultiplier = baseMultiplier * (1.0f + 0.25f * ballisticsLevel);
        if (speedMultiplier > 0.0) {
            cir.setReturnValue(cir.getReturnValue() / speedMultiplier);
        }
    }
}
```

> ☕ *1인 개발자 노트*: 현실적인 중충격 탄도학과 시원한 저신장 궤적이 마음에 드신다면, [Ko-fi](https://ko-fi.com/dasikigaijin)에서 개발을 응원해 주세요!

---

## 🔗 관련 문서
- [[Quick Draw Mechanics|ko_kr-26.2-Quick-Draw-Mechanics]]
- [[Configuration & Dynamic GameRules|ko_kr-26.2-Configuration-and-GameRules]]
- [[Architecture & Mixins|ko_kr-26.2-Architecture-and-Mixins]]
- [[Return to Overview Portal|ko_kr-26.2-Home]]
