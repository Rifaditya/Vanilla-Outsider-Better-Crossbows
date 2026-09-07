# 💻 Detalhamento de Arquitetura e Mixins (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Isenção de Responsabilidade do Código-Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos de desenvolvimento à frente das versões públicas no CurseForge e Modrinth.

---

## 1. Quadro Oficial de Arquitetura

| Parâmetro | Detalhes Técnicos |
| :--- | :--- |
| **Pacote raiz** | `net.vanillaoutsider.bettercrossbows` |
| **Plataforma Java** | **Java 25** (`JAVA_25`) |
| **Inicializador do mod** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **Inicializador de cliente** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Configuração Mixin** | `bettercrossbows.mixins.json` |
| **Refmap Mixin** | `bettercrossbows-refmap.json` |
| **Classes alvo de Mixin** | 6 classes (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. Organização de Pacotes e Princípio "1 Arquivo, 1 Propósito"

Better Crossbows segue uma separação rigorosa de conceitos, isolando auxiliares de interface do cliente, registros autoritativos do servidor, persistência de configuração e mixins de bytecode:

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Inicializador do mod e ponto de entrada SLF4J
├── client/
│   └── BetterCrossbowsClientHelper.java # Consultas de mundo solo exclusivas do cliente (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # POJO de configuração persistente (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # Provedor de fábrica do ModMenu (@Environment)
│   └── YaclScreenHelper.java            # Construtor de telas YetAnotherConfigLib v3 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # Inverte/ajusta gravidade de acordo com a velocidade
│   ├── AnvilMenuMixin.java              # Aplica limite de nível da GameRule em combinações na bigorna
│   ├── CreativeModeTabsMixin.java       # Filtra livros encantados na aba criativa e limpa cache
│   ├── CrossbowItemMixin.java           # Escala de velocidade, tempo de recarga e partículas sonoras
│   ├── EnchantmentMenuMixin.java        # Limita ofertas de Balística na mesa de encantamento
│   └── ItemCombinerMenuAccessor.java    # Acessor para o campo player na combinação de itens
└── registry/
    ├── BetterCrossbowsEnchantments.java # Constantes de identificadores de registro
    └── BetterCrossbowsGameRules.java    # Registro dinâmico de GameRules com namespace
```

---

## 3. Dependências de Subsistemas e Diagrama de Arquitetura

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

## 4. Matriz de Referência Completa de Injeção de Mixins

| Classe Mixin | Classe Alvo do Minecraft | Ponto de Injeção / Método | Tipo de Injetor | Prioridade | Descrição |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | Substitui a duração de carga com `BetterCrossbowsGameRules.getReloadTicks()` e subtrai bônus de Carga Rápida. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | Envolve a chamada de disparo, multiplicando o poder base por $M_{\text{shot}}$. |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | Avalia razão de velocidade $R > 1.2$; aciona áudio de estrondo sônico e partículas de choque. |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | Se disparada por besta, divide a gravidade ($0.05$) pelo multiplicador de velocidade ($g_0 / M$). |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | Limita o nível de Balística do item resultante a `CROSSBOW_BALLISTICS_MAX_LEVEL` via `DynamicEnchantmentManager`. |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | Acessa com segurança o campo protegido `player` sem conflitos de herança `@Shadow`. |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | Limita ofertas de Balística geradas na mesa de encantamentos. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | Detecta mudanças na GameRule em tempo de execução e limpa `CACHED_PARAMETERS`, forçando reconstrução. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | Limita o livro de Balística de nível máximo exibido na aba Ingredientes. |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | Limita todos os níveis de livros de Balística exibidos na aba de Busca. |

---

## 5. Destaques Técnicos e Melhores Práticas

### A. MixinExtras `@WrapOperation` vs `@ModifyVariable` Frágil
Em versões anteriores, a força de disparo era modificada com `@ModifyVariable` ordinal:
```java
// Injeção legada frágil (instável contra mudanças no bytecode):
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
Nas versões modernas, isso foi refatorado para MixinExtras `@WrapOperation` visando `shootProjectile`:
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
Isso garante 100% de confiabilidade, imune à reordenação de variáveis locais pelo Loom ou otimizações de compilação.

### B. Protocolo de Isolamento de Tela para Servidores Dedicados
Servidores dedicados de Minecraft não incluem classes de cliente como `net.minecraft.client.Minecraft` ou `Screen`. Chamar APIs de cliente em código comum causa travamentos imediatos:
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
Para eliminar esse risco:
1. `ModMenuIntegration` e `YaclScreenHelper` são marcados com `@Environment(EnvType.CLIENT)`.
2. `BetterCrossbowsClientHelper` encapsula `Minecraft.getInstance().getSingleplayerServer()` atrás de proteções de cliente.
3. O ponto de entrada do ModMenu usa `GuiHelper.getOptionalYaclFactory(...)` da DasikLibrary, adiando o carregamento de classes até a invocação da interface.

---

## 🔗 Páginas Relacionadas
- [[🚀 Balística de impacto pesado|pt_br-26.2-Heavy-Impact-Ballistics]]
- [[⚡ Mecânica de carregamento rápido|pt_br-26.2-Quick-Draw-Mechanics]]
- [[⚙️ Configuração e GameRules|pt_br-26.2-Configuration-and-GameRules]]
- Retornar ao [[26.2 Portal de visão geral|pt_br-26.2-Home]]
