# ⚙️ 設定與遊戲規則 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **存放庫原始碼免責聲明**：本 Wiki 中的文件反映了**存放庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開版本的近期未發布提交或開發特性。

---

## 1. 官方技術資訊框

| 參數 | 技術詳情 |
| :--- | :--- |
| **系統架構** | 動態命名空間 GameRules + 持久化全域設定 JSON |
| **Java 實作** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **GUI 實作** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **GameRule 分類** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **全域檔案位置** | `config/bettercrossbows.json` |
| **指令命名空間** | `/gamerule bettercrossbows:<rule> [value]` |
| **玩家自主性不變量** | 完全解除整數上限約束：`[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. 逐步設定工作流程

### 遊戲內伺服器管理（Brigadier 指令）
伺服器管理員（權限等級 2+）可以即時調整機制，無需重啟伺服器即刻生效：

1. **檢查當前數值**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **修改參數**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **恢復預設值**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### 全域預設設定 (`config/bettercrossbows.json`)
生成**新世界**時，伺服器會依據 `config/bettercrossbows.json` 中定義的數值初始化其 GameRules。對此 JSON 檔案的修改不會覆寫現有世界；對於活躍世界，請使用 `/gamerule`。

### 遊戲內客戶端設定介面 (YACL + ModMenu)
安裝了 **ModMenu** 與 **YetAnotherConfigLib (YACL v3)** 的玩家可以開啟互動式圖形介面：
1. 前往 **選項** -> **模組** -> **Better Crossbows** -> **設定 (⚙️)**。
2. 調整彈射物速度、煙火速度、裝填 tick 與粒子效果開關的滑動條。
3. 可點擊可選的 Ko-fi 創作者支援按鈕，為獨立模組開發助力。

---

## 3. 數學邊界與反保姆哲學不變量

本模組嚴格恪守**玩家自主性與反保姆哲學（Player Agency & Anti-Nanny Invariant）**，**絕不對玩家施加人為的遊戲上限或限制**：
- 所有整數型 GameRules 均以 `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)` 註冊。
- 若管理員希望箭矢以 $10,000\%$ 的速度（$100\times$）發射，或將裝填時間設為 $1$ tick，模組將徹底忠實執行。
- 僅在防止 JVM 致命崩潰的技術必需場合施加下限保護（例如在數學計算中將裝填 tick 限制在 $\ge 1$ tick）。

---

## 4. 設定優先級與生命週期流程圖

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

## 5. JSON 設定結構 (`config/bettercrossbows.json`)

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

## 6. 完整 GameRules 參考表格

| GameRule 標識符 | 類型 | 預設值 | 有效範圍 | 單位 / 刻度 | 描述 |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` 到 `MAX_VALUE` | 等級 | 附魔台、鐵砧與創造模式標籤頁中彈道附魔可獲得的最高等級。 |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` 到 `MAX_VALUE` | 百分比 ($100 = 1.0\times$) | 施加到十字弓射出的箭矢上的速度倍率 ($150 = 1.5\times$)。 |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` 到 `MAX_VALUE` | 百分比 ($100 = 1.0\times$) | 施加到十字弓發射的煙火火箭上的速度倍率 ($100 = 1.0\times$)。 |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` 到 `MAX_VALUE` | 遊戲刻 ($20\text{t} = 1\text{s}$) | 在計算快速裝填縮減前，十字弓蓄力所需的基頻時間。 |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | 開關 | 啟用高速射擊時的超音速音爆音效與衝擊波粒子效果。 |

---

## 7. 開發者與 API 掛鉤

### 從附屬模組讀取 GameRules
附屬模組可以直接透過 `BetterCrossbowsGameRules` 查詢即時 GameRules：

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// 以 float 返回即時倍率（例如 150 返回 1.5f）
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// 返回即時裝填 tick（例如 25）
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// 檢查是否啟用了聲效/粒子特效
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### 透過 `DynamicGameRuleManager` (DasikLibrary) 註冊
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 相關頁面
- [[🚀 重型衝擊彈道|zh_tw-26.3-Heavy-Impact-Ballistics]]
- [[⚡ 快速裝填機制|zh_tw-26.3-Quick-Draw-Mechanics]]
- [[💻 架構與 Mixin 解析|zh_tw-26.3-Architecture-and-Mixins]]
- 返回 [[26.3 總覽傳送門|zh_tw-26.3-Home]]
