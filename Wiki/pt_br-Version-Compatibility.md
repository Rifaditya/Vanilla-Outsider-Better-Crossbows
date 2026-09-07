# 📋 Compatibilidade de Versões e Matriz de Ferramentas

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre o Código-Fonte do Repositório**: A documentação desta Wiki reflete o **estado atual do código-fonte no repositório**, podendo incluir commits recentes ou recursos em desenvolvimento não lançados no CurseForge ou Modrinth.

---

## 🏛️ Política «1 Jar 1 Versão» (1 Jar 1 Version Policy)

**Better Crossbows** adota a rigorosa **Política de 1 Jar 1 Versão**. Cada versão suportada do Minecraft é isolada em seu próprio subprojeto com dependências, mapeamentos e artefatos de compilação soberanos:
- **Sem Jars Universais Frankenstein**: Em vez de depender de reflexão frágil em tempo de execução, cada versão tem uma compilação validada.
- **Lançamentos Dedicados**: Artefatos lançados por versão (ex.: `better-crossbows-1.0.14+26.3.jar`).
- **Preservação em Arquivo**: JARs compilados são arquivados em `Archive Jar of all versions/`.

---

## 📊 Matriz Técnica Completa de Compatibilidade

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **Ativo atual** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **Âncora de paridade** |

---

## 🧩 Dependências Opcionais e Recomendadas

Better Crossbows não possui dependências obrigatórias além do Fabric API e da DasikLibrary. No entanto, mods opcionais ativam menus de configuração ricos:

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 Arquitetura de Segurança de Lado (Cliente/Servidor)

Todo o código de interface do cliente é resguardado pelo **Screen Isolation Protocol**:
- `ModMenuIntegration` e `YaclScreenHelper` são explicitamente anotados com `@Environment(EnvType.CLIENT)`.
- O ponto de entrada é resolvido por `GuiHelper.getOptionalYaclFactory(...)` da DasikLibrary.
- Rodar em um **Servidor Dedicado (Dedicated Server)** garante 0 travamentos por `ClassNotFoundException` ou `NoClassDefFoundError: net/minecraft/client/Minecraft`.

---

## 🗄️ Histórico de Versões e Arquivos Preservados

JARs arquivados de versões anteriores estão disponíveis em:
- `Archive Jar of all versions/`
- Diretório local do subprojeto: `<Subproject>/releases/`
- Lançamentos oficiais no Modrinth: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 Navegação

- Retornar ao [[Portal Inicial|pt_br-Home]]
- [[MC 26.3 Overview|pt_br-26.3-Home]]
- [[MC 26.2 Overview|pt_br-26.2-Home]]
- [[Developer Setup|pt_br-Developer-Setup-and-Building]]
