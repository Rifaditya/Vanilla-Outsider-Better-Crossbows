# 💻 Архитектура и анализ Mixin (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие общедоступные сборки на CurseForge и Modrinth.

---

## 1. Официальный инфобокс архитектуры

| Параметр | Технические детали |
| :--- | :--- |
| **Корневой пакет** | `net.vanillaoutsider.bettercrossbows` |
| **Платформа Java** | **Java 25** (`JAVA_25`) |
| **Инициализатор мода** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **Клиентский инициализатор** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Конфигурация Mixin** | `bettercrossbows.mixins.json` |
| **Refmap Mixin** | `bettercrossbows-refmap.json` |
| **Целевые классы Mixin** | 6 классов (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. Организация пакетов и архитектурный принцип «1 файл — 1 назначение»

Better Crossbows строго следует разделению обязанностей, изолируя вспомогательные клиентские классы UI, авторитетные серверные реестры, сохранение конфигурации и байткод-миксины:

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Инициализатор мода и точка входа SLF4J
├── client/
│   └── BetterCrossbowsClientHelper.java # Запросы к одиночному миру только для клиента (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # POJO сохраняемой конфигурации (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # Фабрика конфигурации ModMenu (@Environment)
│   └── YaclScreenHelper.java            # Построитель экранов YetAnotherConfigLib v3 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # Корректировка гравитации в соответствии со скоростью
│   ├── AnvilMenuMixin.java              # Ограничение максимального уровня GameRule при объединении на наковальне
│   ├── CreativeModeTabsMixin.java       # Фильтрация зачарованных книг в креативе и сброс кэша
│   ├── CrossbowItemMixin.java           # Масштабирование скорости, время перезарядки, частицы удара
│   ├── EnchantmentMenuMixin.java        # Ограничение предложений Баллистики на столе чар
│   └── ItemCombinerMenuAccessor.java    # Аксессор для доступа к полю player при объединении
└── registry/
    ├── BetterCrossbowsEnchantments.java # Константы идентификаторов реестра
    └── BetterCrossbowsGameRules.java    # Регистрация динамических GameRules в пространстве имен
```

---

## 3. Зависимости подсистем и архитектурная диаграмма

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

## 4. Полная справочная матрица внедрения Mixin

| Класс Mixin | Целевой класс Minecraft | Точка внедрения / Метод | Тип инжектора | Приоритет | Описание |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | Переопределяет время зарядки через `BetterCrossbowsGameRules.getReloadTicks()` и вычитает бонусы «Быстрой зарядки». |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | Оборачивает запуск снаряда, умножая базовую силу выстрела на $M_{\text{shot}}$. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | Оценивает соотношение скорости $R > 1.2$; вызывает звук звукового удара и направленные частицы ударной волны. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | Если стрела выпущена из арбалета, делит базовую гравитацию ($0.05$) на множитель скорости ($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | Ограничивает уровень Баллистики результирующего предмета до `CROSSBOW_BALLISTICS_MAX_LEVEL` через `DynamicEnchantmentManager`. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | Безопасный доступ к защищенному полю `player` родительского класса без конфликтов `@Shadow`. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | Ограничивает уровни чар Баллистики, предлагаемые на столе зачарования. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | Обнаруживает изменения GameRule во время игры и сбрасывает `CACHED_PARAMETERS`, принуждая к перегенерации содержимого вкладок. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | Ограничивает книгу Баллистики максимального уровня во вкладке «Ингредиенты». |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | Ограничивает все уровни книг Баллистики во вкладке поиска. |

---

## 5. Технические особенности и лучшие практики

### A. MixinExtras `@WrapOperation` против хрупкого `@ModifyVariable`
В более ранних версиях сила выстрела изменялась с помощью порядкового `@ModifyVariable`:
```java
// Хрупкая устаревшая инъекция (ломается при изменении байткода):
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
В современных версиях это было переработано с использованием MixinExtras `@WrapOperation` для `shootProjectile`:
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
Это гарантирует 100% надежность и устойчивость к оптимизациям компилятора или переупорядочиванию переменных в Loom.

### B. Протокол изоляции экранов для выделенных серверов
Выделенные серверы Minecraft не содержат клиентских классов, таких как `net.minecraft.client.Minecraft` или `Screen`. Вызов клиентских API в общем коде приводит к мгновенному сбою:
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
Для устранения этой опасности:
1. `ModMenuIntegration` и `YaclScreenHelper` помечены аннотацией `@Environment(EnvType.CLIENT)`.
2. `BetterCrossbowsClientHelper` инкапсулирует `Minecraft.getInstance().getSingleplayerServer()` за клиентскими защитами.
3. Точка входа ModMenu использует `GuiHelper.getOptionalYaclFactory(...)` из DasikLibrary, откладывая загрузку классов до момента вызова GUI.

---

## 🔗 Связанные страницы
- [[🚀 Баллистика тяжёлого удара|ru_ru-26.2-Heavy-Impact-Ballistics]]
- [[⚡ Механика быстрой зарядки|ru_ru-26.2-Quick-Draw-Mechanics]]
- [[⚙️ Конфигурация и GameRules|ru_ru-26.2-Configuration-and-GameRules]]
- Вернуться к [[26.2 Портал обзора|ru_ru-26.2-Home]]
