# ⚙️ Konfiguration & GameRules (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Haftungsausschluss zum Repository-Quellcode**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Builds auf CurseForge und Modrinth enthalten kann.

---

## 1. Offizielle technische Infobox

| Parameter | Technische Details |
| :--- | :--- |
| **Systemarchitektur** | Dynamische Namespaced GameRules + persistente globale Konfigurations-JSON |
| **Java-Implementierungen** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **GUI-Implementierungen** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **GameRule-Kategorie** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **Globaler Speicherort** | `config/bettercrossbows.json` |
| **Befehls-Namespace** | `/gamerule bettercrossbows:<rule> [value]` |
| **Spieler-Autonomie-Invariante** | Vollständiger Integer-Bereich freigeschaltet: `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. Schritt-für-Schritt-Konfigurationsablauf

### Serveradministration im Spiel (Brigadier-Befehle)
Server-Operatoren (Berechtigungsstufe 2+) können Mechaniken im laufenden Betrieb mit sofortiger Wirkung ohne Serverneustart anpassen:

1. **Aktuellen Wert prüfen**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **Parameter anpassen**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **Auf Standard zurücksetzen**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### Globale Standardkonfiguration (`config/bettercrossbows.json`)
Beim Erstellen **neuer Welten** initialisiert der Server seine GameRules aus den Werten in `config/bettercrossbows.json`. Änderungen an dieser JSON-Datei überschreiben bestehende Welten nicht; verwenden Sie `/gamerule` für aktive Welten.

### Client-Einstellungsbildschirm im Spiel (YACL + ModMenu)
Spieler mit installiertem **ModMenu** und **YetAnotherConfigLib (YACL v3)** können die interaktive Benutzeroberfläche öffnen:
1. Navigieren Sie zu **Optionen** -> **Mods** -> **Better Crossbows** -> **Einstellungen (⚙️)**.
2. Passen Sie Regler für Geschossgeschwindigkeit, Feuerwerksraketen, Nachlade-Ticks und Partikel an.
3. Klicken Sie auf den optionalen Ko-fi-Button, um die unabhängige Entwicklung zu unterstützen.

---

## 3. Mathematische Grenzen & die Spieler-Autonomie-Invariante

In Übereinstimmung mit der **Spieler-Autonomie & Anti-Bevormundungs-Invariante** erzwingt Better Crossbows **niemals künstliche Obergrenzen oder Bevormundungen**:
- Alle Integer-GameRules registrieren sich mit `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`.
- Wenn ein Administrator Pfeile mit $10.000\%$ Geschwindigkeit ($100\times$) abfeuern möchte oder die Nachladezeit auf $1$ Tick setzt, führt der Mod diese Anweisung aus.
- Untere Sicherheitsgrenzen werden strikt nur dort erzwungen, wo es technisch unvermeidbar ist, um fatale JVM-Abstürze zu verhindern (z. B. Begrenzung der Nachladezeit auf $\ge 1$ Tick bei mathematischen Berechnungen).

---

## 4. Konfigurations-Priorität & Lebenszyklus-Flussdiagramm

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

## 5. JSON-Konfigurationsschema (`config/bettercrossbows.json`)

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

## 6. Vollständige GameRules-Referenztabelle

| GameRule-Bezeichner | Typ | Standard | Gültiger Bereich | Einheit / Skala | Beschreibung |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` bis `MAX_VALUE` | Stufen | Maximal erreichbare Stufe für die Verzauberung Ballistik an Zaubertischen, Ambossen und Kreativ-Tabs. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` bis `MAX_VALUE` | Prozent ($100 = 1.0\times$) | Geschwindigkeitsmultiplikator für Pfeile, die mit einer Armbrust abgefeuert werden ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` bis `MAX_VALUE` | Prozent ($100 = 1.0\times$) | Geschwindigkeitsmultiplikator für Feuerwerksraketen, die mit einer Armbrust abgefeuert werden ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` bis `MAX_VALUE` | Spiel-Ticks ($20\text{t} = 1\text{s}$) | Basiszeit zum Spannen der Armbrust vor Abzug der Mehrfachschuss-/Schnellladen-Reduktionen. |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | Umschalter | Aktiviert den Überschall-Knall und Druckwellen-Partikel für Hochgeschwindigkeitsschüsse. |

---

## 7. Entwickler- & API-Hooks

### Auslesen von GameRules aus Addon-Mods
Addons können die aktiven GameRules direkt über `BetterCrossbowsGameRules` abfragen:

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// Gibt den Multiplikator als float zurück (z. B. 1.5f für 150)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// Gibt die Nachlade-Ticks zurück (z. B. 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// Prüft, ob Effekte aktiv sind
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### Registrierung über `DynamicGameRuleManager` (DasikLibrary)
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 Verwandte Seiten
- [[🚀 Schwere Einschlagsballistik|de_de-26.2-Heavy-Impact-Ballistics]]
- [[⚡ Schnelllade-Mechanik|de_de-26.2-Quick-Draw-Mechanics]]
- [[💻 Architektur & Mixins|de_de-26.2-Architecture-and-Mixins]]
- Zurück zum [[26.2 Übersichtsportal|de_de-26.2-Home]]
