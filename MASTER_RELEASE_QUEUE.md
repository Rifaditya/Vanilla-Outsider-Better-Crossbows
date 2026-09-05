# 🎛️ Master Release Queue: Vanilla Outsider — Better Crossbows

> **Mod Project Master Ground-Truth Document**  
> *Last Synchronized: 2026-09-01*  
> **Modrinth ID**: *Unregistered* | **CurseForge ID**: *Unregistered* | **Lead SemVer**: `1.0.8`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | — | `1.0.8+26.3` | ⏸️ **Parity Hold** | Compiled modern lead build held in archive pending MC 26.2 launch of 1.0.8. |
| **MC 26.2** | Modern Standard | *(Unreleased)* | `1.0.8+26.2` | 🛠️ **Local Development** | YACL v3 migration, top-pinned Ko-fi button, compiled for MC 26.2. |

---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **🔢 Universal Direct SemVer Inheritance**:
   - Modern subprojects share unified SemVer milestone lineage targeting `1.0.7`.
   - Each Minecraft version anchor manages its own organic progression to ensure 100% clean, verified parity.

2. **📅 Daily Update Guard**:
   - Strict maximum of 1 release per day per targeted Minecraft version anchor across Modrinth and CurseForge.

---

## 🛠️ CLI Publisher Commands for Better Crossbows

```powershell
# 1. Check current status across all targeted Minecraft versions
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Better Crossbows" --status

# 2. Publish next sequential batch across all active versions
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Better Crossbows" --publish-next --yes

```
