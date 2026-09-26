# Architecture & Symbol Index: Better Crossbows

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `bettercrossbows`
- **Main Entrypoint**: `net.vanillaoutsider.bettercrossbows.BetterCrossbows` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `None`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.vanillaoutsider.bettercrossbows.mixin.CrossbowItemMixin` | Core mixin hook |
| `Vanilla Class` | `net.vanillaoutsider.bettercrossbows.mixin.AnvilMenuMixin` | Core mixin hook |
| `Vanilla Class` | `net.vanillaoutsider.bettercrossbows.mixin.ItemCombinerMenuAccessor` | Core mixin hook |
| `Vanilla Class` | `net.vanillaoutsider.bettercrossbows.mixin.CreativeModeTabsMixin` | Core mixin hook |
| `Vanilla Class` | `net.vanillaoutsider.bettercrossbows.mixin.EnchantmentMenuMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`bettercrossbows:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
