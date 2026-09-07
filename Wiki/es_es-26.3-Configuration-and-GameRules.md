# ⚙️ Configuración y GameRules (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Descargo de responsabilidad del código fuente del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las compilaciones públicas en CurseForge y Modrinth.

---

## 1. Cuadro de información técnica oficial

| Parámetro | Detalles técnicos |
| :--- | :--- |
| **Arquitectura del sistema** | GameRules dinámicas con espacio de nombres + JSON de configuración global persistente |
| **Implementaciones Java** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **Implementaciones GUI** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **Categoría GameRule** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **Ubicación del archivo global** | `config/bettercrossbows.json` |
| **Espacio de nombres de comandos** | `/gamerule bettercrossbows:<rule> [value]` |
| **Invariante de libertad del jugador** | Rango de enteros completo desbloqueado: `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. Flujo de trabajo de configuración paso a paso

### Administración del servidor en el juego (Comandos Brigadier)
Los operadores del servidor (Nivel de permiso 2+) pueden ajustar la mecánica sobre la marcha con efecto inmediato sin reiniciar el servidor:

1. **Comprobar valor actual**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **Modificar parámetro**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **Restablecer a los valores predeterminados**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### Configuración global predeterminada (`config/bettercrossbows.json`)
Al generar **nuevos mundos**, el servidor inicializa sus GameRules a partir de los valores definidos en `config/bettercrossbows.json`. Los cambios en este archivo JSON no sobrescriben los mundos existentes; utilice `/gamerule` para los mundos activos.

### Pantalla de configuración del cliente en el juego (YACL + ModMenu)
Los jugadores con **ModMenu** y **YetAnotherConfigLib (YACL v3)** instalados pueden abrir la interfaz gráfica interactiva:
1. Vaya a **Opciones** -> **Mods** -> **Better Crossbows** -> **Ajustes (⚙️)**.
2. Ajuste los controles deslizantes de velocidad de proyectiles, cohetes, ticks de recarga y efectos de partículas.
3. Haga clic en el botón opcional de apoyo al creador de Ko-fi para apoyar el desarrollo independiente.

---

## 3. Límites matemáticos y el invariante contra la sobreprotección

En cumplimiento del **Invariante de libertad del jugador contra la sobreprotección (Anti-Nanny Invariant)**, Better Crossbows **nunca impone límites o techos de juego artificiales**:
- Todas las GameRules enteras se registran con `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`.
- Si un administrador desea que las flechas se disparen al $10,000\%$ de velocidad ($100\times$) o establece los ticks de recarga en $1$, el mod cumple la instrucción por completo.
- Las restricciones de seguridad de límite inferior se aplican estrictamente cuando es necesario para evitar fallos fatales de la JVM (por ejemplo, limitar los ticks de recarga a $\ge 1$ tick durante la evaluación matemática).

---

## 4. Prioridad de configuración y diagrama de flujo del ciclo de vida

```
           [ config/bettercrossbows.json ]
                         |
                         | (Read on mod startup)
                         v
           [ BetterCrossbowsConfig.load() ]
                         |
                         | (Supplies initial defaults)
                         v
           [ World Creation (Level.java) ]
                         |
                         | (GameRules initialized)
                         v
          +-------------------------------+
          |  ACTIVE LEVEL GAMERULE STATE  | <-----+ (/gamerule command)
          +-------------------------------+       |
                         |                        | (Admin modification)
                         v                        |
           [ DynamicGameRuleManager.get() ] ------+
                         |
                         +---> CrossbowItemMixin (Launch & Reload)
                         +---> AbstractArrowMixin (Gravity)
                         +---> CreativeModeTabsMixin (Tab Books)
```

---

## 5. Esquema de configuración JSON (`config/bettercrossbows.json`)

```json
{
  "configVersion": 1,
  "crossbowBallisticsMaxLevel": 5,
  "crossbowVelocityMultiplier": 150,
  "crossbowFireworkMultiplier": 100,
  "crossbowReloadTicks": 25,
  "crossbowEnableJuice": true
}
```

---

## 6. Tabla de referencia exhaustiva de GameRules

| Identificador de GameRule | Tipo | Predeterminado | Rango válido | Unidad / Escala | Descripción |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` a `MAX_VALUE` | Niveles | Nivel máximo obtenible para el encantamiento Balística en mesas de encantamiento, yunques y pestañas creativas. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` a `MAX_VALUE` | Porcentaje ($100 = 1.0\times$) | Multiplicador de velocidad aplicado a las flechas disparadas desde una ballesta ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` a `MAX_VALUE` | Porcentaje ($100 = 1.0\times$) | Multiplicador de velocidad aplicado a los cohetes de fuegos artificiales lanzados desde una ballesta ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` a `MAX_VALUE` | Ticks del juego ($20\text{t} = 1\text{s}$) | Tiempo base requerido para cargar una ballesta antes de aplicar las reducciones de Carga Rápida. |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | Interruptor | Habilita el estallido sónico supersónico y las partículas de onda expansiva para disparos de alta velocidad. |

---

## 7. Ganchos de desarrollador y API

### Lectura de GameRules desde addons
Los mods addons pueden consultar las GameRules activas directamente a través de `BetterCrossbowsGameRules`:

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// Devuelve el multiplicador en vivo como float (por ejemplo, 1.5f para 150)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// Devuelve los ticks de recarga en vivo (por ejemplo, 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// Comprueba si los efectos visuales están activos
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### Registro a través de `DynamicGameRuleManager` (DasikLibrary)
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 Páginas relacionadas
- [[🚀 Balística de impacto pesado|es_es-26.3-Heavy-Impact-Ballistics]]
- [[⚡ Mecánica de carga rápida|es_es-26.3-Quick-Draw-Mechanics]]
- [[💻 Arquitectura y Mixins|es_es-26.3-Architecture-and-Mixins]]
- Volver al [[26.3 Portal de vista general|es_es-26.3-Home]]
