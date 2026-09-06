# 🏹 Vanilla Outsider: Better Crossbows 公式Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: このWikiのドキュメントは、CurseForgeおよびModrinthでの公開ビルドに先んじる最新の開発コミットや未リリースの機能を含む**リポジトリの現在のソースコード状態**を反映しています。

---

## 🎯 公式ドキュメントへようこそ

**Better Crossbows** は、**Dasik (Rifaditya)** が手掛ける **Vanilla Outsider Collection** の精密戦闘および弾道メカニクスModです。クロスボウを単なる攻撃力の水増しではなく、運動エネルギー速度、直線的な弾道落下、そしてモジュール式の装填時間を備えた専用の**重弾頭投射プラットフォーム**へと刷新します。

---

## 🧭 バージョン選択ポータル

ターゲットとするMinecraftのバージョンを選択して、専用ドキュメントツリーにアクセスしてください：

| 対象Minecraftバージョン | Modリリース | 実行環境ツールチェーン | 状態 | ドキュメントポータル |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 現行バージョン | [[👉 MC 26.3 Wikiへ入る|26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 パリティ版 | [[👉 MC 26.2 Wikiへ入る|26.2-Home]] |

---

## 🌟 主要機能の概要

- **[[重衝撃弾道メカニクス|26.3-Heavy-Impact-Ballistics]]**:
  - クロスボウ矢の初速がデフォルトで **1.5倍** ($150\%$) に向上。
  - 弾道の動的フラット化：矢にかかる重力が速度に応じて反比例して減少 ($g_{\text{eff}} = g_0 / M$)。
  - **弾道学エンチャント (`bettercrossbows:ballistics`)**: レベルごとに速度が +25% 増加（レベルVで最大 +125%）。拡散（マルチショット）とは排他。
  - 超音速発射時の重低音ソニックブーム爆発音および衝撃波パーティクル。
- **[[高速装填・テンション調整|26.3-Quick-Draw-Mechanics]]**:
  - ゲームルールによる装填ティック数の自在な調整。高速装填（Quick Charge）とも完全互換。
- **[[設定とゲームルール|26.3-Configuration-and-GameRules]]**:
  - DasikLibrary を用いた動的ゲームルール登録 (`bettercrossbows:better_crossbows`)。
  - プレイヤーの自由を重んじる反過保護原則に基づき、全整数範囲 (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`) を開放。
  - クライアント専用の YACL v3 設定画面に対応。
- **[[アーキテクチャとMixin解説|26.3-Architecture-and-Mixins]]**:
  - `CrossbowItemMixin` や `AbstractArrowMixin` をはじめとする全注入ポイントの詳細解説。

---

## 📜 クレジットとライセンス

- **制作者**: **Dasik (Rifaditya)**
- **ライセンス**: **GNU General Public License v3.0 (GPLv3)**
- **ソースコード**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
