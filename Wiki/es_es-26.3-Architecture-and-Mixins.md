# 💻 Desglose de arquitectura y Mixins (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Descargo de responsabilidad del código fuente del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las compilaciones públicas en CurseForge y Modrinth.

---

## 1. Cuadro de información técnica de arquitectura

| Parámetro | Detalles técnicos |
| :--- | :--- |
| **Paquete raíz** | `net.vanillaoutsider.bettercrossbows` |
| **Plataforma Java** | **Java 25** (`JAVA_25`) |
| **Inicializador del mod** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **Inicializador de cliente** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Configuración de Mixin** | `bettercrossbows.mixins.json` |
| **Refmap de Mixin** | `bettercrossbows-refmap.json` |
| **Clases de destino Mixin** | 6 clases (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. Organización de paquetes y principio "1 archivo, 1 propósito"

Better Crossbows sigue una estricta separación de responsabilidades, separando las utilidades de interfaz del cliente, los registros autoritarios del servidor, la persistencia de la configuración y los mixins de código de bytes:

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Inicializador del mod y registro SLF4J
├── client/
│   └── BetterCrossbowsClientHelper.java # Consultas de mundo individual solo de cliente (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # POJO de configuración persistente (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # Proveedor de fábrica de ModMenu (@Environment)
│   └── YaclScreenHelper.java            # Constructor de pantallas YetAnotherConfigLib v3 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # Invierte la gravedad según la escala de velocidad
│   ├── AnvilMenuMixin.java              # Aplica el tope de nivel GameRule en combinaciones de yunque
│   ├── CreativeModeTabsMixin.java       # Filtra libros encantados en pestañas creativas y limpia caché
│   ├── CrossbowItemMixin.java           # Escala de velocidad, recarga y partículas sónicas
│   ├── EnchantmentMenuMixin.java        # Limita ofertas de Balística en mesas de encantamiento
│   └── ItemCombinerMenuAccessor.java    # Accesor para el campo player en combinaciones de yunque
└── registry/
    ├── BetterCrossbowsEnchantments.java # Constantes de identificadores de registro
    └── BetterCrossbowsGameRules.java    # Registro dinámico de GameRules con espacio de nombres
```

---

## 3. Dependencias de subsistemas y diagrama de arquitectura

```
           +---------------------------------------------+
           |         BetterCrossbows (Initializer)       |
           +---------------------------------------------+
                   |                             |
                   v                             v
+-----------------------------------+   +-----------------------------------+
|     BetterCrossbowsConfig         |   |      BetterCrossbowsGameRules     |
|   - JSON loading & saving         |   |   - DynamicGameRuleManager        |
|   - Anti-nanny bounds             |   |   - Categories & live getters     |
+-----------------------------------+   +-----------------------------------+
                   |                                     ^
                   v                                     |
+-----------------------------------+                    |
|       YaclScreenHelper (GUI)      |                    |
|   - Optional YACL v3 screens      |                    |
|   - Ko-fi creator support button  |                    |
+-----------------------------------+                    |
                                                         |
         +-----------------------------------------------+
         |
         v
+-------------------------------------------------------------------------------+
|                                MIXIN LAYER                                    |
|                                                                               |
|  [CrossbowItemMixin]       --> Scaled power launch & custom reload ticks     |
|  [AbstractArrowMixin]      --> Gravity scaling for flat trajectory arc       |
|  [AnvilMenuMixin]          --> Caps output enchantments to live GameRule     |
|  [EnchantmentMenuMixin]    --> Caps table rolls to live GameRule             |
|  [CreativeModeTabsMixin]   --> Caps books & flushes cached item parameters   |
|  [ItemCombinerMenuAccessor]--> Accesses protected player context              |
+-------------------------------------------------------------------------------+
```

---

## 4. Matriz de referencia completa de inyección de Mixins

| Clase Mixin | Clase objetivo de Minecraft | Punto de inyección / Método | Tipo de inyector | Prioridad | Descripción |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | Sobrescribe el tiempo de carga con `BetterCrossbowsGameRules.getReloadTicks()` y resta reducciones de Carga Rápida. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | Envuelve el disparo del proyectil, multiplicando la potencia base por $M_{\text{shot}}$. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | Evalúa ratio de velocidad $R > 1.2$; genera estallido sónico y partículas de onda expansiva. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | Si la flecha fue disparada por ballesta, divide la gravedad ($0.05$) entre el multiplicador ($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | Limita el nivel de Balística del resultado a `CROSSBOW_BALLISTICS_MAX_LEVEL` vía `DynamicEnchantmentManager`. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | Accede con seguridad al campo protegido `player` sin conflictos `@Shadow`. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | Limita los niveles de Balística ofrecidos en la mesa de encantamientos. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | Detecta cambios de GameRule en tiempo de ejecución y limpia `CACHED_PARAMETERS`, forzando regeneración. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | Limita el libro de Balística de nivel máximo en la pestaña Ingredientes. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | Limita todos los niveles de libros de Balística en la pestaña Búsqueda. |

---

## 5. Aspectos técnicos destacados y mejores prácticas

### A. MixinExtras `@WrapOperation` frente al frágil `@ModifyVariable`
En versiones anteriores, la potencia de lanzamiento se modificaba mediante `@ModifyVariable` ordinal:
```java
// Inyección heredada frágil (inestable ante cambios de bytecode):
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
En las versiones modernas, esto se ha refactorizado a MixinExtras `@WrapOperation` apuntando a `shootProjectile`:
```java
@WrapOperation(
    method = "performShooting",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shootProjectile(...)V")
)
private void bettercrossbows$wrapShootProjectile(..., Operation<Void> original) {
    float multiplier = bettercrossbows$getShotMultiplier(level, weapon);
    original.call(..., power * multiplier, ...);
}
```
Esto garantiza una fiabilidad del 100%, inmune al reordenamiento de variables locales de Loom o las optimizaciones del compilador.

### B. Protocolo de aislamiento de pantalla para servidores dedicados
Los servidores dedicados de Minecraft no incluyen clases de cliente como `net.minecraft.client.Minecraft` o `Screen`. Llamar a las API de cliente en código común provoca fallos inmediatos:
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
Para eliminar este peligro:
1. `ModMenuIntegration` y `YaclScreenHelper` están marcados con `@Environment(EnvType.CLIENT)`.
2. `BetterCrossbowsClientHelper` encapsula `Minecraft.getInstance().getSingleplayerServer()` tras protecciones de cliente.
3. El punto de entrada de ModMenu utiliza `GuiHelper.getOptionalYaclFactory(...)` de DasikLibrary, aplazando la carga de clases hasta la invocación de la GUI.

---

## 🔗 Páginas relacionadas
- [[🚀 Balística de impacto pesado|es_es-26.3-Heavy-Impact-Ballistics]]
- [[⚡ Mecánica de carga rápida|es_es-26.3-Quick-Draw-Mechanics]]
- [[⚙️ Configuración y GameRules|es_es-26.3-Configuration-and-GameRules]]
- Volver al [[26.3 Portal de vista general|es_es-26.3-Home]]
