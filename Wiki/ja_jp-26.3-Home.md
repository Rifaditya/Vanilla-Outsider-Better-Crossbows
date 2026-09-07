# 🏹 Minecraft 26.3 — Better Crossbows ドキュメントポータル

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: 本Wikiドキュメントは**リポジトリの最新ソースコード状態**を反映しており、CurseForgeやModrinthで公開されているリリース版に含まれない開発中機能を含む場合があります。

---

## 🎯 Minecraft 26.3 ドキュメントハブへようこそ

このドキュメントは **Minecraft 26.3** (`1.0.14+26.3`) 向け **Better Crossbows** の仕様解説です。

Minecraft 26.3 では戦闘メカニクスの近代化、Java 25 準拠、Fabric API (`0.156.1+26.3`) が導入されています。Better Crossbows はクロスボウを高速かつ低伸弾道を備えた長距離狙撃兵器へと進化させます。

---

## 🧭 MC 26.3 サブシステムナビゲーション

このバージョン専用の技術ドキュメント：

```
[ 26.3 Home Portal ]
        |
        +---> [[🚀 Heavy-Impact Ballistics|ja_jp-26.3-Heavy-Impact-Ballistics]]
        |       (Kinetic speed, gravity scaling math, Ballistics enchantment, sonic shockwaves)
        |
        +---> [[⚡ Quick Draw Mechanics|ja_jp-26.3-Quick-Draw-Mechanics]]
        |       (Reload ticks, Quick Charge integration, tension curves)
        |
        +---> [[⚙️ Configuration & GameRules|ja_jp-26.3-Configuration-and-GameRules]]
        |       (Dynamic GameRules, JSON configs, YACL v3 GUI, player agency values)
        |
        +---> [[💻 Architecture & Mixins|ja_jp-26.3-Architecture-and-Mixins]]
                (Package breakdown, Mixin injection points, API facades)
```

---

## 📋 MC 26.3 ビルド仕様

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.3` / `26.3-snapshot-6` (`>=26.3-`) |
| **Mod Version** | `1.0.14+26.3` |
| **Fabric Loader** | `>=0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **Java Requirement** | Java 25 (`>=25`) |
| **DasikLibrary** | `1.8.39` (`>=1.8.38`) |
| **Parchment Mappings** | `2026.01.22` |
| **Subproject Source** | `Better Crossbows v26.3/better-crossbows/` |

---

## 🌟 主要なハイライトと新機能 (26.3)

1. **Mixin近代化**: `shootProjectile` に MixinExtras `@WrapOperation` を採用し、壊れやすい順序依存インジェクションを完全排除。
2. **真のサンドボックスの自由**: プレイヤーの自由とアンチナニー原則に従い、GameRulesの上限を `Integer.MAX_VALUE` まで完全開放。
3. **専用サーバーの堅牢性**: シングルプレイ用処理を `@Environment(EnvType.CLIENT)` で完全に隔離し、Linuxサーバーでのクラッシュを防止。
4. **クリエイティブタブの即時キャッシュ破棄**: GameRules変更時にエンチャント本リストをワールド再読み込みなしで即座に更新。

---

## 🧭 Navigation
- [[Master Home Portal|ja_jp-Home]]
- [[Version Compatibility|ja_jp-Version-Compatibility]]
- [[Developer Setup|ja_jp-Developer-Setup-and-Building]]
- [[Switch to Minecraft 26.2|ja_jp-26.2-Home]]
