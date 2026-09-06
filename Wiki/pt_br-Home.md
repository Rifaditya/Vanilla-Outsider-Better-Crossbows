# 🏹 Vanilla Outsider: Better Crossbows Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso sobre o código-fonte do repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes ou recursos de desenvolvimento ainda não publicados no CurseForge e Modrinth.

---

## 🎯 Bem-vindo à documentação oficial

**Better Crossbows** é um mod de combate e mecânica balística da coleção **Vanilla Outsider Collection**, desenvolvido por **Dasik (Rifaditya)**. Ele transforma a besta em uma verdadeira **plataforma de projéteis de impacto pesado**, priorizando velocidade cinética, trajetória reta e tempos de recarga personalizáveis em vez de simples aumentos artificiais de dano.

---

## 🧭 Portal de seleção de versões

Selecione sua versão-alvo do Minecraft para acessar a documentação dedicada:

| Versão do Minecraft | Lançamento do mod | Ambiente de execução | Status | Portal de acesso direto |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 Atual | [[👉 Entrar na Wiki do MC 26.3|26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 Paridade | [[👉 Entrar na Wiki do MC 26.2|26.2-Home]] |

---

## 🌟 Visão geral das mecânicas principais

- **[[Balística de impacto pesado|26.3-Heavy-Impact-Ballistics]]**:
  - Velocidade inicial base aumentada em **1.5×** ($150\%$).
  - Correção dinâmica de arco: a gravidade da flecha diminui proporcionalmente à velocidade ($g_{\text{eff}} = g_0 / M$).
  - **Encantamento Balística (`bettercrossbows:ballistics`)**: concede +25% de velocidade por nível (até +125% no nível V); incompatível com Tiro Múltiplo.
  - Estrondo sônico sonoro e partículas de onda de choque.
- **[[Mecânica de armação rápida|26.3-Quick-Draw-Mechanics]]**:
  - Duração de recarga configurável em ticks de servidor, totalmente compatível com Carga Rápida (Quick Charge).
- **[[Configurações e GameRules|26.3-Configuration-and-GameRules]]**:
  - GameRules dinâmicas via DasikLibrary (`bettercrossbows:better_crossbows`).
  - Espaço de números inteiros totalmente destravado (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`), respeitando a liberdade de sandbox.
  - Tela de configurações YACL v3 opcional no cliente.
- **[[Arquitetura e Mixins|26.3-Architecture-and-Mixins]]**:
  - Detalhamento técnico de injeções em `CrossbowItemMixin`, `AbstractArrowMixin` e outros.

---

## 📜 Créditos e licença

- **Autor e desenvolvedor**: **Dasik (Rifaditya)**
- **Licença**: **GNU General Public License v3.0 (GPLv3)**
- **Código-fonte**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
