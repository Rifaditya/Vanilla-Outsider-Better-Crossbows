# 💻 Détail de l'architecture et des Mixins (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Avis de non-responsabilité concernant le code source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en cours de développement en avance sur les versions publiques sur CurseForge et Modrinth.

---

## 1. Infobox officielle d'architecture

| Paramètre | Détails techniques |
| :--- | :--- |
| **Package racine** | `net.vanillaoutsider.bettercrossbows` |
| **Plateforme Java** | **Java 25** (`JAVA_25`) |
| **Initialiseur du mod** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **Initialiseur client** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Configuration Mixin** | `bettercrossbows.mixins.json` |
| **Refmap Mixin** | `bettercrossbows-refmap.json` |
| **Classes cibles Mixin** | 6 classes (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. Organisation des packages et principe « 1 fichier, 1 responsabilité »

Better Crossbows applique une stricte séparation des responsabilités, isolant les aides UI côté client, les registres autoritaires serveur, la persistance de configuration et les mixins bytecode :

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Initialiseur du mod et point d'entrée SLF4J
├── client/
│   └── BetterCrossbowsClientHelper.java # Requêtes solo réservées au client (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # POJO de configuration persistante (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # Fournisseur de configuration ModMenu (@Environment)
│   └── YaclScreenHelper.java            # Constructeur d'écrans YetAnotherConfigLib v3 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # Ajuste la gravité selon l'échelle de vitesse
│   ├── AnvilMenuMixin.java              # Impose le niveau max GameRule lors des fusions à l'enclume
│   ├── CreativeModeTabsMixin.java       # Filtre les livres enchantés du mode créatif et vide le cache
│   ├── CrossbowItemMixin.java           # Échelle de vitesse, temps de recharge, effets de choc sonique
│   ├── EnchantmentMenuMixin.java        # Limite les offres de Balistique sur la table d'enchantement
│   └── ItemCombinerMenuAccessor.java    # Accesseur pour le champ player lors des fusions
└── registry/
    ├── BetterCrossbowsEnchantments.java # Constantes d'identifiants de registre
    └── BetterCrossbowsGameRules.java    # Enregistrement des GameRules dynamiques sous espace de noms
```

---

## 3. Dépendances des sous-systèmes et schéma d'architecture

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

## 4. Matrice de référence complète des injections Mixin

| Classe Mixin | Classe cible Minecraft | Point d'injection / Méthode | Type d'injecteur | Priorité | Description |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | Remplace la durée de charge avec `BetterCrossbowsGameRules.getReloadTicks()` et soustrait les réductions de Charge Rapide. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | Encapsule l'appel de tir en multipliant la puissance initiale par $M_{\text{shot}}$. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | Évalue le ratio de vitesse $R > 1.2$ ; déclenche le claquement sonique et les particules de choc. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | Si la flèche provient d'une arbalète, divise la gravité ($0.05$) par le facteur de vitesse ($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | Plafonne le niveau de Balistique résultant à `CROSSBOW_BALLISTICS_MAX_LEVEL` via `DynamicEnchantmentManager`. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | Accède en toute sécurité au champ `player` protégé sans conflit d'héritage `@Shadow`. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | Plafonne les offres d'enchantement de Balistique générées sur la table d'enchantement. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | Détecte les modifications de GameRule et vide `CACHED_PARAMETERS`, forçant la régénération. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | Plafonne le livre de Balistique de niveau max affiché dans l'onglet Ingrédients. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | Plafonne tous les paliers de livres de Balistique affichés dans l'onglet Recherche. |

---

## 5. Points techniques clés et bonnes pratiques

### A. MixinExtras `@WrapOperation` contre le fragile `@ModifyVariable`
Dans les versions antérieures, la puissance de tir était modifiée à l'aide d'un `@ModifyVariable` ordinal :
```java
// Injection héritée fragile (sensible aux changements de bytecode) :
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
Dans les versions modernes, cela a été refactorisé avec MixinExtras `@WrapOperation` ciblant `shootProjectile` :
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
Cela garantit une fiabilité à 100 %, totalement insensible aux réordonnancements de variables locales par Loom ou le compilateur.

### B. Protocole d'isolation d'écran pour serveurs dédiés
Les serveurs dédiés Minecraft ne contiennent pas de classes client telles que `net.minecraft.client.Minecraft` ou `Screen`. L'appel d'API client dans le code commun entraîne un crash immédiat :
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
Pour éliminer ce danger :
1. `ModMenuIntegration` et `YaclScreenHelper` portent l'annotation `@Environment(EnvType.CLIENT)`.
2. `BetterCrossbowsClientHelper` encapsule `Minecraft.getInstance().getSingleplayerServer()` derrière des gardes client.
3. Le point d'entrée ModMenu utilise `GuiHelper.getOptionalYaclFactory(...)` de DasikLibrary, différant le chargement des classes jusqu'à l'ouverture réelle du GUI.

---

## 🔗 Pages connexes
- [[🚀 Balistique à lourd impact|fr_fr-26.3-Heavy-Impact-Ballistics]]
- [[⚡ Mécanique de charge rapide|fr_fr-26.3-Quick-Draw-Mechanics]]
- [[⚙️ Configuration et GameRules|fr_fr-26.3-Configuration-and-GameRules]]
- Retour au [[26.3 Portail de présentation|fr_fr-26.3-Home]]
