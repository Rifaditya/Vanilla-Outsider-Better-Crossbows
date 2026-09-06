# 📋 Version Compatibility & Toolchain Matrix

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 🏛️ The "1 Jar 1 Version" Policy

**Better Crossbows** enforces a strict **1 Jar 1 Version Policy**. Each target Minecraft version is isolated into its own independent subproject directory with sovereign dependency bindings, compiler mappings, and build artifacts:
- **No Frankenstein Universal Jars**: Rather than attempting fragile runtime reflection across divergent Minecraft bytecode releases, each version anchor has a strictly validated build.
- **Dedicated Releases**: Mod artifacts are released per anchor (e.g. `better-crossbows-1.0.14+26.3.jar`).
- **Archive Preservations**: Built releases are archived under `Archive Jar of all versions/`.

---

## 📊 Complete Technical Compatibility Matrix

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Active Current** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Parity Anchor** |

---

## 🧩 Optional & Suggested Dependencies

Better Crossbows is designed for zero mandatory client dependencies beyond Fabric API and DasikLibrary. However, rich graphical interfaces are enabled dynamically when optional mods are present:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Server & Client Side-Safety Architecture

All client GUI code is protected under the **Screen Isolation Protocol**:
- `ModMenuIntegration` and `YaclScreenHelper` are explicitly annotated with `@Environment(EnvType.CLIENT)`.
- The entrypoint resolves via `GuiHelper.getOptionalYaclFactory(...)` from DasikLibrary, which isolates YACL class references behind reflection and client guards.
- Running Better Crossbows on a **Dedicated Server** guarantees zero `ClassNotFoundException` or `NoClassDefFoundError: net/minecraft/client/Minecraft` crashes.

---

## 🗄️ Historical Builds & Archives

Archived JARs for previous release builds are stored in:
- `Archive Jar of all versions/`
- Local subproject release staging: `<Subproject>/releases/`
- Official Modrinth releases: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)
