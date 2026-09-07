# 📋 Compatibilité des versions et matrice des outils

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Clause de non-responsabilité relative au code source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, incluant d'éventuels commits récents non publiés sur CurseForge ou Modrinth.

---

## 🏛️ Politique « 1 Jar 1 Version » (1 Jar 1 Version Policy)

**Better Crossbows** applique strictement la **politique 1 Jar 1 Version**. Chaque version cible de Minecraft est isolée dans son propre sous-projet indépendant avec ses propres dépendances et fichiers compilés :
- **Zéro Jar universel hybride** : Plutôt que d'employer une réflexion instable à l'exécution, chaque version dispose d'un binaire validé.
- **Fichiers dédiés** : Les artefacts sont publiés par version (ex. `better-crossbows-1.0.14+26.3.jar`).
- **Préservation d'archives** : Les JARs compilés sont archivés dans `Archive Jar of all versions/`.

---

## 📊 Matrice technique complète de compatibilité

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Actif principal** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Ancre de parité** |

---

## 🧩 Dépendances optionnelles et recommandées

Better Crossbows ne nécessite aucune dépendance client obligatoire en dehors de Fabric API et DasikLibrary. Toutefois, l'installation de mods optionnels débloque un menu de configuration graphique complet :

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Architecture de sécurité Client / Serveur

L'ensemble du code d'interface graphique client est protégé par le **Screen Isolation Protocol** :
- `ModMenuIntegration` et `YaclScreenHelper` sont annotés avec `@Environment(EnvType.CLIENT)`.
- Le point d'entrée est résolu via `GuiHelper.getOptionalYaclFactory(...)` de DasikLibrary.
- L'exécution de Better Crossbows sur un **serveur dédié (Dedicated Server)** garantit 0 crash `ClassNotFoundException` ou `NoClassDefFoundError: net/minecraft/client/Minecraft`.

---

## 🗄️ Historique des versions et archives

Les fichiers JAR archivés des versions précédentes sont conservés dans :
- `Archive Jar of all versions/`
- Sous-projet local : `<Subproject>/releases/`
- Page officielle Modrinth : [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 Navigation

- Retour au [[Portail d'accueil|fr_fr-Home]]
- [[MC 26.3 Overview|fr_fr-26.3-Home]]
- [[MC 26.2 Overview|fr_fr-26.2-Home]]
- [[Developer Setup|fr_fr-Developer-Setup-and-Building]]
