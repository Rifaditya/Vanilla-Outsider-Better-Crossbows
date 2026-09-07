# 📋 Versionskompatibilität & Toolchain-Matrix

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Quellcode-Hinweis**: Die Dokumentation in diesem Wiki gibt den **aktuellen Stand des Quellcodes im Repository** wieder, einschließlich unfertiger Commits und Entwicklungsfunktionen vor der offiziellen Veröffentlichung auf CurseForge und Modrinth.

---

## 🏛️ Die «1 Jar 1 Version» Richtlinie (1 Jar 1 Version Policy)

**Better Crossbows** folgt strikt der **1 Jar 1 Version Richtlinie**. Jede Zielversion von Minecraft wird in einem eigenständigen Unterprojekt isoliert mit souveränen Abhängigkeiten, Mappings und Build-Artefakten gepflegt:
- **Keine Frankenstein Universal-Jars**: Anstelle fehleranfälliger Laufzeit-Reflexion besitzt jeder Versionsanker einen validierten Build.
- **Dedizierte Releases**: Mod-Artefakte werden pro Anker veröffentlicht (z. B. `better-crossbows-1.0.14+26.3.jar`).
- **Archivierung**: Kompilierte JARs werden im Ordner `Archive Jar of all versions/` aufbewahrt.

---

## 📊 Vollständige technische Kompatibilitätsmatrix

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Aktuell aktiv** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Paritäts-Anker** |

---

## 🧩 Optionale & Empfohlene Abhängigkeiten

Better Crossbows benötigt clientseitig außer Fabric API und DasikLibrary keine zwingenden Abhängigkeiten. Bei installierten optionalen Mods steht jedoch eine interaktive Konfigurationsoberfläche zur Verfügung:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Server- und Client-Seitensicherheit

Der gesamte Client-GUI-Code ist durch das **Screen Isolation Protocol** geschützt:
- `ModMenuIntegration` und `YaclScreenHelper` sind explizit mit `@Environment(EnvType.CLIENT)` annotiert.
- Die Initialisierung erfolgt über `GuiHelper.getOptionalYaclFactory(...)` aus der DasikLibrary.
- Der Einsatz auf einem **dedizierten Server (Dedicated Server)** garantiert 0 Abstürze durch `ClassNotFoundException` oder `NoClassDefFoundError: net/minecraft/client/Minecraft`.

---

## 🗄️ Historische Builds & Archivdateien

Archivierte JARs früherer Versionen befinden sich in:
- `Archive Jar of all versions/`
- Lokale Unterprojekt-Releases: `<Subproject>/releases/`
- Offizielle Modrinth-Releases: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 Navigation

- Zurück zum [[Hauptportal|de_de-Home]]
- [[MC 26.3 Overview|de_de-26.3-Home]]
- [[MC 26.2 Overview|de_de-26.2-Home]]
- [[Developer Setup|de_de-Developer-Setup-and-Building]]
