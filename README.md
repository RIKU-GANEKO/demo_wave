# Demo Wave

**デモ活動を通じて、あなたの声を社会に届けるプラットフォーム**

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Stripe](https://img.shields.io/badge/Stripe-Payment-purple)

## サービス概要

Demo Waveは、市民の声を可視化し、社会課題の解決に向けた行動を支援するWebプラットフォームです。

環境問題、人権問題、政治的な課題など、様々な社会問題に対してデモ活動への参加ハードルを下げ、より多くの人々が社会運動に関わることができる世界を目指しています。

### サービスURL
https://www.demo-wave.com

## 主な機能

| 機能 | 説明                |
|------|-------------------|
| デモ検索 | カテゴリ・地域でデモ活動を検索   |
| デモ登録 | Google Maps連携で開催場所を設定し、デモを主催 |
| 参加表明 | ワンクリックでデモ活動への参加を表明 |
| 応援ポイント | ポイントを購入し、デモ活動に付与。 |
| 報酬分配 | 位置情報で参加を確認し、デモ参加者に報酬を分配 |
| マイページ | 参加・応援・投稿したデモの履歴管理 |

## 開発背景

現代社会では様々な課題が山積していますが、デモ活動への参加は情報が分散していたり、どう関わればいいか分からなかったりとハードルが高いのが現状です。

Demo Waveは以下の課題を解決します：

1. **情報の分散** → デモ活動情報を一元化
2. **参加方法の不明確さ** → 簡単な参加表明システム
3. **遠方からの応援ができない** → 応援ポイントによる金銭的支援
4. **参加者へのインセンティブ不足** → 応援ポイントの報酬分配

## 技術スタック

### バックエンド
- **言語**: Java 21
- **フレームワーク**: Spring Boot 3.1
- **ORM**: Spring Data JPA / Hibernate
- **認証**: Supabase Auth (OAuth対応)

### フロントエンド
- **テンプレートエンジン**: Thymeleaf
- **CSS**: Tailwind CSS
- **JavaScript**: Vanilla JS / jQuery

### データベース
- **本番**: PostgreSQL (Supabase)

### インフラ・外部サービス
- **ホスティング**: Railway
- **決済**: Stripe
- **地図**: Google Maps API
- **認証**: Supabase Auth

## システム構成

```
┌─────────────────┐     ┌─────────────────┐
│    Browser      │────▶│    Railway      │
│   (Thymeleaf)   │     │  (Spring Boot)  │
└─────────────────┘     └────────┬────────┘
                                 │
        ┌────────────────────────┼────────────────────────┐
        │                        │                        │
        ▼                        ▼                        ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│   Supabase    │    │    Stripe     │    │  Google Maps  │
│  (PostgreSQL  │    │  (Payment)    │    │     API       │
│   + Auth)     │    │               │    │               │
└───────────────┘    └───────────────┘    └───────────────┘
```

## ポイント・報酬フロー

```
1. 応援者がStripeでポイント購入
         │
         ▼
2. 応援者がデモにポイントを送付
         │
         ▼
3. デモ開催日に参加者の位置情報を確認
         │
         ▼
4. 実参加者を特定（位置情報ベース）
         │
         ▼
5. 運営が月次で報酬を計算・分配
         │
         ▼
6. 実参加者へ報酬を支払い
```

## プロジェクト構成

```
src/main/java/product/demo_wave/
├── api/              # REST APIエンドポイント
├── common/           # 共通ユーティリティ
├── config/           # 設定クラス
├── demo/             # デモ関連機能
├── entity/           # JPA Entity
├── mypage/           # マイページ機能
├── point/            # ポイント機能
├── repository/       # データアクセス層
└── service/          # ビジネスロジック

src/main/resources/
├── templates/        # Thymeleafテンプレート
└──static/           # 静的ファイル
```

## ライセンス

This project is proprietary software. All rights reserved.

---

**Demo Wave** - 声を、波に。
