# ⚙️ Configuração e GameRules (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Isenção de Responsabilidade do Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos de desenvolvimento à frente das versões públicas no CurseForge e Modrinth.

---

## 1. Quadro de Informações Técnicas Oficiais

| Parâmetro | Detalhes Técnicos |
| :--- | :--- |
| **Arquitetura do sistema** | GameRules dinâmicas com namespace + JSON de configuração global persistente |
| **Implementações Java** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **Implementações GUI** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **Categoria GameRule** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **Localização do arquivo global** | `config/bettercrossbows.json` |
| **Namespace de comandos** | `/gamerule bettercrossbows:<rule> [value]` |
| **Invariante de arbítrio do jogador** | Intervalo inteiro total desbloqueado: `[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. Fluxo de Trabalho de Configuração Passo a Passo

### Administração de Servidor no Jogo (Comandos Brigadier)
Operadores de servidor (Nível de permissão 2+) podem ajustar a mecânica em tempo real com efeito imediato, sem reiniciar o servidor:

1. **Verificar valor atual**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **Modificar parâmetro**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **Redefinir para o padrão**:
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### Configuração Padrão Global (`config/bettercrossbows.json`)
Ao gerar **novos mundos**, o servidor inicializa suas GameRules a partir dos valores definidos em `config/bettercrossbows.json`. Alterações neste arquivo JSON não sobrescrevem mundos existentes; use `/gamerule` para mundos ativos.

### Tela de Configurações do Cliente no Jogo (YACL + ModMenu)
Jogadores com **ModMenu** e **YetAnotherConfigLib (YACL v3)** instalados podem abrir a interface interativa:
1. Vá em **Opções** -> **Mods** -> **Better Crossbows** -> **Configurações (⚙️)**.
2. Ajuste controles deslizantes para velocidade de projéteis, fogos de artifício, ticks de recarga e partículas.
3. Clique no botão de apoio ao criador Ko-fi para apoiar o desenvolvimento independente.

---

## 3. Limites Matemáticos e o Invariante de Arbítrio do Jogador

Em conformidade com o **Invariante de Arbítrio do Jogador e Anti-Paternalismo (Anti-Nanny Invariant)**, Better Crossbows **nunca impõe tetos de jogabilidade artificiais ou restrições arbitrárias**:
- Todas as GameRules de inteiros são registradas com `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)`.
- Se um administrador desejar disparar flechas a $10.000\%$ de velocidade ($100\times$) ou definir os ticks de recarga para $1$, o mod respeita a instrução totalmente.
- Travas de segurança de limite inferior são aplicadas estritamente onde necessário para evitar travamentos fatais da JVM (por exemplo, limitar ticks de recarga a $\ge 1$ tick na avaliação matemática).

---

## 4. Precedência de Configuração e Fluxograma de Ciclo de Vida

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

## 5. Esquema de Configuração JSON (`config/bettercrossbows.json`)

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

## 6. Tabela de Referência Exaustiva de GameRules

| Identificador de GameRule | Tipo | Padrão | Intervalo Válido | Unidade / Escala | Descrição |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` a `MAX_VALUE` | Níveis | Nível máximo obtenível para o encantamento Balística em mesas de encantamento, bigornas e abas criativas. |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` a `MAX_VALUE` | Porcentagem ($100 = 1.0\times$) | Multiplicador de velocidade aplicado a flechas disparadas de uma besta ($150 = 1.5\times$). |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` a `MAX_VALUE` | Porcentagem ($100 = 1.0\times$) | Multiplicador de velocidade aplicado a fogos de artifício lançados de uma besta ($100 = 1.0\times$). |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` a `MAX_VALUE` | Ticks de jogo ($20\text{t} = 1\text{s}$) | Tempo base necessário para armar a besta antes da aplicação das reduções de Carga Rápida. |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | Alternância | Habilita o estalo sônico supersônico e partículas de onda de choque para disparos em alta velocidade. |

---

## 7. Ganchos para Desenvolvedores e API

### Lendo GameRules a partir de addons
Mods dependentes podem consultar as GameRules ativas diretamente via `BetterCrossbowsGameRules`:

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// Retorna o multiplicador ativo como float (ex: 1.5f para 150)
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// Retorna os ticks de recarga ativos (ex: 25)
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// Verifica se os efeitos visuais estão ativos
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### Registro via `DynamicGameRuleManager` (DasikLibrary)
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 Páginas Relacionadas
- [[🚀 Balística de impacto pesado|pt_br-26.3-Heavy-Impact-Ballistics]]
- [[⚡ Mecânica de carregamento rápido|pt_br-26.3-Quick-Draw-Mechanics]]
- [[💻 Arquitetura e Mixins|pt_br-26.3-Architecture-and-Mixins]]
- Retornar ao [[26.3 Portal de visão geral|pt_br-26.3-Home]]
