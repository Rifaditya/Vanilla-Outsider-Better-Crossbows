# 🏹 Vanilla Outsider: Better Crossbows Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre el código fuente del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes o características en desarrollo no disponibles aún en CurseForge o Modrinth.

---

## 🎯 Bienvenido a la documentación oficial

**Better Crossbows** es un mod de combate y balística perteneciente a la colección **Vanilla Outsider Collection**, diseñado por **Dasik (Rifaditya)**. Transforma la ballesta en una auténtica **plataforma de proyectiles de impacto pesado**, dando prioridad a la velocidad cinética, trayectorias rasantes y tiempos de tensión modulables en lugar de aumentos artificiales de daño.

---

## 🧭 Portal de selección de versiones

Selecciona tu versión de Minecraft para acceder al árbol de documentación correspondiente:

| Versión de Minecraft | Versión del mod | Herramientas | Estado | Portal de acceso directo |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 Activo principal | [[👉 Entrar a la Wiki de MC 26.3|26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 Paridad | [[👉 Entrar a la Wiki de MC 26.2|26.2-Home]] |

> [!NOTE]
> Bajo la política de **1 Jar 1 Versión**, cada versión se compila como un archivo autónomo e independiente.

---

## 🌟 Resumen de subsistemas principales

- **[[Balística de impacto pesado|26.3-Heavy-Impact-Ballistics]]**:
  - Velocidad inicial aumentada en **1.5×** ($150\%$).
  - Corrección dinámica de arco: la gravedad de la flecha se reduce proporcionalmente a la velocidad ($g_{\text{eff}} = g_0 / M$).
  - **Encantamiento Balística (`bettercrossbows:ballistics`)**: otorga +25% de velocidad por nivel (hasta +125% en nivel V); incompatible con Disparo múltiple.
  - Efectos de sonido subsónico/supersónico y partículas de choque.
- **[[Mecánica de tensado rápido|26.3-Quick-Draw-Mechanics]]**:
  - Duración de recarga ajustable en ticks del servidor, con compatibilidad total para Carga rápida (Quick Charge).
- **[[Configuración y GameRules|26.3-Configuration-and-GameRules]]**:
  - GameRules dinámicas con DasikLibrary (`bettercrossbows:better_crossbows`).
  - Rango de enteros completamente abierto (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`) para absoluta libertad de configuración.
  - Interfaz opcional YACL v3 en el cliente.
- **[[Arquitectura y Mixins|26.3-Architecture-and-Mixins]]**:
  - Desglose técnico de inyecciones en `CrossbowItemMixin`, `AbstractArrowMixin` y más.

---

## 📜 Créditos y licencia

- **Autor y desarrollador**: **Dasik (Rifaditya)**
- **Licencia**: **GNU General Public License v3.0 (GPLv3)**
- **Código fuente**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
