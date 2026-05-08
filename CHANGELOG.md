# Changelog

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
