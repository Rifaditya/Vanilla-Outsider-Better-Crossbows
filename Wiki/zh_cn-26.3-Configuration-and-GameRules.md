# ⚙️ 配置与游戏规则 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **仓库源码免责声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开版本的近期未发布提交或开发特性。

---

## 1. 官方技术信息框

| 参数 | 技术详情 |
| :--- | :--- |
| **系统架构** | 动态命名空间 GameRules + 持久化全局配置 JSON |
| **Java 实现** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **GUI 实现** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **GameRule 分类** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **全局文件位置** | `config/bettercrossbows.json` |
| **指令命名空间** | `/gamerule bettercrossbows:<rule> [value]` |
| **玩家自主性不变量** | 完全解除整数上限约束：`[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. 逐步配置工作流

### 游戏内服务器管理（Brigadier 指令）
服务器管理员（权限等级 2+）可以实时调整机制，无需重启服务器即刻生效：

1. **检查当前值**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **修改参数**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **恢复默认值**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### 全局默认配置 (`config/bettercrossbows.json`)
生成**新世界**时，服务器会根据 `config/bettercrossbows.json` 中定义的值初始化其 GameRules。对此 JSON 文件的修改不会覆盖现有世界；对于活跃世界，请使用 `/gamerule`。

### 游戏内客户端设置界面 (YACL + ModMenu)
安装了 **ModMenu** 和 **YetAnotherConfigLib (YACL v3)** 的玩家可以打开交互式图形界面：
1. 前往 **选项** -> **模组** -> **Better Crossbows** -> **设置 (⚙️)**。
2. 调整弹射物速度、烟花速度、装填 tick 和粒子效果开关的滑动条。
3. 可点击可选的 Ko-fi 创作者支持按钮，为独立模组开发助力。

---

## 3. 数学边界与反保姆哲学不变量

本模组严格恪守**玩家自主性与反保姆哲学（Player Agency & Anti-Nanny Invariant）**，**绝不对玩家施加人为的游戏上限或限制**：
- 所有整型 GameRules 均以 `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)` 注册。
- 如果管理员希望箭矢以 $10,000\%$ 的速度（$100\times$）发射，或将装填时间设为 $1$ tick，模组将彻底忠实执行。
- 仅在防止 JVM 致命崩溃的技术必需场合施加下限保护（例如在数学计算中将装填 tick 限制在 $\ge 1$ tick）。

---

## 4. 配置优先级与生命周期流程图

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

## 5. JSON 配置结构 (`config/bettercrossbows.json`)

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

## 6. 完整 GameRules 参考表格

| GameRule 标识符 | 类型 | 默认值 | 有效范围 | 单位 / 刻度 | 描述 |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` 到 `MAX_VALUE` | 等级 | 附魔台、铁砧和创造模式标签页中弹道附魔可获得的最高等级。 |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` 到 `MAX_VALUE` | 百分比 ($100 = 1.0\times$) | 施加到弩射出的箭矢上的速度倍率 ($150 = 1.5\times$)。 |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` 到 `MAX_VALUE` | 百分比 ($100 = 1.0\times$) | 施加到弩发射的烟花火箭上的速度倍率 ($100 = 1.0\times$)。 |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` 到 `MAX_VALUE` | 游戏刻 ($20\text{t} = 1\text{s}$) | 在计算快速装填缩减前，弩蓄力所需的基准时间。 |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | 开关 | 启用高速射击时的超音速音爆音效与冲击波粒子效果。 |

---

## 7. 开发者与 API 挂钩

### 从附属模组读取 GameRules
附属模组可以直接通过 `BetterCrossbowsGameRules` 查询实时 GameRules：

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// 以 float 返回实时倍率（例如 150 返回 1.5f）
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// 返回实时装填 tick（例如 25）
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// 检查是否启用了声效/粒子特效
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### 通过 `DynamicGameRuleManager` (DasikLibrary) 注册
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 相关页面
- [[🚀 重型冲击弹道|zh_cn-26.3-Heavy-Impact-Ballistics]]
- [[⚡ 快速装填机制|zh_cn-26.3-Quick-Draw-Mechanics]]
- [[💻 架构与 Mixin 解析|zh_cn-26.3-Architecture-and-Mixins]]
- 返回 [[26.3 概览传送门|zh_cn-26.3-Home]]
