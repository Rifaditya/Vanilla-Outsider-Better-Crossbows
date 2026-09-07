# 💻 架构与 Mixin 解析 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **仓库源码免责声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开版本的近期未发布提交或开发特性。

---

## 1. 官方架构信息框

| 参数 | 技术详情 |
| :--- | :--- |
| **根包名** | `net.vanillaoutsider.bettercrossbows` |
| **Java 平台** | **Java 25** (`JAVA_25`) |
| **模组初始化器** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **客户端初始化器** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Mixin 配置** | `bettercrossbows.mixins.json` |
| **Mixin Refmap** | `bettercrossbows-refmap.json` |
| **Mixin 目标类** | 6 个类 (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. 包结构组织与“单一职责（1 File, 1 Purpose）”架构

Better Crossbows 严格遵循职责分离原则，将客户端 UI 辅助器、服务端权威注册表、持久化配置和字节码 mixin 分离：

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # 模组初始化器与 SLF4J 日志入口
├── client/
│   └── BetterCrossbowsClientHelper.java # 仅客户端单人世界查询 (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # 持久化配置 POJO (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # ModMenu 配置工厂提供者 (@Environment)
│   └── YaclScreenHelper.java            # YetAnotherConfigLib v3 界面构建器 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # 依据速度倍率反转重力
│   ├── AnvilMenuMixin.java              # 铁砧合成时强制执行 GameRule 最高等级限制
│   ├── CreativeModeTabsMixin.java       # 过滤创造标签页附魔书并刷新缓存
│   ├── CrossbowItemMixin.java           # 速度缩放、装填时间、音爆粒子特效
│   ├── EnchantmentMenuMixin.java        # 钳制附魔台弹道附魔候选项
│   └── ItemCombinerMenuAccessor.java    # 铁砧合成时访问 player 字段的 Accessor
└── registry/
    ├── BetterCrossbowsEnchantments.java # 注册表标识符常量
    └── BetterCrossbowsGameRules.java    # 动态命名空间 GameRules 注册
```

---

## 3. 子系统依赖与架构流程图

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

## 4. 完整 Mixin 注入参考矩阵

| Mixin 类 | 目标 Minecraft 类 | 注入点 / 方法 | 注入器类型 | 优先级 | 描述 |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | 使用 `BetterCrossbowsGameRules.getReloadTicks()` 覆盖装填时间并减去快速装填的缩减量。 |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | 包装弹射物发射调用，将基础发射威力乘以 $M_{\text{shot}}$。 |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | 评估速度比率 $R > 1.2$；触发音爆音频与定向烟云/气流冲击波粒子。 |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | 若箭矢由十字弩射出，将原版重力 ($0.05$) 除以速度倍率 ($g_0 / M$)。 |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | 通过 `DynamicEnchantmentManager` 将输出物品的弹道等级钳制在 `CROSSBOW_BALLISTICS_MAX_LEVEL` 以内。 |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | 安全访问父类的受保护 `player` 字段，避免 `@Shadow` 继承冲突。 |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | 钳制附魔台生成的弹道附魔候选项等级。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | 在运行时检测 GameRule 变更并清除 `CACHED_PARAMETERS`，强制重新生成标签页内容。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | 钳制材料标签页中显示的最高等级弹道附魔书。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | 钳制搜索标签页中显示的所有等级弹道附魔书。 |

---

## 5. 技术亮点与最佳实践

### A. MixinExtras `@WrapOperation` 对比脆弱的 `@ModifyVariable`
在早期版本中，发射威力使用序号 `@ModifyVariable` 修改：
```java
// 脆弱的早期注入（容易随字节码变动而失效）：
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
在现代版本中，已全面重构为针对 `shootProjectile` 的 MixinExtras `@WrapOperation`：
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
这保证了 100% 的可靠性，完全不受 Loom 重排序或编译器局部变量优化的影响。

### B. 专用服务端的屏幕隔离协议
Minecraft 专用服务器不包含 `net.minecraft.client.Minecraft` 或 `Screen` 等客户端类。在通用代码中调用客户端 API 会导致立即崩溃：
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
为消除该风险：
1. `ModMenuIntegration` 与 `YaclScreenHelper` 均标记了 `@Environment(EnvType.CLIENT)`。
2. `BetterCrossbowsClientHelper` 将 `Minecraft.getInstance().getSingleplayerServer()` 封装在客户端守卫中。
3. ModMenu 入口点使用 DasikLibrary 的 `GuiHelper.getOptionalYaclFactory(...)`，将类加载推迟至用户实际打开 GUI 时。

---

## 🔗 相关页面
- [[🚀 重型冲击弹道|zh_cn-26.2-Heavy-Impact-Ballistics]]
- [[⚡ 快速装填机制|zh_cn-26.2-Quick-Draw-Mechanics]]
- [[⚙️ 配置与游戏规则|zh_cn-26.2-Configuration-and-GameRules]]
- 返回 [[26.2 概览传送门|zh_cn-26.2-Home]]
