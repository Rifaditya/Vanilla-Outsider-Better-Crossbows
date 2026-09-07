# ⚙️ Конфигурация и GameRules (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Wiki отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие общедоступные сборки на CurseForge и Modrinth.

---

## 1. Официальный технический инфобокс

| Параметр | Технические детали |
| :--- | :--- |
| **Архитектура системы** | Динамические GameRules в пространстве имен + сохраняемый глобальный конфиг JSON |
| **Реализации Java** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **Реализации GUI** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **Категория GameRule** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **Расположение глобального файла** | `config/bettercrossbows.json` |
| **Пространство имен команд** | `/gamerule bettercrossbows:<rule> [value]` |
| **Инвариант свободы игрока** | Полный диапазон целых чисел: `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. Пошаговый рабочий процесс настройки

### Администрирование сервера в игре (команды Brigadier)
Операторы сервера (уровень разрешений 2+) могут изменять механику на лету с немедленным вступлением в силу без перезагрузки сервера:

1. **Проверить текущее значение**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **Изменить параметр**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **Сбросить на значение по умолчанию**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### Глобальная конфигурация по умолчанию (`config/bettercrossbows.json`)
При создании **новых миров** сервер инициализирует свои GameRules из значений, определенных в `config/bettercrossbows.json`. Изменения в этом файле JSON не перезаписывают существующие миры; для активных миров используйте `/gamerule`.

### Внутриигровой экран настроек клиента (YACL + ModMenu)
Игроки с установленными **ModMenu** и **YetAnotherConfigLib (YACL v3)** могут открыть интерактивный интерфейс:
1. Перейдите в **Настройки** -> **Моды** -> **Better Crossbows** -> **Настройки (⚙️)**.
2. Настройте интерактивные ползунки скорости снаряда, фейерверков, тиков перезарядки и переключатели частиц.
3. Нажмите кнопку поддержки автора на Ko-fi, чтобы поддержать независимую разработку.

---

## 3. Математические границы и инвариант свободы игрока

В соответствии с **инвариантом свободы игрока и отсутствия чрезмерной опеки (Anti-Nanny Invariant)**, Better Crossbows **никогда не навязывает искусственных игровых ограничений или потолков**:
- Все целочисленные GameRules регистрируются с `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`.
- Если администратор хочет стрелять стрелами со скоростью $10,000\%$ ($100\times$) или установить время перезарядки в $1$ тик, мод полностью выполняет инструкцию.
- Нижние границы безопасности применяются строго там, где это необходимо для предотвращения фатальных сбоев JVM (например, ограничение тиков перезарядки $\ge 1$ тика при математических расчетах).

---

## 4. Приоритет конфигурации и блок-схема жизненного цикла

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

## 5. Схема конфигурации JSON (`config/bettercrossbows.json`)

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

## 6. Полная справочная таблица GameRules

| Идентификатор GameRule | Тип | По умолчанию | Допустимый диапазон | Единица / Шкала | Описание |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` до `MAX_VALUE` | Уровни | Максимально достижимый уровень чар «Баллистика» на столе чар, наковальне и во вкладках творческого режима. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` до `MAX_VALUE` | Проценты ($100 = 1.0\times$) | Множитель скорости стрел, выпущенных из арбалета ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` до `MAX_VALUE` | Проценты ($100 = 1.0\times$) | Множитель скорости фейерверков, запущенных из арбалета ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` до `MAX_VALUE` | Игровые тики ($20\text{t} = 1\text{s}$) | Базовое время зарядки арбалета до применения сокращений «Быстрой зарядки». |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | Переключатель | Включает звуковой треск звукового удара и частицы ударной волны при выстрелах на высокой скорости. |

---

## 7. Хуки для разработчиков и API

### Чтение GameRules из сторонних модов
Аддоны могут напрямую запрашивать актуальные значения GameRules через `BetterCrossbowsGameRules`:

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// Возвращает актуальный множитель в виде float (например, 1.5f для 150)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// Возвращает тики перезарядки (например, 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// Проверяет, активны ли визуальные эффекты
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### Регистрация через `DynamicGameRuleManager` (DasikLibrary)
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 Связанные страницы
- [[🚀 Баллистика тяжёлого удара|ru_ru-26.3-Heavy-Impact-Ballistics]]
- [[⚡ Механика быстрой зарядки|ru_ru-26.3-Quick-Draw-Mechanics]]
- [[💻 Архитектура и Mixin|ru_ru-26.3-Architecture-and-Mixins]]
- Вернуться к [[26.3 Портал обзора|ru_ru-26.3-Home]]
