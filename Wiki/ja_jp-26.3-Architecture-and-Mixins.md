# 💻 アーキテクチャと Mixin 解析 (Minecraft 26.3)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: この Wiki のドキュメントは**リポジトリ内の現在のソースコードの状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

---

## 1. 公式アーキテクチャインフォボックス

| パラメータ | 技術的詳細 |
| :--- | :--- |
| **ルートパッケージ** | `net.vanillaoutsider.bettercrossbows` |
| **Java プラットフォーム** | **Java 25** (`JAVA_25`) |
| **Mod イニシャライザ** | `BetterCrossbows.java` (`net.fabricmc.api.ModInitializer`) |
| **クライアントイニシャライザ** | `ModMenuIntegration.java` (`com.terraformersmc.modmenu.api.ModMenuApi`) |
| **Mixin 設定** | `bettercrossbows.mixins.json` |
| **Mixin Refmap** | `bettercrossbows-refmap.json` |
| **Mixin 対象クラス** | 6 クラス (`CrossbowItem`, `AbstractArrow`, `AnvilMenu`, `ItemCombinerMenu`, `EnchantmentMenu`, `CreativeModeTabs`) |

---

## 2. パッケージ構造と「1ファイル・1目的（1 File, 1 Purpose）」原則

Better Crossbows は関心の明確な分離に従い、クライアント専用 UI ヘルパー、サーバー権限レジストリ、設定の永続化、バイトコード Mixin を明確に分割しています：

```
net.vanillaoutsider.bettercrossbows/
├── BetterCrossbows.java                 # Mod イニシャライザと SLF4J ロガー入口
├── client/
│   └── BetterCrossbowsClientHelper.java # クライアント専用シングルプレイヤー世界照会 (@Environment)
├── config/
│   ├── BetterCrossbowsConfig.java       # 永続設定 POJO (config/bettercrossbows.json)
│   ├── ModMenuIntegration.java          # ModMenu 設定ファクトリプロバイダ (@Environment)
│   └── YaclScreenHelper.java            # YetAnotherConfigLib v3 画面ビルダー (@Environment)
├── mixin/
│   ├── AbstractArrowMixin.java          # 弾速倍率に応じた重力反転・スケール処理
│   ├── AnvilMenuMixin.java              # 金床合成時に GameRule 上限レベルを適用
│   ├── CreativeModeTabsMixin.java       # クリエイティブタブのエンチャント本フィルタとキャッシュ破棄
│   ├── CrossbowItemMixin.java           # 速度スケーリング、装填タイミング、ソニックブーム粒子演出
│   ├── EnchantmentMenuMixin.java        # エンチャントテーブルの弾道学提示レベル制限
│   └── ItemCombinerMenuAccessor.java    # 金床合成時の player フィールドへの安全なアクセサ
└── registry/
    ├── BetterCrossbowsEnchantments.java # レジストリ識別子定数
    └── BetterCrossbowsGameRules.java    # 動的名前空間付き GameRules 登録
```

---

## 3. サブシステムの依存関係とアーキテクチャ図

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

## 4. 完全な Mixin 注入リファレンスマトリクス

