# 🔍 Multi-Era Anchor Parity Audit Report

> **Generated**: 2026-09-27 07:49:36  
> **Target Mod Root**: `E:\Minecraft Project\Vanilla Outsider Collections\Better Crossbows`  
> **Modern Lead Anchor**: `Better Crossbows v26.3 (MC 26.3)`  
> **Parity Status**: ✅ **100% Multi-Era Parity** (0 lagging anchor(s) with feature deltas)  

---

## 📊 1. Multi-Era Parity Matrix

| Anchor Directory | MC Anchor | Mod Version | GameRules | AI Goals | Handlers/Helpers | Mixins | Tests | Published (Live) | Status | Parity Gap |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Better Crossbows v26.1** | `26.1.2` | `1.0.14+26.1.2` | `5` | `0` | `1` | `6` | `1` | `1.0.0+build.2` | Active | **✅ In Parity** |
| **Better Crossbows v26.2** | `26.2` | `1.0.14+26.2` | `5` | `0` | `1` | `6` | `1` | `1.0.0+build.2` | Active | **✅ In Parity** |
| **Better Crossbows v26.3** | `26.3` | `1.0.14+26.3` | `5` | `0` | `1` | `6` | `1` | `1.0.7-26.1` | ⏸️ Parity Hold | **✅ Lead Baseline** |

---

## 🧩 2. Subsystem Distribution Table

| Subsystem Classification | `26.1.2` | `26.2` | `26.3` | Lead Baseline (`26.3`) |
| :--- | :--- | :--- | :--- | :--- |
| **`ai`** | 0 | 0 | **0** |
| **`util/handler`** | 1 | 1 | **1** |
| **`mixin`** | 6 | 6 | **6** |
| **`advancement`** | 0 | 0 | **0** |
| **`config`** | 2 | 2 | **2** |
| **`compat`** | 2 | 2 | **2** |
| **`scheduler/listener`** | 0 | 0 | **0** |
| **`test`** | 1 | 1 | **1** |
| **`core`** | 2 | 2 | **2** |

---

## 🔎 3. Detailed Parity Gaps & Delta Breakdown

🎉 **Zero Parity Gaps Detected!** All anchors are 100% synchronized with the lead baseline.

---

## 🧹 4. Dead / Unreferenced Legacy Class Candidates

Classes present in `src/main/java/` that are unreferenced across other Java classes, `fabric.mod.json`, and `*.mixins.json`:

✅ **No unreferenced legacy classes detected.**

---

## 📋 5. Release Queue & Parity Hold Audit

| Anchor | Parity Hold Note | Latest Candidate (`- [ ]`) | Latest Published (`- [x]`) |
| :--- | :--- | :--- | :--- |
| `26.1.2` | *None* | - [ ] **1.0.14+26.1.2** - Modern Parity Catch-Up (... | - [x] **`1.0.0+build.2`** (2026-05-08) - - Fixed M... |
| `26.2` | *None* | - [ ] **`1.0.9+26.2`** (2026-09-05) - Clean up con... | - [x] **`1.0.0+build.2`** (2026-05-08) - - Fixed M... |
| `26.3` | # 📋 Better Crossbows 26.3 Release Queue & Parity Hold | - [ ] **1.0.14+26.3** | - [x] **`1.0.7-26.1`** (2026-06-14) - - **Separate... |

---

## 🎯 6. Actionable Parity Remediation Roadmap

✅ All anchors are in lockstep parity. Release queues may proceed per daily release schedule.
