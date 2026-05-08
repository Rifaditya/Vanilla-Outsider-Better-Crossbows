# Audit Helper for Moderators

## Summary
Better Crossbows modifies the behavior of the vanilla CrossbowItem to support high-velocity ballistics and configurable reload speeds.

## Technical Details
- **Mixins**:
  - CrossbowItemMixin: Injects into performShooting to modify projectile power and into getChargeDuration for reload timing.
- **Registries**:
  - ettercrossbows:ballistics: Data-driven enchantment.
  - GameRules registered via DynamicGameRuleManager (DasikLibrary).

## Privacy & Network
- No external connections.
- No personal data collected.
- No network packets sent (Standard vanilla projectile syncing used).
