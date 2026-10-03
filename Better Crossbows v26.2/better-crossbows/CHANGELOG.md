# Changelog: Vanilla Outsider — Better Crossbows (MC 26.2)

## [1.0.14+26.2] - 2026-09-05

### Changed
- **DasikLibrary 1.8.39 Alignment**: Upgraded to DasikLibrary 1.8.39, adopting formal `@DasikApiStatus` / `@APIDasikStatus` API governance and client side-safety architecture.
- **License Normalization**: Standardized single-line GPLv3 headers across all source files.

## [1.0.13+26.2] - 2026-09-05

### Fixed
- **Client Side-Safety Annotations**: Annotated `YaclScreenHelper` and `ModMenuIntegration` with `@Environment(EnvType.CLIENT)`, eliminating dedicated server classloading hazards and aligning with the Client Side-Safety Standard.

## [1.0.12+26.2] - 2026-09-05

### Changed
- **Mixin Modernization**: Replaced fragile ordinal `@ModifyVariable(argsOnly = true, ordinal = 0)` on `performShooting` with MixinExtras `@WrapOperation` on `shootProjectile`, guaranteeing exact velocity scaling at the arrow launch invocation without local variable ordinal ambiguity.

## [1.0.11+26.2] - 2026-09-05

### Changed
- **Player Agency & True Sandbox Freedom Restoration**: Removed artificial clamps across all crossbow GameRules (`CROSSBOW_BALLISTICS_MAX_LEVEL`, `CROSSBOW_VELOCITY_MULTIPLIER`, `CROSSBOW_FIREWORK_MULTIPLIER`, and `CROSSBOW_RELOAD_TICKS`), unlocking values from `Integer.MIN_VALUE` to `Integer.MAX_VALUE` in accordance with the Player Agency & Anti-Nanny Invariant. Negative multipliers enable backwards rocket and arrow physics; reload ticks are safely handled by `Math.max(1, ...)` for instant firing.

## [1.0.10+26.2] - 2026-09-05

### Fixed
- **Dedicated Server Crash Elimination**: Isolated singleplayer ballistics cap resolution behind client-side `@Environment(EnvType.CLIENT)` helper (`BetterCrossbowsClientHelper`), preventing dedicated server `NoClassDefFoundError: net/minecraft/client/Minecraft` crashes.
- **GameRule & Config Bounds Clamping**: Added strict range clamps across all 4 integer GameRules (`.range(1, 255)` for ballistics, `.range(10, 1000)` for velocity and fireworks, `.range(0, 200)` for reload ticks) and config validation, eliminating negative velocity and inverted gravity bugs.

## [1.0.9+26.2] - 2026-09-05

### Changed
- **Config Polish**: Cleaned up config warning descriptions in localization and finalized settings screen layout.

## [1.0.8+26.2] - 2026-09-05

### Added
- **YACL v3 & Ko-fi Support**: Migrated configuration GUI from Cloth Config to YetAnotherConfigLib v3 (YACL). Injected top-pinned Ko-fi creator support button. Aligned toolchain to Minecraft 26.2 and DasikLibrary v1.8.38.
