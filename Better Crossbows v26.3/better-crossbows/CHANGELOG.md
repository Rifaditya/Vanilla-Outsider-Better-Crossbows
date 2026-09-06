# Changelog

## [1.0.14+26.3] - 2026-09-05

### Changed
- **DasikLibrary 1.8.39 Alignment**: Upgraded to DasikLibrary 1.8.39, adopting formal `@DasikApiStatus` / `@APIDasikStatus` API governance and client side-safety architecture.
- **License Normalization**: Standardized single-line GPLv3 headers across all source files.

## [1.0.13+26.3] - 2026-09-05

### Fixed
- **Client Side-Safety Annotations**: Annotated `YaclScreenHelper` and `ModMenuIntegration` with `@Environment(EnvType.CLIENT)`, eliminating dedicated server classloading hazards and aligning with the Client Side-Safety Standard.

## [1.0.12+26.3] - 2026-09-05

### Changed
- **Mixin Modernization**: Replaced fragile ordinal `@ModifyVariable(argsOnly = true, ordinal = 0)` on `performShooting` with MixinExtras `@WrapOperation` on `shootProjectile`, guaranteeing exact velocity scaling at the arrow launch invocation without local variable ordinal ambiguity.

## [1.0.11+26.3] - 2026-09-05

### Changed
- **Player Agency & True Sandbox Freedom Restoration**: Removed artificial clamps across all crossbow GameRules (`CROSSBOW_BALLISTICS_MAX_LEVEL`, `CROSSBOW_VELOCITY_MULTIPLIER`, `CROSSBOW_FIREWORK_MULTIPLIER`, and `CROSSBOW_RELOAD_TICKS`), unlocking values from `Integer.MIN_VALUE` to `Integer.MAX_VALUE` in accordance with the Player Agency & Anti-Nanny Invariant. Negative multipliers enable backwards rocket and arrow physics; reload ticks are safely handled by `Math.max(1, ...)` for instant firing.

## [1.0.10]

### Fixed
- **Dedicated Server Crash Elimination**: Isolated singleplayer ballistics cap resolution behind client-side `@Environment(EnvType.CLIENT)` helper (`BetterCrossbowsClientHelper`), preventing dedicated server `NoClassDefFoundError: net/minecraft/client/Minecraft` crashes.
- **GameRule & Config Bounds Clamping**: Added strict range clamps across all 4 integer GameRules (`.range(1, 255)` for ballistics, `.range(10, 1000)` for velocity and fireworks, `.range(0, 200)` for reload ticks) and config validation, eliminating negative velocity and inverted gravity bugs.

## [1.0.7-26.1] - 2026-06-14

### Added
- **Separate Firework Velocity Scaling**: Introduced a separate velocity multiplier GameRule and Config option specifically for Firework Rockets (`bettercrossbows:crossbow_firework_multiplier`), allowing arrows and fireworks to be scaled independently.

## [1.0.6-26.1] - 2026-06-14

### Fixed
- **Flattened Trajectory (Arc Fix)**: Implemented `AbstractArrowMixin` to dynamically scale down gravity for arrows fired from crossbows, aligning the vertical drop shape (arc) with their increased velocity, resulting in a flatter precision sniper trajectory.

## [1.0.5-26.1] - 2026-06-14

### Changed
- **Dynamic Sonic Juice Scaling**: Firing high-velocity crossbow projectiles now dynamically scales the sonic boom crack sound pitch, cloud particle count, and cloud particle speed based on the arrow's final power. Firing at extreme velocities (velocity ratio > 2.0x) spawns extra gust particles.

## [1.0.4-26.1] - 2026-06-14

### Added
- **Optional Client-Side GUI**: Implemented optional client-side configuration GUI using Cloth Config and ModMenu integration.
- **Dedicated Server Crash Protection**: Implemented the Screen Isolation Protocol to ensure all Cloth Config screen API calls are isolated in helper classes and lazily loaded, preventing ClassNotFoundExceptions on dedicated servers.

## [1.0.3-26.1] - 2026-06-14

### Added
- **Config JSON File Integration**: Added support for global JSON configuration file (`config/bettercrossbows.json`).
- **Dynamic GameRules Initialization**: GameRules are now initialized using defaults loaded from the persistent config JSON rather than hardcoded numbers.

## [1.0.2-26.1] - 2026-06-14

### Changed
- **Mod Versioning Standard**: Aligned mod versioning scheme to `1.0.2-26.1` (SemVer with target Minecraft Drop 26.1).

### Fixed
- **Codebase Cleanup**: Removed unused/dead `BALLISTICS` static field and empty `register()` method in `BetterCrossbowsEnchantments.java`.

## [1.0.0+build.12] - 2026-05-10

