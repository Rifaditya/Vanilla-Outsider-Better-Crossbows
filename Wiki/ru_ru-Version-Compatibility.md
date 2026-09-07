# 📋 Совместимость версий и матрица инструментария

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности относительно исходного кода**: Документация в этой вики отражает **текущее состояние исходного кода в репозитории**, которое может содержать недавние невыпущенные коммиты или разрабатываемые функции, опережающие общедоступные сборки на CurseForge и Modrinth.

---

## 🏛️ Политика «Один Jar — Одна версия» (1 Jar 1 Version)

**Better Crossbows** строго следует **политике 1 Jar 1 Version**. Каждая целевая версия Minecraft изолирована в собственном подпроекте с суверенными зависимостями, маппингами компилятора и артефактами сборки:
- **Никаких универсальных Франкенштейн-JAR**: вместо нестабильной рефлексии в рантайме каждая целевая версия получает строго протестированную сборку.
- **Выделенные релизы**: артефакты мода выпускаются отдельно под каждую версию (например, `better-crossbows-1.0.14+26.3.jar`).
- **Архивное хранение**: готовые релизы сохраняются в каталоге `Archive Jar of all versions/`.

---

## 📊 Полная матрица технической совместимости

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Актуальная версия** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Версия паритета** |

---

## 🧩 Опциональные и рекомендуемые зависимости

Better Crossbows не требует обязательных клиентских зависимостей, кроме Fabric API и DasikLibrary. Однако при наличии опциональных модов динамически включается полноценный графический интерфейс настроек:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Архитектура безопасности сторон (Server & Client Safety)

Весь код клиентского графического интерфейса защищен **Протоколом изоляции экранов (Screen Isolation Protocol)**:
- Классы `ModMenuIntegration` и `YaclScreenHelper` явно аннотированы `@Environment(EnvType.CLIENT)`.
- Точка входа разрешается через `GuiHelper.getOptionalYaclFactory(...)` из библиотеки DasikLibrary, что изолирует ссылки на YACL за рефлексией и проверками стороны.
- Запуск Better Crossbows на **выделенном сервере (Dedicated Server)** гарантирует полное отсутствие сбоев `ClassNotFoundException` или `NoClassDefFoundError: net/minecraft/client/Minecraft`.

---

## 🗄️ История сборок и архивные файлы

Архивные JAR-файлы предыдущих выпусков доступны в:
- `Archive Jar of all versions/`
- Каталоге локальных релизов подпроекта: `<Subproject>/releases/`
- Официальных версиях Modrinth: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 Навигация

- Вернуться на [[Главную страницу|ru_ru-Home]]
- [[MC 26.3 Overview|ru_ru-26.3-Home]]
- [[MC 26.2 Overview|ru_ru-26.2-Home]]
- [[Developer Setup|ru_ru-Developer-Setup-and-Building]]
