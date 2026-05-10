# Changelog

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
