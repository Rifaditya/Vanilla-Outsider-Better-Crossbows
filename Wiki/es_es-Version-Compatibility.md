# 📋 Compatibilidad de versiones y matriz de herramientas

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre el código fuente del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes o características en desarrollo no disponibles aún en CurseForge o Modrinth.

---

## 🏛️ Política «1 Jar 1 Versión» (1 Jar 1 Version)

**Better Crossbows** aplica una estricta **Política de 1 Jar 1 Versión**. Cada versión objetivo de Minecraft está aislada en su propio subproyecto independiente con dependencias soberanas, mapeos de compilador y artefactos de compilación:
- **Sin Jars universales Frankenstein**: En lugar de recurrir a reflexión inestable en tiempo de ejecución, cada versión ancla cuenta con una compilación estrictamente validada.
- **Lanzamientos dedicados**: Los artefactos se publican por versión ancla (ej. `better-crossbows-1.0.14+26.3.jar`).
- **Preservación en archivo**: Los archivos compilados se almacenan bajo `Archive Jar of all versions/`.

---

## 📊 Matriz técnica completa de compatibilidad

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Activo actual** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Ancla de paridad** |

---

## 🧩 Dependencias opcionales y recomendadas

Better Crossbows no requiere dependencias cliente obligatorias más allá de Fabric API y DasikLibrary. Sin embargo, al instalar mods opcionales se habilitan pantallas de configuración gráfica interactivas:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Arquitectura de seguridad cliente/servidor

Todo el código de interfaz cliente está protegido bajo el **Protocolo de aislamiento de pantallas (Screen Isolation Protocol)**:
- `ModMenuIntegration` y `YaclScreenHelper` están anotados explícitamente con `@Environment(EnvType.CLIENT)`.
- El punto de entrada se resuelve mediante `GuiHelper.getOptionalYaclFactory(...)` de DasikLibrary, aislando YACL tras reflexión y comprobaciones de entorno.
- Ejecutar Better Crossbows en un **Servidor Dedicado (Dedicated Server)** garantiza 0 bloqueos por `ClassNotFoundException` o `NoClassDefFoundError: net/minecraft/client/Minecraft`.

---

## 🗄️ Historial de versiones y archivos archivados

Los JARs archivados de versiones anteriores se conservan en:
- `Archive Jar of all versions/`
- Directorio de versiones del subproyecto: `<Subproject>/releases/`
- Versiones oficiales en Modrinth: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 Navegación

- Volver al [[Portal de Inicio|es_es-Home]]
- [[MC 26.3 Overview|es_es-26.3-Home]]
- [[MC 26.2 Overview|es_es-26.2-Home]]
- [[Developer Setup|es_es-Developer-Setup-and-Building]]