| Mixin クラス | 対象 Minecraft クラス | 注入ポイント / メソッド | インジェクタ型 | 優先度 | 説明 |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `getChargeDuration(ItemStack, LivingEntity)` | `@Inject` at `RETURN` | 1000 | `BetterCrossbowsGameRules.getReloadTicks()` を用いて装填時間を上書きし、クイックチャージ短縮分を減算。 |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` on `CrossbowItem#shootProjectile` | `@WrapOperation` | 1000 | 弾丸発射呼び出しをラップし、基礎発射威力を $M_{\text{shot}}$ 倍する。 |
| **`CrossbowItemMixin`** | `net.minecraft.world.item.CrossbowItem` | `performShooting(...)` | `@Inject` at `HEAD` | 1000 | 速度比率 $R > 1.2$ を評価し、ソニックブーム音と衝撃波パーティクルをトリガー。 |
| **`AbstractArrowMixin`** | `net.minecraft.world.entity.projectile.arrow.AbstractArrow` | `getDefaultGravity()` | `@Inject` at `RETURN` | 1000 | 矢がクロスボウから発射された場合、バニラの重力 ($0.05$) を速度乗数で除算 ($g_0 / M$)。 |
| **`AnvilMenuMixin`** | `net.minecraft.world.inventory.AnvilMenu` | `createResult()` | `@Inject` at `RETURN` | 500 | `DynamicEnchantmentManager` を通じて完成品の弾道学レベルを `CROSSBOW_BALLISTICS_MAX_LEVEL` に制限。 |
| **`ItemCombinerMenuAccessor`** | `net.minecraft.world.inventory.ItemCombinerMenu` | `player` field | `@Accessor` | - | `@Shadow` の継承競合を起こさずに親クラスの保護された `player` フィールドに安全アクセス。 |
| **`EnchantmentMenuMixin`** | `net.minecraft.world.inventory.EnchantmentMenu` | `getEnchantmentList(RegistryAccess, ItemStack, int, int)` | `@Inject` at `RETURN` | 1000 | エンチャントテーブルで提示される弾道学エンチャントのレベルを制限。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `tryRebuildTabContents(...)` | `@Inject` at `HEAD` | 1000 | 実行時の GameRule 変更を検知して `CACHED_PARAMETERS` をクリアし、タブ再構築を強制。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesOnlyMaxLevel(...)` | `@Inject` at `HEAD` | 1000 | 材料タブに表示される最大レベルの弾道学エンチャント本を制限。 |
| **`CreativeModeTabsMixin`** | `net.minecraft.world.item.CreativeModeTabs` | `generateEnchantmentBookTypesAllLevels(...)` | `@Inject` at `HEAD` | 1000 | 検索タブに表示される全レベルの弾道学エンチャント本を制限。 |

---

## 5. 技術的ハイライトとベストプラクティス

### A. MixinExtras `@WrapOperation` vs 脆弱な `@ModifyVariable`
旧バージョンでは、発射威力は序数指定の `@ModifyVariable` で変更されていました：
```java
// 脆弱なレガシー注入（バイトコードの変化に弱い）：
@ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
```
現代のバージョンでは、`shootProjectile` をターゲットとする MixinExtras の `@WrapOperation` に全面的にリファクタリングされています：
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
これにより、Loom やコンパイラによるローカル変数の順序変更に左右されない 100% の堅牢性が保証されます。

### B. 専用サーバーのための画面隔離プロトコル
Minecraft 専用サーバー（Dedicated Server）には `net.minecraft.client.Minecraft` や `Screen` などのクライアントクラスが含まれていません。共通コードでこれらを呼び出すと即座にクラッシュします：
```
java.lang.NoClassDefFoundError: net/minecraft/client/Minecraft
```
この危険を完全に排除するため：
1. `ModMenuIntegration` および `YaclScreenHelper` には `@Environment(EnvType.CLIENT)` を付与。
2. `BetterCrossbowsClientHelper` は `Minecraft.getInstance().getSingleplayerServer()` をクライアントガードの背後にカプセル化。
3. ModMenu エントリポイントは DasikLibrary の `GuiHelper.getOptionalYaclFactory(...)` を使用し、GUI が実際に呼び出されるまでクラスロードを遅延。

---

## 🔗 関連ページ
- [[🚀 重量衝撃の弾道学|ja_jp-26.3-Heavy-Impact-Ballistics]]
- [[⚡ 高速リロード機構|ja_jp-26.3-Quick-Draw-Mechanics]]
- [[⚙️ 設定とゲームルール|ja_jp-26.3-Configuration-and-GameRules]]
- [[26.3 概要ポータル|ja_jp-26.3-Home]] に戻る