### Added
- **Instant Creative Menu Update**: The Creative Menu now instantly reflects dynamic changes to the `crossbow_ballistics_max_level` GameRule when opened, without requiring the player to rejoin or use `/reload`. This is achieved by tracking GameRule changes and forcefully clearing vanilla's `CACHED_PARAMETERS` for creative tabs when a change is detected.

## [1.0.0+build.11] - 2026-05-10

### Fixed
- **Creative Menu Blank Enchanted Books**: Fixed an issue where enchanted books for Ballistics generated in the Creative Menu would appear blank ("Enchanted Book" without the "Ballistics V" lore). This occurred because `DynamicGameRuleManager` defaults to returning `0` when queried by a `ClientLevel` (multiplayer/UI), causing the book to generate with Level 0. The mixin now safely extracts the `ServerLevel` in singleplayer to fetch the live GameRule, and correctly falls back to the data-driven max level in multiplayer to prevent invalid Level 0 book generation.

## [1.0.0+build.10] - 2026-05-10

### Added
- **Enchanting Table Cap** (`EnchantmentMenuMixin`): The Enchanting Table now dynamically respects the `crossbow_ballistics_max_level` GameRule when generating enchantments for crossbows or books.

### Fixed
- **Quick Charge Incompatibility**: `CrossbowItemMixin` no longer breaks the vanilla Quick Charge enchantment. It now calculates the exact tick reduction applied by Quick Charge and correctly subtracts it from the new configurable base reload duration.

### Concept Coverage ⭐ NEW
- Features implemented: 3/3 (100%)
- Missing: None

## [1.0.0+build.9] - 2026-05-10

### Fixed
- **Creative tab Ballistics cap (parent tab)**: Also hooked `generateEnchantmentBookTypesOnlyMaxLevel` which controls the single max-level book shown in the Ingredients tab. Previous build.8 only hooked the search tab variant, leaving the parent tab still showing level 255.
- **Shooting crash**: `NoClassDefFoundError: ProjectileEffectHelper` — requires DasikLibrary `1.6.9+build.24+`. Update the library jar in your mods folder.

## [1.0.0+build.8] - 2026-05-10

### Added
- **Creative tab Ballistics cap** (`CreativeModeTabsMixin`): Enchanted books in the creative/search tab now only show up to the `crossbow_ballistics_max_level` GameRule value (default 5). Commands (`/enchant`, `/give`) are unaffected and can still exceed the cap freely, consistent with vanilla enchantment behavior.

## [1.0.0+build.7] - 2026-05-10

### Fixed
- **CrossbowItemMixin `@ModifyVariable` signature**: Handler now correctly declares all method parameters as context (`float power, float uncertainty`) matching the `performShooting(Level, LivingEntity, InteractionHand, ItemStack, float, float, LivingEntity)` signature in 26.1.2.


### Fixed
- **AnvilMenuMixin crash**: Replaced invalid `@Shadow` on inherited `player` field (declared in `ItemCombinerMenu`) with an `@Accessor` interface (`ItemCombinerMenuAccessor`). `@Shadow` cannot target fields from parent classes.
- **CrossbowItemMixin crash**: Updated `@ModifyVariable` and `@Inject` handlers for `performShooting` to match the 26.1.2 signature. The `List<ItemStack> projectiles` parameter was removed from `performShooting`; projectiles are now read internally from `DataComponents.CHARGED_PROJECTILES`.


## [1.0.0+build.5] - 2026-05-08

### Changed
- Refactored `AnvilMenuMixin` and `CrossbowItemMixin` to delegate their logic to `DasikLibrary` for better code reuse ("Thin Mod, Fat Library" architecture).
- Bumped DasikLibrary dependency to `v1.6.9+build.24`.

## [1.0.0+build.4] - 2026-05-08

### Fixed
- Fixed critical Mixin crash by adding missing `refmap` to `bettercrossbows.mixins.json`.
- Improved `AnvilMenuMixin` stability by switching from `access` field to `player` field shadow.

## [1.0.0+build.3] - 2026-05-08

### Added
- Added `crossbow_ballistics_max_level` GameRule to dynamically configure the max level of the Ballistics enchantment (Default: 5).
- Ballistics enchantment now scales up to Level 255 in data, with survival combinations capped by the GameRule via Mixin.

### Fixed
- Fixed Ballistics velocity scaling logic to correctly apply a +25% increase per level.
- Fixed Sonic Juice effect threshold to be relative to base velocity, ensuring it triggers correctly on 1.21+.
- Improved Sonic Juice sound logic with client-side prediction for zero latency.

## [1.0.0+build.2] - 2026-05-08

### Fixed
- Fixed Modrinth accessibility rejection by removing hidden `\x08` (backspace) control characters from Modrinth and CurseForge description pages, and README.md.
- Fixed critical game crash (`Unbound tags in registry`) by defining the missing `minecraft:exclusive_set/multishot` enchantment tag.
- Corrected Loom configuration `defaultRefmapName` to point to `bettercrossbows-refmap.json`.
