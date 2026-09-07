# 🏹 Minecraft 26.2 — Better Crossbows Portal

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Quellcode-Hinweis**: Die Dokumentation in diesem Wiki gibt den **aktuellen Stand des Quellcodes im Repository** wieder, einschließlich unfertiger Commits und Entwicklungsfunktionen vor der offiziellen Veröffentlichung auf CurseForge und Modrinth.

---

## 🎯 Willkommen im Minecraft 26.2 Dokumentationsportal

Dieses Portal behandelt **Better Crossbows** für **Minecraft 26.2** (`1.0.14+26.2`).

Minecraft 26.2 dient als Paritäts-Anker für 26.x Kampfmechaniken unter Java 25 und Fabric API (`0.149.0+26.2`). Better Crossbows verwandelt die Armbrust in eine schwere Projektil-Plattform mit flacher Flugbahn.

---

## 🧭 MC 26.2 Subsystem-Dokumentationsbaum

Entdecke die Handbücher für diese Version:

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|de_de-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|de_de-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|de_de-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|de_de-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Technische Spezifikationen für MC 26.2

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.2` (`>=26.2-`) |
| **Mod Version** | `1.0.14+26.2` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.149.0+26.2` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.02.15` |
| **Subproject Source** | `Better Crossbows v26.2/better-crossbows/` |

---

## 🌟 Versions-Highlights & Neuerungen (26.2)

1. **Mixin-Modernisierung**: Einsatz von MixinExtras `@WrapOperation` für `shootProjectile`, wodurch fehleranfällige Ordinal-Injektionen entfallen.
2. **Echte Sandbox-Freiheit**: Entfernung künstlicher Grenzen in den GameRules gemäß dem Anti-Nanny-Prinzip bis zu `Integer.MAX_VALUE`.
3. **Dedizierte Server-Stabilität**: Strikte Kapselung von Client-Code hinter `@Environment(EnvType.CLIENT)` für Linux-Server.
4. **Sofortige Kreativ-Tab Cache-Bereinigung**: Live-Änderungen an GameRules aktualisieren Zauberbücher sofort ohne Weltneustart.

---

## 🧭 Navigation
- [[Master Home Portal|de_de-Home]]
- [[Version Compatibility|de_de-Version-Compatibility]]
- [[Developer Setup|de_de-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|de_de-26.3-Home]]
