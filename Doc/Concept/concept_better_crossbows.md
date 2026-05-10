# Better Crossbows

## Vision
Transform the Crossbow from a "slower bow" into a **Heavy-Impact Projectile Platform**. This mod emphasizes the "Sniper" and "Siege" identity of the crossbow, making it a viable high-tier alternative to the standard Bow by prioritizing terminal velocity and kinetic impact over rate of fire.

---

## 1. Core Mechanics

### 1.1 Kinetic Acceleration (Terminal Velocity)
- **Description**: Arrows fired from a Crossbow travel at a significantly higher velocity by default. In Minecraft 26.x, arrow damage is calculated based on the velocity vector magnitude. This mod leverages this to provide a natural "power" buff without arbitrary damage modifiers.
- **Default Multiplier**: `1.5x` (3.15 -> 4.725 velocity).
- **Juice (IG)**: High-velocity shots (velocity > 4.0) trigger a "Sonic Crack" sound effect and spawn a brief trail of `cloud` and `crit` particles to emphasize the power.
- **Implementation**: Mixin into `CrossbowItem#shootProjectile` to intercept the `power` parameter before it is passed to `projectileEntity#shoot`.

### 1.2 The "Ballistics" Enchantment
- **Description**: A new unique enchantment for Crossbows that further optimizes projectile flight.
- **Dynamic Max Level**: Capped by the `crossbow_ballistics_max_level` GameRule (Default: 5). 
  - **Enchanting Table**: The max achievable level offered is directly capped by the GameRule (e.g., if set to 3, you cannot roll > 3).
  - **Anvil Combining**: Combining books or crossbows safely caps the resulting enchantment level to the GameRule, preventing circumvention.
  - **Creative Menu**: The creative tab dynamic book generation reflects this cap in real-time.
- **Effect**: Increases velocity per level.
- **Conflict**: Incompatible with **Multishot**. This forces the player to choose between "Area Denial" (Multishot) and "Precision Sniper" (Ballistics).
- **Implementation**: Registry entry for `bettercrossbows:ballistics` (via Datapack in 26.x). Logic checked dynamically via GameRules in `EnchantmentMenuMixin`, `AnvilMenuMixin`, and `CreativeModeTabsMixin`.

### 1.3 Variable Tension (Reload Logic)
- **Description**: The base reload duration of a crossbow is now configurable. This allows players to balance the high-velocity "Sniper" feel with a longer reload time, or go for a "Rapid Fire" build.
- **Implementation**: Mixin into `CrossbowItem#getChargeDuration`. Instead of returning the hardcoded `1.25f` (25 ticks), it fetches the value from the `crossbow_reload_ticks` GameRule.
- **Scaling**: Fully compatible with the vanilla `Quick Charge` enchantment, which subtracts 5 ticks per level from the base.

---

## 2. Configuration (GameRules)

Integrated with **DasikLibrary** for real-time synchronization:

| GameRule | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `crossbow_ballistics_max_level` | Integer | `5` | Maximum obtainable level for the Ballistics enchantment. |
| `crossbow_velocity_multiplier` | Integer | `150` | Multiplier applied to the base power of arrows (in percent, 150 = 1.5x). |
| `crossbow_reload_ticks` | Integer | `25` | Base duration in ticks to charge a crossbow. |
| `crossbow_enable_juice` | Boolean | `true` | When true, high-velocity shots produce particles and sonic crack sounds. |

---

## 3. Technical Implementation Details

### 3.1 Mixin Targets
- **Target**: `net.minecraft.world.item.CrossbowItem`
- **Method**: `shootProjectile(Level, LivingEntity, InteractionHand, ItemStack, float, float, LivingEntity)`
    - **Action**: Multiply `power` (float) by the GameRule value.
- **Method**: `getChargeDuration(ItemStack, LivingEntity)`
    - **Action**: Replace `1.25f` with `(float)GameRule / 20.0f`.
- **Target**: `net.minecraft.world.item.CreativeModeTabs`
    - **Method**: Intercept `Enchantment.getMaxLevel()` or similar to dynamically cap creative menu books.

### 3.2 Enchantment Registry
- **Namespace**: `bettercrossbows`
- **Key**: `ballistics`
- **Weight**: `RARE`

---

## 4. Quality Assurance (QA)

### 4.1 Debugging Commands
- `/gamerule bettercrossbows:crossbow_velocity_multiplier 500`: Extreme velocity testing (should result in near-instant travel across render distance).
- `/gamerule bettercrossbows:crossbow_reload_ticks 1`: Instant reload testing.
- `/gamerule bettercrossbows:crossbow_ballistics_max_level 10`: Test dynamic enchantment capping.

### 4.2 Test Cases
1. **The Drop Test**: Shoot at a target 100 blocks away. Vanilla Crossbow requires high aiming; Better Crossbow should hit with almost zero drop.
2. **The Damage Test**: Verify that high-velocity arrows deal more damage than vanilla (due to the velocity-based damage calc).
3. **The Multishot Conflict**: Ensure `Ballistics` cannot be applied to a `Multishot` crossbow via anvil.
4. **Dynamic Capping**: Verify changing the max level GameRule automatically updates creative tab displays.

---

## 5. Assets Needed
- **Sounds**: A crisp, low-frequency "thwack" for the sonic crack.
- **Particles**: Standard `cloud` and `crit` are sufficient, but a custom `bolt_trail` could be added in the future.
