# 📜 Better Crossbows Technical History & Developer Ledger

This ledger tracks internal technical backlog tickets, architecture decisions, and developer specifications.

---

## 🏛️ Resolved Technical & Parity Milestones

### [BL-BC-PARITY-001] Modern 26.1.2 Anchor Parity Catch-Up (`CROSSBOW_FIREWORK_MULTIPLIER`)
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED` (2026-09-27)
- **Target Component(s)**: `BetterCrossbowsGameRules.java`, `FireworkDamageHelper.java`, `BetterCrossbowsTest.java` in `Vanilla Outsider Collections\Better Crossbows\Better Crossbows v26.1\better-crossbows`
- **Resolution**: Ported `CROSSBOW_FIREWORK_MULTIPLIER`, `FireworkDamageHelper`, headless unit tests, and full modern parity to `Better Crossbows v26.1\better-crossbows`. Compiled `better-crossbows-1.0.14+26.1.2.jar` with 100% test pass.

### [BL-BC-PARITY-002] Automated Parity Verification & Lift 26.3 Parity Hold
- **Category**: `[TECH_DEBT]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED` (2026-09-27)
- **Target Component(s)**: `Better Crossbows v26.3/better-crossbows/RELEASE_QUEUE.md`, `PARITY_AUDIT.md`
- **Resolution**: Ran `anchor_parity_auditor.py --path "Better Crossbows" --export`. Confirmed 100% parity across all 3 anchors (`26.1.2`, `26.2`, `26.3`). Lifted Parity Hold in `Better Crossbows v26.3\better-crossbows\RELEASE_QUEUE.md` and queued `1.0.14+26.3`.
