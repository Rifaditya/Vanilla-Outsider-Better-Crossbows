# 🏹 Minecraft 26.2 — Portal Better Crossbows

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação desta Wiki reflete o **estado atual do código-fonte no repositório**, podendo incluir commits recentes ou recursos em desenvolvimento não lançados no CurseForge ou Modrinth.

---

## 🎯 Bem-vindo ao Portal de Documentação do Minecraft 26.2

Esta central documenta o **Better Crossbows** para **Minecraft 26.2** (`1.0.14+26.2`).

Minecraft 26.2 é a âncora de paridade de combate 26.x, em conformidade com Java 25 e Fabric API (`0.149.0+26.2`). Transforma a besta em uma plataforma de disparo pesado com física refinada de projéteis.

---

## 🧭 Árvore de Subsistemas do MC 26.2

Explore os guias detalhados desta versão:

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|pt_br-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|pt_br-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|pt_br-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|pt_br-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 Especificações Técnicas do MC 26.2

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

## 🌟 Destaques e Novidades (26.2)

1. **Modernização de Mixins**: Uso do MixinExtras `@WrapOperation` em `shootProjectile`, eliminando injeções ordinais frágeis.
2. **Liberdade Sandbox Autêntica**: Remoção de limites artificiais nas GameRules, permitindo valores até `Integer.MAX_VALUE`.
3. **Estabilidade em Servidores Dedicados**: Código de interface isolado por `@Environment(EnvType.CLIENT)` para Linux headless.
4. **Atualização Instantânea da Aba Criativo**: Alterações em GameRules invalidam o cache de itens imediatamente.

---

## 🧭 Navigation
- [[Master Home Portal|pt_br-Home]]
- [[Version Compatibility|pt_br-Version-Compatibility]]
- [[Developer Setup|pt_br-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|pt_br-26.3-Home]]
