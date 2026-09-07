# 🏹 Vanilla Outsider: Better Crossbows Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Avertissement sur le code source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source du dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en cours de développement avant leur mise en ligne sur CurseForge et Modrinth.

---

## 🎯 Bienvenue sur la documentation officielle

**Better Crossbows** est un mod de combat et de mécanique de tir de la collection **Vanilla Outsider Collection**, conçu par **Dasik (Rifaditya)**. Il transforme l'arbalète en une véritable **plateforme de tir lourd**, privilégiant la vélocité cinétique, une trajectoire tendue et un temps d'armement paramétrable plutôt qu'une simple hausse artificielle des dégâts.

---

## 🧭 Portail de sélection de versions

Sélectionnez votre version ciblée de Minecraft pour accéder à l'arborescence dédiée :

| Version Minecraft | Version du mod | Environnement | Statut | Accès direct |
| :--- | :--- | :--- | :---: | :--- |
| **Minecraft 26.3** | `1.0.14+26.3` | Fabric Loader `>=0.19.3` / Java 25 | 🟢 Actif actuel | [[👉 Ouvrir le Wiki MC 26.3|fr_fr-26.3-Home]] |
| **Minecraft 26.2** | `1.0.14+26.2` | Fabric Loader `>=0.19.3` / Java 25 | 🟡 Parité | [[👉 Ouvrir le Wiki MC 26.2|fr_fr-26.2-Home]] |

---

## 🌟 Aperçu des mécaniques clés

- **[[Balistique d'impact lourd|fr_fr-26.3-Heavy-Impact-Ballistics]]** :
  - Vitesse initiale par défaut augmentée de **1.5×** ($150\%$).
  - Aplatissement dynamique de trajectoire : la gravité de la flèche diminue proportionnellement à la vitesse ($g_{\text{eff}} = g_0 / M$).
  - **Enchantement Balistique (`bettercrossbows:ballistics`)** : confère +25% de vélocité par niveau (jusqu'à +125% au niveau V) ; incompatible avec Tir multiple.
  - Détonation supersonique sonore et ondes de choc de particules.
- **[[Mécanique de tension rapide|fr_fr-26.3-Quick-Draw-Mechanics]]** :
  - Durée d'armement configurable en ticks serveur, entièrement compatible avec Charge rapide (Quick Charge).
- **[[Configuration et GameRules|fr_fr-26.3-Configuration-and-GameRules]]** :
  - GameRules dynamiques enregistrées avec DasikLibrary (`bettercrossbows:better_crossbows`).
  - Espace entier non bridé (`[Integer.MIN_VALUE, Integer.MAX_VALUE]`) respectant la liberté totale de jeu en mode bac à sable.
  - Interface graphique YACL v3 optionnelle côté client.
- **[[Architecture et Mixins|fr_fr-26.3-Architecture-and-Mixins]]** :
  - Décomposition technique des points d'injection dans `CrossbowItemMixin`, `AbstractArrowMixin`, etc.

---

## 📜 Crédits et licence

- **Auteur** : **Dasik (Rifaditya)**
- **Licence** : **GNU General Public License v3.0 (GPLv3)**
- **Code source** : [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Better-Crossbows)
