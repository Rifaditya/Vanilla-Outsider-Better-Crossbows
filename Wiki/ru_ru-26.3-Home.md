# 🏹 Minecraft 26.3 — Портал Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности относительно исходного кода**: Документация в этой вики отражает **текущее состояние исходного кода в репозитории**, которое может содержать недавние невыпущенные коммиты или разрабатываемые функции, опережающие общедоступные сборки на CurseForge и Modrinth.

---

## 🎯 Добро пожаловать в центр документации Minecraft 26.3

В этом разделе описан мод **Better Crossbows** для **Minecraft 26.3** (`1.0.14+26.3`).

Minecraft 26.3 включает обновленную боевую механику, поддержку Java 25 и Fabric API (`0.156.1+26.3`). Better Crossbows делает арбалет мощным кинетическим оружием с настильной траекторией, звуковыми эффектами и гибкими серверными GameRules.

---

## 🧭 Дерево документации подсистем MC 26.3

Изучите специализированные руководства этой версии:

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|ru_ru-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|ru_ru-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|ru_ru-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|ru_ru-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Технические спецификации сборки MC 26.3

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.3` / `26.3-snapshot-6` (`>=26.3-`) |
| **Mod Version** | `1.0.14+26.3` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.01.22` |
| **Subproject Source** | `Better Crossbows v26.3/better-crossbows/` |

---

## 🌟 Ключевые особенности и нововведения (26.3)

1. **Модернизация миксинов**: Использование MixinExtras `@WrapOperation` для метода `shootProjectile` полностью устраняет хрупкие инъекции.
2. **Истинная свобода песочницы**: Устранены искусственные лимиты GameRule согласно принципу свободы игрока, значения открыты до `Integer.MAX_VALUE`.
3. **Надежность для серверов**: Клиентская логика одиночной игры изолирована `@Environment(EnvType.CLIENT)`, гарантируя стабильность на Linux-серверах.
4. **Мгновенный сброс кэша вкладок креатива**: Изменение GameRules моментально обновляет зачарованные книги без перезапуска мира.

---

## 🧭 Navigation
- [[Master Home Portal|ru_ru-Home]]
- [[Version Compatibility|ru_ru-Version-Compatibility]]
- [[Developer Setup|ru_ru-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|ru_ru-26.2-Home]]
