# 🏹 Minecraft 26.3 — Better Crossbows Portal

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Quellcode-Hinweis**: Die Dokumentation in diesem Wiki gibt den **aktuellen Stand des Quellcodes im Repository** wieder, einschließlich unfertiger Commits und Entwicklungsfunktionen vor der offiziellen Veröffentlichung auf CurseForge und Modrinth.

---

## 🎯 Willkommen im Minecraft 26.3 Dokumentationsportal

Dieses Dokumentationsportal behandelt **Better Crossbows** für **Minecraft 26.3** (`1.0.14+26.3`).

Minecraft 26.3 bietet modernisierte Kampfmechaniken, Java 25 Unterstützung und Fabric API (`0.156.1+26.3`). Better Crossbows verfeinert die Armbrust mit Hochgeschwindigkeitsballistik, flacheren Kurven und anpassbaren GameRules.

---

## 🧭 MC 26.3 Subsystem-Dokumentationsbaum

Entdecke die Handbücher für diese Version:

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|de_de-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|de_de-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|de_de-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|de_de-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Technische Spezifikationen für MC 26.3

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.3` / `26.3-snapshot-6` (`>=26.3-`) |
| **Mod Version** | `1.0.14+26.3` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.01.22` |
| **Subproject Source** | `Better Crossbows v26.3/better-crossbows/` |

---

## 🌟 Versions-Highlights & Neuerungen (26.3)

1. **Mixin-Modernisierung**: Einsatz von MixinExtras `@WrapOperation` für `shootProjectile`, wodurch fehleranfällige Ordinal-Injektionen entfallen.
2. **Echte Sandbox-Freiheit**: Entfernung künstlicher Grenzen in den GameRules gemäß dem Anti-Nanny-Prinzip bis zu `Integer.MAX_VALUE`.
3. **Dedizierte Server-Stabilität**: Strikte Kapselung von Client-Code hinter `@Environment(EnvType.CLIENT)` für Linux-Server.
4. **Sofortige Kreativ-Tab Cache-Bereinigung**: Live-Änderungen an GameRules aktualisieren Zauberbücher sofort ohne Weltneustart.

---

## 🧭 Navigation
- [[Master Home Portal|de_de-Home]]
- [[Version Compatibility|de_de-Version-Compatibility]]
- [[Developer Setup|de_de-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|de_de-26.2-Home]]
