# 🏹 Vanilla Outsider: 更好弩 (Better Crossbows) 官方維基

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **程式碼儲存庫源碼免責聲明**：本維基文件反映了**儲存庫當前的原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新開發提交或未發布功能。

---

## 🎯 歡迎來到官方文件

**Better Crossbows** 是 **Vanilla Outsider 系列** 中的一款精準戰鬥與射擊機制模組，由 **Dasik (Rifaditya)** 打造。該模組將弩重塑為專業的**重型彈道發射平台**，專注於彈丸動能速度、平直彈道下墜以及模組化裝填拉弦時間，而非單純堆砌數值傷害。

無論你是在生存模式中校準遠距離彈道落點的神射手，還是希望接入發射速度倍率的附屬模組開發者，本文件均提供詳盡的技術細節、數學公式與內部架構說明。

---

## 🧭 活動版本選擇傳送門

請選擇你的目標 Minecraft 版本以進入對應獨立文件樹：

| 目標 Minecraft 版本 | 模組發行定位 | 執行時期工具鏈 | 狀態 | 文件傳送入口 |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 當前主版本 | [[👉 進入 MC 26.3 維基|zh_tw-26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 對齊版本 | [[👉 進入 MC 26.2 維基|zh_tw-26.2-Home]] |

> [!NOTE]
> 遵循模組的 **單版本單 Jar 策略 (1 Jar 1 Version Policy)**，每個版本分支均作為具有獨立相依性映射的完備製品進行建置。

---

## 🌟 核心子系統概覽

- **[[重型衝擊彈道|zh_tw-26.3-Heavy-Impact-Ballistics]]**：
  - 預設弩箭發射初速提升為 **1.5× 動能倍率** ($150\%$)。
  - 重力加速度動態按比例縮減 ($g_{\text{eff}} = g_0 / M$)，帶來平直的狙擊彈道。
  - **彈道學附魔 (`bettercrossbows:ballistics`)**：每級提升 +25% 速度（V 級達到 +125%），與多重射擊互斥。
  - 音效與粒子衝擊波：超音速發射伴隨深沉的破空爆破聲與定向粒子衝擊波。
- **[[快速拉弦機制|zh_tw-26.3-Quick-Draw-Mechanics]]**：
  - 透過遊戲規則自訂裝填刻數，並完美相容快速裝填 (Quick Charge) 附魔。
- **[[設定與遊戲規則|zh_tw-26.3-Configuration-and-GameRules]]**：
  - 基於 DasikLibrary 動態註冊的遊戲規則 (`bettercrossbows:better_crossbows`)。
  - 徹底解鎖全部整數空間 (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`)，實踐沙盒玩家自由與反保姆原則。
  - 選配 YACL v3 用戶端 GUI，無伺服端類別載入隱患。
- **[[架構與 Mixin 剖析|zh_tw-26.3-Architecture-and-Mixins]]**：
  - 深入剖析包含 `CrossbowItemMixin` 與 `AbstractArrowMixin` 在內的所有注入節點。

---

## 📜 製作署名與授權協議

- **作者與維護者**: **Dasik (Rifaditya)**
- **授權協議**: **GNU General Public License v3.0 (GPLv3)**
- **程式碼儲存庫**: [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
