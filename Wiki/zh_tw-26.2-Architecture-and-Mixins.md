# 💻 架構與 Mixin 解析 (Minecraft 26.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **存放庫原始碼免責聲明**：本 Wiki 中的文件反映了**存放庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開版本的近期未發布提交或開發特性。

---

## 1. 官方架構資訊框

| 參數 | 技術詳情 |
| :--- | :--- |
| **根套件名** | `net.vanillaoutsider.bettercrossbows` |
| **Java 平台** | **Java 25** (`JAVA_25`) |
| **模組初始化器** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **客戶端初始化器** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Mixin 設定** | `bettercrossbows.mixins.json` |
| **Mixin Refmap** | `bettercrossbows-refmap.json` |
| **Mixin 目標類別** | 6 個類別 (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. 套件結構組織與「單一職責（1 File, 1 Purpose）」架構

Better Crossbows 嚴格遵循職責分離原則，將客戶端 UI 輔助器、伺服端權威註冊表、持久化設定與位元組碼 mixin 分離：

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # 模組初始化器與 SLF4J 日誌入口
├── client/
│   └── BetterCrossbowsClientHelper.java # 僅客戶端單人世界查詢 (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # 持久化設定 POJO (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # ModMenu 設定工廠提供者 (@Environment)
│   └── YaclScreenHelper.java            # YetAnotherConfigLib v3 介面建構器 (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # 依據速度倍率反轉重力
│   ├── AnvilMenuMixin.java              # 鐵砧合成時強制執行 GameRule 最高等級限制
│   ├── CreativeModeTabsMixin.java       # 過濾創造標籤頁附魔書並重新整理快取
│   ├── CrossbowItemMixin.java           # 速度縮放、裝填時間、音爆粒子特效
│   ├── EnchantmentMenuMixin.java        # 鉗制附魔台彈道附魔候選項
│   └── ItemCombinerMenuAccessor.java    # 鐵砧合成時存取 player 欄位的 Accessor
└── registry/
    ├── BetterCrossbowsEnchantments.java # 註冊表標識符常量
    └── BetterCrossbowsGameRules.java    # 動態命名空間 GameRules 註冊
```

---

## 3. 子系統依賴與架構流程圖

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

## 4. 完整 Mixin 注入參考矩陣

| Mixin 類別 | 目標 Minecraft 類別 | 注入點 / 方法 | 注入器類型 | 優先級 | 描述 |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | 使用 `BetterCrossbowsGameRules.getReloadTicks()` 覆寫裝填時間並扣除快速裝填的縮減量。 |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | 包裝彈射物發射呼叫，將基礎發射威力乘以 $M_{\text{shot}}$。 |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | 評估速度比率 $R > 1.2$；觸發音爆音效與定向煙雲/氣流衝擊波粒子。 |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | 若箭矢由十字弓射出，將原版重力 ($0.05$) 除以速度倍率 ($g_0 / M$)。 |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | 透過 `DynamicEnchantmentManager` 將輸出物品的彈道等級鉗制在 `CROSSBOW_BALLISTICS_MAX_LEVEL` 以內。 |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | 安全存取父類別的受保護 `player` 欄位，避免 `@Shadow` 繼承衝突。 |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | 鉗制附魔台產生的彈道附魔候選項等級。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | 在執行時期偵測 GameRule 變更並清除 `CACHED_PARAMETERS`，強制重新產生標籤頁內容。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | 鉗制材料標籤頁中顯示的最高等級彈道附魔書。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | 鉗制搜尋標籤頁中顯示的所有等級彈道附魔書。 |

---

## 5. 技術亮點與最佳實踐

### A. MixinExtras `@WrapOperation` 對比脆弱的 `@ModifyVariable`
在早期版本中，發射威力使用序號 `@ModifyVariable` 修改：
```java
// 脆弱的早期注入（容易隨位元組碼變動而失效）：
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
在現代版本中，已全面重構為針對 `shootProjectile` 的 MixinExtras `@WrapOperation`：
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
這保證了 100% 的可靠性，完全不受 Loom 重排序或編譯器區域變數優化的影響。

### B. 專用伺服器的螢幕隔離協議
Minecraft 專用伺服器不包含 `net.minecraft.client.Minecraft` 或 `Screen` 等客戶端類別。在通用代碼中呼叫客戶端 API 會導致立即崩潰：
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
為消除該風險：
1. `ModMenuIntegration` 與 `YaclScreenHelper` 均標記了 `@Environment(EnvType.CLIENT)`。
2. `BetterCrossbowsClientHelper` 將 `Minecraft.getInstance().getSingleplayerServer()` 封裝在客戶端守衛中。
3. ModMenu 入口點使用 DasikLibrary 的 `GuiHelper.getOptionalYaclFactory(...)`，將類別載入推遲至使用者實際開啟 GUI 時。

---

## 🔗 相關頁面
- [[🚀 重型衝擊彈道|zh_tw-26.2-Heavy-Impact-Ballistics]]
- [[⚡ 快速裝填機制|zh_tw-26.2-Quick-Draw-Mechanics]]
- [[⚙️ 設定與遊戲規則|zh_tw-26.2-Configuration-and-GameRules]]
- 返回 [[26.2 總覽傳送門|zh_tw-26.2-Home]]
