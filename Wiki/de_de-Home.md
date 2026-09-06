# 🏹 Vanilla Outsider: Better Crossbows Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Repository-Quellcode-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Stand des Quellcodes im Repository** wider, der neuere unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Builds auf CurseForge und Modrinth enthalten kann.

---

## 🎯 Willkommen in der offiziellen Dokumentation

**Better Crossbows** ist eine Präzisionskampf- und Mechanik-Mod aus der **Vanilla Outsider Collection**, entwickelt von **Dasik (Rifaditya)**. Sie verwandelt die Armbrust in eine dedizierte **Plattform für schwere Projektile**, bei der kinetische Fluggeschwindigkeit, gestreckte ballistische Flugbahnen und anpassbare Spannzeiten im Vordergrund stehen.

---

## 🧭 Versionsauswahl-Portal

Wähle deine Minecraft-Zielversion für den Zugriff auf den isolierten Dokumentationsbaum:

| Minecraft-Version | Mod-Release | Laufzeitumgebung | Status | Direktes Portal |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 Aktuell | [[👉 MC 26.3 Wiki öffnen|26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 Parität | [[👉 MC 26.2 Wiki öffnen|26.2-Home]] |

---

## 🌟 Übersicht der Kernmechaniken

- **[[Schwere Ballistik und Kinetik|26.3-Heavy-Impact-Ballistics]]**:
  - Armbrustpfeile fliegen standardmäßig mit **1.5-facher Geschwindigkeit** ($150\%$).
  - Dynamische Flugbahnglättung: Die Pfeilgravitation verringert sich proportional zur Geschwindigkeit ($g_{\text{eff}} = g_0 / M$).
  - **Ballistik-Verzauberung (`bettercrossbows:ballistics`)**: +25% Geschwindigkeit pro Stufe (bis zu +125% bei Stufe V); schließt Mehrfachschuss (Multishot) aus.
  - Akustischer Überschallknall und Partikelstoßwellen.
- **[[Schnellspann-Mechanik|26.3-Quick-Draw-Mechanics]]**:
  - Konfigurierbare Nachlade-Ticks im Einklang mit der Vanilla-Verzauberung Schnellladen (Quick Charge).
- **[[Konfiguration und GameRules|26.3-Configuration-and-GameRules]]**:
  - Dynamische GameRules via DasikLibrary (`bettercrossbows:better_crossbows`).
  - Uneingeschränkter Wertebereich (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`) für maximale Administratorfreiheit.
  - Optionale YACL v3 Client-GUI.
- **[[Architektur und Mixins|26.3-Architecture-and-Mixins]]**:
  - Technische Übersicht der Mixin-Injektionen (`CrossbowItemMixin`, `AbstractArrowMixin` usw.).

---

## 📜 Credits und Lizenz

- **Autor**: **Dasik (Rifaditya)**
- **Lizenz**: **GNU General Public License v3.0 (GPLv3)**
- **Quellcode**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
