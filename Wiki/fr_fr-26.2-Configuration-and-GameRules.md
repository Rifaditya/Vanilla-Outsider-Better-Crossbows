# ⚙️ Configuration et GameRules (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Avis de non-responsabilité concernant le code source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en cours de développement en avance sur les versions publiques sur CurseForge et Modrinth.

---

## 1. Infobox technique officielle

| Paramètre | Détails techniques |
| :--- | :--- |
| **Architecture système** | GameRules dynamiques sous espace de noms + JSON de configuration globale persistante |
| **Implémentations Java** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **Implémentations GUI** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **Catégorie GameRule** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **Emplacement du fichier global** | `config/bettercrossbows.json` |
| **Espace de noms des commandes** | `/gamerule bettercrossbows:<rule> [value]` |
| **Invariant d'autonomie du joueur** | Plage entière totale déverrouillée : `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. Flux de travail de configuration étape par étape

### Administration du serveur en jeu (Commandes Brigadier)
Les opérateurs de serveur (niveau de permission 2+) peuvent ajuster les mécaniques à la volée avec effet immédiat sans redémarrer le serveur :

1. **Vérifier la valeur actuelle** :
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **Modifier le paramètre** :
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **Réinitialiser à la valeur par défaut** :
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### Configuration globale par défaut (`config/bettercrossbows.json`)
Lors de la génération de **nouveaux mondes**, le serveur initialise ses GameRules à partir des valeurs définies dans `config/bettercrossbows.json`. Les modifications apportées à ce fichier JSON n'écrasent pas les mondes existants ; utilisez `/gamerule` pour les mondes actifs.

### Écran de configuration client en jeu (YACL + ModMenu)
Les joueurs ayant installé **ModMenu** et **YetAnotherConfigLib (YACL v3)** peuvent ouvrir l'interface interactive :
1. Allez dans **Options** -> **Mods** -> **Better Crossbows** -> **Paramètres (⚙️)**.
2. Ajustez les curseurs pour la vitesse des projectiles, les fusées, les ticks de rechargement et les particules.
3. Cliquez sur le bouton Ko-fi facultatif pour soutenir le développement indépendant.

---

## 3. Limites mathématiques et invariant d'autonomie du joueur

Conformément à **l'invariant d'autonomie du joueur et anti-paternalisme (Anti-Nanny Invariant)**, Better Crossbows **n'impose jamais de plafonds de jeu artificiels ou de limites arbitraires** :
- Toutes les GameRules entières s'enregistrent avec `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`.
- Si un administrateur souhaite tirer des flèches à $10\,000\%$ de vitesse ($100\times$) ou définir le temps de rechargement sur $1$ tick, le mod honore complètement l'instruction.
- Des limites de sécurité inférieures ne sont appliquées que là où cela est techniquement obligatoire pour éviter des plantages de la JVM (par exemple, limiter les ticks de rechargement à $\ge 1$ tick lors de l'évaluation mathématique).

---

## 4. Priorité de configuration et organigramme du cycle de vie

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

## 5. Schéma de configuration JSON (`config/bettercrossbows.json`)

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

## 6. Tableau de référence exhaustif des GameRules

| Identifiant GameRule | Type | Défaut | Plage valide | Unité / Échelle | Description |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` à `MAX_VALUE` | Niveaux | Niveau maximal accessible pour l'enchantement Balistique sur les tables d'enchantement, enclumes et onglets créatifs. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` à `MAX_VALUE` | Pourcentage ($100 = 1.0\times$) | Multiplicateur de vélocité appliqué aux flèches tirées depuis une arbalète ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` à `MAX_VALUE` | Pourcentage ($100 = 1.0\times$) | Multiplicateur de vélocité appliqué aux fusées de feu d'artifice tirées depuis une arbalète ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` à `MAX_VALUE` | Ticks de jeu ($20\text{t} = 1\text{s}$) | Temps de base requis pour charger une arbalète avant l'application des réductions de Charge Rapide. |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | Basculer | Active le claquement sonore de déflagration supersonique et les particules d'onde de choc pour les tirs à haute vitesse. |

---

## 7. Hooks pour développeurs et API

### Lecture des GameRules depuis des mods addons
Les addons peuvent interroger directement les GameRules actives via `BetterCrossbowsGameRules` :

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// Renvoie le multiplicateur actif sous forme de float (ex. 1.5f pour 150)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// Renvoie les ticks de rechargement actifs (ex. 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// Vérifie si les effets visuels sont activés
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### Enregistrement via `DynamicGameRuleManager` (DasikLibrary)
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 Pages connexes
- [[🚀 Balistique à lourd impact|fr_fr-26.2-Heavy-Impact-Ballistics]]
- [[⚡ Mécanique de charge rapide|fr_fr-26.2-Quick-Draw-Mechanics]]
- [[💻 Architecture et Mixins|fr_fr-26.2-Architecture-and-Mixins]]
- Retour au [[26.2 Portail de présentation|fr_fr-26.2-Home]]
