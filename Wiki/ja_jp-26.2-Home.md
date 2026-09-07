# 🏹 Minecraft 26.2 — Better Crossbows ドキュメントポータル

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: 本Wikiドキュメントは**リポジトリの最新ソースコード状態**を反映しており、CurseForgeやModrinthで公開されているリリース版に含まれない開発中機能を含む場合があります。

---

## 🎯 Minecraft 26.2 ドキュメントハブへようこそ

このドキュメントは **Minecraft 26.2** (`1.0.14+26.2`) 向け **Better Crossbows** の解説です。

Minecraft 26.2 は26.x系戦闘システムの安定したパリティアンカーであり、Java 25 と Fabric API (`0.149.0+26.2`) に準拠しています。クロスボウの弾速と弾道特性を劇的に向上させます。

---

## 🧭 MC 26.2 サブシステムナビゲーション

このバージョン専用の技術ドキュメント：

```
[ 26.2 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|ja_jp-26.2-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|ja_jp-26.2-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|ja_jp-26.2-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|ja_jp-26.2-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 MC 26.2 ビルド仕様

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.2` (`>=26.2-`) |
| **Mod Version** | `1.0.14+26.2` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.149.0+26.2` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.02.15` |
| **Subproject Source** | `Better Crossbows v26.2/better-crossbows/` |

---

## 🌟 主要なハイライトと新機能 (26.2)

1. **Mixin近代化**: `shootProjectile` に MixinExtras `@WrapOperation` を採用し、壊れやすい順序依存インジェクションを完全排除。
2. **真のサンドボックスの自由**: プレイヤーの自由とアンチナニー原則に従い、GameRulesの上限を `Integer.MAX_VALUE` まで完全開放。
3. **専用サーバーの堅牢性**: シングルプレイ用処理を `@Environment(EnvType.CLIENT)` で完全に隔離し、Linuxサーバーでのクラッシュを防止。
4. **クリエイティブタブの即時キャッシュ破棄**: GameRules変更時にエンチャント本リストをワールド再読み込みなしで即座に更新。

---

## 🧭 Navigation
- [[Master Home Portal|ja_jp-Home]]
- [[Version Compatibility|ja_jp-Version-Compatibility]]
- [[Developer Setup|ja_jp-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.3|ja_jp-26.3-Home]]
