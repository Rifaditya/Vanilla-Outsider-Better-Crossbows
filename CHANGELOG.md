# Changelog

## [1.0.0+build.2] - 2026-05-08

### Fixed
- Fixed Modrinth accessibility rejection by removing hidden `\x08` (backspace) control characters from Modrinth and CurseForge description pages, and README.md.
- Fixed critical game crash (`Unbound tags in registry`) by defining the missing `minecraft:exclusive_set/multishot` enchantment tag.
- Corrected Loom configuration `defaultRefmapName` to point to `bettercrossbows-refmap.json`.
