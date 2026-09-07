# 🏹 Minecraft 26.3 — Portal de Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre el código fuente del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes o características en desarrollo no disponibles aún en CurseForge o Modrinth.

---

## 🎯 Bienvenido al centro de documentación de Minecraft 26.3

Este centro de documentación cubre **Better Crossbows** para **Minecraft 26.3** (`1.0.14+26.3`).

Minecraft 26.3 presenta mecánicas de combate modernizadas, compatibilidad con Java 25 y Fabric API (`0.156.1+26.3`). Better Crossbows perfecciona la ballesta con balística cinética de alta velocidad, trayectorias rasantes y GameRules configurables.

---

## 🧭 Árbol de documentación de subsistemas MC 26.3

Explora las guías dedicadas para esta versión:

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|es_es-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|es_es-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|es_es-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|es_es-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Especificaciones técnicas de compilación MC 26.3

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

## 🌟 Aspectos destacados y novedades (26.3)

1. **Modernización de Mixins**: Empleo de MixinExtras `@WrapOperation` en `shootProjectile`, eliminando inyecciones ordinales frágiles.
2. **Auténtica libertad de juego**: Eliminación de techos artificiales en GameRules conforme al principio anti-nanny, permitiendo valores hasta `Integer.MAX_VALUE`.
3. **Robustez en servidores dedicados**: Aislamiento total de comprobaciones monojugador tras `@Environment(EnvType.CLIENT)` para servidores Linux.
4. **Vaciado inmediato de caché creativo**: Los cambios de GameRules invalidan la caché de `CreativeModeTabs` al instante.

---

## 🧭 Navigation
- [[Master Home Portal|es_es-Home]]
- [[Version Compatibility|es_es-Version-Compatibility]]
- [[Developer Setup|es_es-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|es_es-26.2-Home]]
