# 💻 Architektur & Mixins Aufschlüsselung (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Haftungsausschluss zum Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Builds auf CurseForge und Modrinth enthalten kann.

---

## 1. Offizielle Architektur-Infobox

| Parameter | Technische Details |
| :--- | :--- |
| **Root-Paket** | `net.vanillaoutsider.bettercrossbows` |
| **Java-Plattform** | **Java 25** (`JAVA_25`) |
| **Mod-Initializer** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **Client-Initializer** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Mixin-Konfiguration** | `bettercrossbows.mixins.json` |
| **Mixin-Refmap** | `bettercrossbows-refmap.json` |
| **Mixin-Zielklassen** | 6 Klassen (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. Paketorganisation & „1 Datei, 1 Zweck“-Prinzip

Better Crossbows folgt einer strikten Trennung der Zuständigkeiten, indem clientseitige UI-Helfer, server-autoritative Registrierungen, Konfigurationspersistenz und Bytecode-Mixins getrennt werden:

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Mod-Initializer & SLF4J-Logger-Einstiegspunkt
├── client/
│   └── BetterCrossbowsClientHelper.java # Client-only Einzelspieler-Weltabfragen (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # Persistentes Konfigurations-POJO (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # ModMenu-Konfigurations-Provider (@Environment)
│   └── YaclScreenHelper.java            # YetAnotherConfigLib v3 Bildschirm-Builder (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # Invertiert/skaliert Schwerkraft gemäß Geschwindigkeitsfaktor
│   ├── AnvilMenuMixin.java              # Erzwingt GameRule-Höchststufe beim Amboss-Kombinieren
│   ├── CreativeModeTabsMixin.java       # Filtert Kreativ-Tab-Zauberbücher & leert Cache
│   ├── CrossbowItemMixin.java           # Geschwindigkeitsskalierung, Nachlade-Timing, Überschall-Partikel
│   ├── EnchantmentMenuMixin.java        # Begrenzt Ballistik-Angebote an Zaubertischen
│   └── ItemCombinerMenuAccessor.java    # Accessor für geschütztes player-Feld beim Kombinieren
└── registry/
    ├── BetterCrossbowsEnchantments.java # Registrierungs-Identifikator-Konstanten
    └── BetterCrossbowsGameRules.java    # Dynamische Namespaced GameRules-Registrierung
```

---

## 3. Subsystem-Abhängigkeiten & Architekturdiagramm

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

## 4. Vollständige Mixin-Injektions-Referenzmatrix

| Mixin-Klasse | Minecraft-Zielklasse | Injektionspunkt / Methode | Injektortyp | Priorität | Beschreibung |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | Überschreibt die Ladedauer mittels `BetterCrossbowsGameRules.getReloadTicks()` und zieht Schnellladen-Reduktionen ab. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | Ummantelt den Abschussaufruf und multipliziert die Basisstärke mit $M_{\text{shot}}$. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | Prüft Geschwindigkeitsverhältnis $R > 1.2$; löst Überschallknall-Audio und Druckwellenpartikel aus. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | Falls Pfeil aus Armbrust stammt, teilt Standardgravitation ($0.05$) durch Geschwindigkeitsfaktor ($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | Begrenzt Ballistik-Stufe des Zielobjekts über `DynamicEnchantmentManager` auf `CROSSBOW_BALLISTICS_MAX_LEVEL`. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | Greift sicher auf geschütztes `player`-Feld zu, ohne `@Shadow`-Vererbungskonflikte. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | Begrenzt angebotene Ballistik-Stufen am Zaubertisch. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | Erkennt GameRule-Änderungen zur Laufzeit, leert `CACHED_PARAMETERS` und erzwingt Neuaufbau. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | Begrenzt das Max-Level-Ballistik-Buch im Zutaten-Tab. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | Begrenzt alle Ballistik-Buchstufen im Such-Tab. |

---

## 5. Technische Highlights & Best Practices

### A. MixinExtras `@WrapOperation` vs. fragiles `@ModifyVariable`
In früheren Versionen wurde die Abschusskraft mit ordinalem `@ModifyVariable` geändert:
```java
// Fragile alte Injektion (anfällig für Bytecode-Änderungen):
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
In modernen Versionen wurde dies auf MixinExtras `@WrapOperation` auf `shootProjectile` umgestellt:
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
Dies gewährleistet 100%ige Zuverlässigkeit, immun gegen Umordnungen lokaler Variablen durch Loom oder Compiler-Optimierungen.

### B. Bildschirm-Isolationsprotokoll für dedizierte Server
Dedizierte Minecraft-Server enthalten keine Client-Klassen wie `net.minecraft.client.Minecraft` oder `Screen`. Der Aufruf von Client-APIs im Common-Code führt zu sofortigen Abstürzen:
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
Um dieses Risiko zu eliminieren:
1. `ModMenuIntegration` und `YaclScreenHelper` sind mit `@Environment(EnvType.CLIENT)` annotiert.
2. `BetterCrossbowsClientHelper` kapselt `Minecraft.getInstance().getSingleplayerServer()` hinter Client-Guards.
3. Der ModMenu-Einstiegspunkt verwendet `GuiHelper.getOptionalYaclFactory(...)` aus der DasikLibrary, was das Laden von Klassen bis zum tatsächlichen GUI-Aufruf verzögert.

---

## 🔗 Verwandte Seiten
- [[🚀 Schwere Einschlagsballistik|de_de-26.3-Heavy-Impact-Ballistics]]
- [[⚡ Schnelllade-Mechanik|de_de-26.3-Quick-Draw-Mechanics]]
- [[⚙️ Konfiguration & GameRules|de_de-26.3-Configuration-and-GameRules]]
- Zurück zum [[26.3 Übersichtsportal|de_de-26.3-Home]]
