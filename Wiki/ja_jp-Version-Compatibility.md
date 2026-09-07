# 📋 バージョン互換性およびツールチェーンマトリクス

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースコードに関する免責事項**: 本Wikiドキュメントは**リポジトリの最新ソースコード状態**を反映しており、CurseForgeやModrinthで公開されているリリース版に含まれない開発中機能を含む場合があります。

---

## 🏛️ 「1 Jar 1 Version」 ポリシー (1 Jar 1 Version Policy)

**Better Crossbows** は厳格な **1 Jar 1 Version ポリシー** に基づいて開発されています。各Minecraftバージョンは独立したサブプロジェクトで管理され、明確な依存関係とコンパイル成果物を持ちます：
- **無理なユニバーサルJarの排除**: 実行時の不安定なリフレクションを避け、バージョンごとに厳密に検証されたビルドを提供します。
- **専用リリース**: 各バージョン固有の成果物を配布（例: `better-crossbows-1.0.14+26.3.jar`）。
- **アーカイブ保存**: ビルド済みJARは `Archive Jar of all versions/` に永続保存されます。

---

## 📊 技術互換性マトリクス

| Minecraft Anchor | Mod Release | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary | Status |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Minecraft 26.3** | `1.0.14+26.3` | `>=0.19.3` | `0.156.1+26.3` | **Java 25** (`>=25`) | `2026.01.22` (26.3-snapshot-6) | `>=1.8.38` (Build: `1.8.39`) | 🟢 **現行アクティブ** |
| **Minecraft 26.2** | `1.0.14+26.2` | `>=0.19.3` | `0.149.0+26.2` | **Java 25** (`>=25`) | `2026.02.15` (26.2) | `>=1.8.38` (Build: `1.8.39`) | 🟡 **パリティアンカー** |

---

## 🧩 オプションおよび推奨依存関係

Better Crossbows はFabric APIとDasikLibrary以外のクライアント必須依存関係を持ちません。ただし、オプションModを追加することでリッチな設定画面が有効になります：

| Dependency | Suggested Bounds | Purpose | Client / Server Safe |
| :--- | :--- | :--- | :---: |
| **YetAnotherConfigLib (YACL v3)** | `*` (`yet-another-config-lib`) | Rich in-game graphical settings screen with interactive sliders | ✅ 100% Client-Safe (Zero server classloading) |
| **Cloth Config v13+** | `*` (`cloth-config`) | Fallback GUI provider for configuration screens | ✅ 100% Client-Safe |
| **ModMenu** | `*` (`modmenu`) | Adds in-game "Mods" screen button to configure Better Crossbows directly | ✅ Client-Only |
| **DasikLibrary** | `>=1.8.38` | Mandatory API library providing Dynamic GameRule registration, ConfigHelper, and Social APIs | 🌐 Universal (Required on both sides) |

---

## 🔒 サーバー／クライアント両端の安全性アーキテクチャ

すべてのクライアントGUIコードは **Screen Isolation Protocol** によって保護されています：
- `ModMenuIntegration` および `YaclScreenHelper` には明示的に `@Environment(EnvType.CLIENT)` が付与されています。
- エントリーポイントはDasikLibraryの `GuiHelper.getOptionalYaclFactory(...)` を経由して遅延解決されます。
- **専用サーバー (Dedicated Server)** 環境でも `ClassNotFoundException` や `NoClassDefFoundError: net/minecraft/client/Minecraft` を100%防止します。

---

## 🗄️ 過去ビルドとアーカイブ

過去のリリースビルドJARは以下に保管されています：
- `Archive Jar of all versions/`
- サブプロジェクトのローカルリリース: `<Subproject>/releases/`
- 公式Modrinthページ: [Better Crossbows on Modrinth](https://modrinth.com/mod/better-crossbows/versions)

---

## 🧭 ナビゲーション

- [[メインポータル|ja_jp-Home]] へ戻る
- [[MC 26.3 Overview|ja_jp-26.3-Home]]
- [[MC 26.2 Overview|ja_jp-26.2-Home]]
- [[Developer Setup|ja_jp-Developer-Setup-and-Building]]
