# 🏹 Minecraft 26.2 — Портал Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности относительно исходного кода**: Документация в этой вики отражает **текущее состояние исходного кода в репозитории**, которое может содержать недавние невыпущенные коммиты или разрабатываемые функции, опережающие общедоступные сборки на CurseForge и Modrinth.

---

## 🎯 Добро пожаловать в центр документации Minecraft 26.2

В этом разделе описан мод **Better Crossbows** для **Minecraft 26.2** (`1.0.14+26.2`).

Minecraft 26.2 представляет собой паритетную версию боевой механики 26.x с поддержкой Java 25 и Fabric API (`0.149.0+26.2`). Better Crossbows превращает арбалет в кинетическое оружие высокой точности с плоской траекторией полета стрел.

---

## 🧭 Дерево документации подсистем MC 26.2

Изучите специализированные руководства этой версии:

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|ru_ru-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|ru_ru-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|ru_ru-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|ru_ru-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Технические спецификации сборки MC 26.2

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.2` (`>=26.2-`) |
| **Mod Version** | `1.0.14+26.2` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.149.0+26.2` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.02.15` |
| **Subproject Source** | `Better Crossbows v26.2/better-crossbows/` |

---

## 🌟 Ключевые особенности и нововведения (26.2)

1. **Модернизация миксинов**: Использование MixinExtras `@WrapOperation` для метода `shootProjectile` полностью устраняет хрупкие инъекции.
2. **Истинная свобода песочницы**: Устранены искусственные лимиты GameRule согласно принципу свободы игрока, значения открыты до `Integer.MAX_VALUE`.
3. **Надежность для серверов**: Клиентская логика одиночной игры изолирована `@Environment(EnvType.CLIENT)`, гарантируя стабильность на Linux-серверах.
4. **Мгновенный сброс кэша вкладок креатива**: Изменение GameRules моментально обновляет зачарованные книги без перезапуска мира.

---

## 🧭 Navigation
- [[Master Home Portal|ru_ru-Home]]
- [[Version Compatibility|ru_ru-Version-Compatibility]]
- [[Developer Setup|ru_ru-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|ru_ru-26.3-Home]]
