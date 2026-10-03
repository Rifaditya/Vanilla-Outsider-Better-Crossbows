# 🎛️ Master Release Queue: Vanilla Outsider — Better Crossbows

> **Mod Project Master Ground-Truth Document**  
> *Last Synchronized: 2026-10-03*  
> **Modrinth ID**: *Unregistered* | **CurseForge ID**: *Unregistered* | **Lead SemVer**: `1.0.14`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | *(Unreleased)* | `1.0.7+26.3` | 🚀 **Ready for Release** | Minecraft 26.3 port, YACL v3 migration, shootProjectile WrapOperation. |
| **MC 26.2** | Modern Standard | *(Unreleased)* | `1.0.8+26.2` | 🚀 **Ready for Release** | Minecraft 26.2 build, YACL v3 migration, top-pinned Ko-fi button. |
| **MC 26.1.2** | Modern Sovereign | *(Unreleased)* | `1.0.14+26.1.2` | 🚀 **Ready for Release** | Modern anchor parity catch-up, firework velocity scaling, headless tests. |

---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **🔢 Universal Direct SemVer Inheritance**:
   - Modern subprojects share unified SemVer milestone lineage targeting `1.0.14`.
   - Each Minecraft version anchor manages its own organic progression to ensure 100% clean, verified parity.
   - Dedicated `CHANGELOG.md` and `RELEASE_QUEUE.md` are maintained in each version subproject folder to ensure deterministic extraction by the automated platform publisher.

2. **📅 Daily Update Guard**:
   - Strict maximum of 1 release per day per targeted Minecraft version anchor across Modrinth and CurseForge.
