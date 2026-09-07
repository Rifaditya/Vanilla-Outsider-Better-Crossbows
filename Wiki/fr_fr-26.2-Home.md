# 🏹 Minecraft 26.2 — Portail Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Clause de non-responsabilité relative au code source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, incluant d'éventuels commits récents non publiés sur CurseForge ou Modrinth.

---

## 🎯 Bienvenue sur le portail Minecraft 26.2

Ce portail détaille **Better Crossbows** pour **Minecraft 26.2** (`1.0.14+26.2`).

Minecraft 26.2 représente la version de parité pour les combats 26.x avec Java 25 et Fabric API (`0.149.0+26.2`). Better Crossbows transforme l'arbalète en plateforme d'impact lourd avec flèches rapides et trajectoires tendues.

---

## 🧭 Arborescence de la documentation MC 26.2

Consultez les guides spécifiques à cette version :

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|fr_fr-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|fr_fr-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|fr_fr-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|fr_fr-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Spécifications techniques MC 26.2

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

## 🌟 Points clés et nouveautés (26.2)

1. **Modernisation des Mixins** : Utilisation de MixinExtras `@WrapOperation` sur `shootProjectile`, éliminant les injections ordinales fragiles.
2. **Liberté totale du joueur** : Suppression des limites artificielles dans les GameRules jusqu'à `Integer.MAX_VALUE`.
3. **Sécurité sur serveurs dédiés** : Isolation complète du code client sous `@Environment(EnvType.CLIENT)` pour serveurs Linux sans interface.
4. **Actualisation immédiate des onglets Créatif** : Les modifications de GameRules invalident le cache instantanément.

---

## 🧭 Navigation
- [[Master Home Portal|fr_fr-Home]]
- [[Version Compatibility|fr_fr-Version-Compatibility]]
- [[Developer Setup|fr_fr-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|fr_fr-26.3-Home]]
