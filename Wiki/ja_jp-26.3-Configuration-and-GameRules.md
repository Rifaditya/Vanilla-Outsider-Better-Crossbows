# ⚙️ 設定とゲームルール (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: この Wiki のドキュメントは**リポジトリ内の現在のソースコードの状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

---

## 1. 公式技術インフォボックス

| パラメータ | 技術的詳細 |
| :--- | :--- |
| **システムアーキテクチャ** | 動的名前空間付き GameRules + 永続グローバル設定 JSON |
| **Java 実装** | [`BetterCrossbowsGameRules.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/registry/BetterCrossbowsGameRules.java), [`BetterCrossbowsConfig.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/BetterCrossbowsConfig.java) |
| **GUI 実装** | [`ModMenuIntegration.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/ModMenuIntegration.java), [`YaclScreenHelper.java`](file:///src/main/java/net/vanillaoutsider/bettercrossbows/config/YaclScreenHelper.java) |
| **GameRule カテゴリ** | `bettercrossbows:better_crossbows` ("Vanilla Outsider: Better Crossbows") |
| **グローバルファイル配置** | `config/bettercrossbows.json` |
| **コマンド名前空間** | `/gamerule bettercrossbows:<rule> [value]` |
| **プレイヤー主権不変則** | 全整数範囲の制限解除：`[Integer.MIN_VALUE, Integer.MAX_VALUE]` |

---

## 2. 段階的設定ワークフロー

### ゲーム内サーバー管理（Brigadier コマンド）
サーバー管理者（権限レベル 2 以上）は、サーバーを再起動することなく、即座に反映される形でメカニクスを調整できます：

1. **現在の値を確認**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier
   ```
2. **パラメータを変更**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 200
   ```
3. **デフォルトにリセット**：
   ```mcfunction
   /gamerule bettercrossbows:crossbow_velocity_multiplier 150
   ```

### グローバルデフォルト設定 (`config/bettercrossbows.json`)
**新しいワールド**を生成する際、サーバーは `config/bettercrossbows.json` で定義された値から GameRules を初期化します。この JSON ファイルを変更しても既存のワールドは上書きされません。稼働中のワールドでは `/gamerule` を使用してください。

### ゲーム内クライアント設定画面 (YACL + ModMenu)
**ModMenu** および **YetAnotherConfigLib (YACL v3)** を導入しているプレイヤーは、インタラクティブな設定画面を開くことができます：
1. **設定** -> **Mod** -> **Better Crossbows** -> **設定 (⚙️)** へ移動します。
2. 弾速、花火速度、リロード tick 数、パーティクル切り替えのスライダーを調整します。
3. 任意の Ko-fi クリエイター支援ボタンをクリックして、開発を支援できます。

---

## 3. 数学的境界とプレイヤー主権不変則

**プレイヤー主権および反過保護の不変則（Anti-Nanny Invariant）** に従い、Better Crossbows は**不自然なゲームプレイの上限や制限を一切課しません**：
- すべての整数型 GameRules は `.range(Integer.MIN_VALUE, Integer.MAX_VALUE)` で登録されます。
- 管理者が矢を $10,000\%$ の速度（$100\times$）で発射したい場合や、リロード時間を $1$ tick に設定したい場合でも、Mod はその指示を忠実に実行します。
- 致命的な JVM クラッシュを防ぐために技術的に必須な場合にのみ、厳密な下限安全クランプが適用されます（例：計算中のリロード時間を $\ge 1$ tick に固定）。

---

## 4. 設定の優先順位とライフサイクル・フローチャート

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

## 5. JSON 設定スキーマ (`config/bettercrossbows.json`)

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

## 6. 完全な GameRules リファレンステーブル

| GameRule 識別子 | 型 | デフォルト | 有効範囲 | 単位 / スケール | 説明 |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `bettercrossbows:crossbow_ballistics_max_level` | `Integer` | `5` | `MIN_VALUE` 〜 `MAX_VALUE` | レベル | エンチャントテーブル、金床、クリエイティブタブで入手可能な弾道学エンチャントの最大レベル。 |
| `bettercrossbows:crossbow_velocity_multiplier` | `Integer` | `150` | `MIN_VALUE` 〜 `MAX_VALUE` | パーセント ($100 = 1.0\times$) | クロスボウから発射される矢に適用される速度乗数 ($150 = 1.5\times$)。 |
| `bettercrossbows:crossbow_firework_multiplier` | `Integer` | `100` | `MIN_VALUE` 〜 `MAX_VALUE` | パーセント ($100 = 1.0\times$) | クロスボウから発射される花火のロケット弾に適用される速度乗数 ($100 = 1.0\times$)。 |
| `bettercrossbows:crossbow_reload_ticks` | `Integer` | `25` | `MIN_VALUE` 〜 `MAX_VALUE` | ゲーム Tick ($20\text{t} = 1\text{s}$) | クイックチャージによる短縮が適用される前のクロスボウ装填基準時間。 |
| `bettercrossbows:crossbow_enable_juice` | `Boolean` | `true` | `true` / `false` | 切り替え | 高速射撃時の超音速衝撃波音（ソニックブーム音）と衝撃波パーティクルを有効化。 |

---

## 7. 開発者および API フック

### アドオン Mod からの GameRules の読み取り
アドオンは `BetterCrossbowsGameRules` を通じてアクティブな GameRules を直接照会できます：

```java
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import net.minecraft.world.level.Level;

// リアルタイムの倍率を float で取得（例：150 の場合は 1.5f）
float arrowMult = BetterCrossbowsGameRules.getVelocityMultiplier(level);

// リアルタイムのリロード tick 数を取得（例：25）
int baseTicks = BetterCrossbowsGameRules.getReloadTicks(level);

// エフェクト演出が有効かどうかを確認
boolean juice = BetterCrossbowsGameRules.isJuiceEnabled(level);
```

### `DynamicGameRuleManager` (DasikLibrary) による登録
```java
CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager
    .integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, config.crossbowVelocityMultiplier)
    .name("Crossbow Velocity Multiplier")
    .description("Multiplier applied to the base power of arrows (in percent). Default: " + config.crossbowVelocityMultiplier)
    .range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    .register();
```

---

## 🔗 関連ページ
- [[🚀 重量衝撃の弾道学|ja_jp-26.3-Heavy-Impact-Ballistics]]
- [[⚡ 高速リロード機構|ja_jp-26.3-Quick-Draw-Mechanics]]
- [[💻 アーキテクチャと Mixin|ja_jp-26.3-Architecture-and-Mixins]]
- [[26.3 概要ポータル|ja_jp-26.3-Home]] に戻る
