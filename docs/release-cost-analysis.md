# DemoWave リリース費用分析

## 📋 概要

DemoWave（iOS + Web）をリリースする際にかかる費用を段階別に分析したドキュメントです。個人開発者向けに最適なインフラ構成と費用を整理しています。

## 🏗️ システム構成

### 現在の技術スタック
- **モバイル**: React Native (iOS)
- **バックエンド**: Spring Boot + MySQL
- **フロントエンド**: Thymeleaf + HTML/CSS/JS
- **認証**: Firebase Authentication
- **ストレージ**: Firebase Storage
- **地図**: Google Maps API

### 推奨移行後スタック
- **モバイル**: React Native (iOS)
- **バックエンド**: Spring Boot + PostgreSQL
- **フロントエンド**: Thymeleaf + HTML/CSS/JS
- **認証**: Supabase Authentication
- **ストレージ**: Supabase Storage
- **地図**: Google Maps API

## 💰 段階別費用分析

### 🚀 開発・テスト段階（月額 $0-8）

#### 推奨構成: Railway + Supabase
| サービス | 費用 | 内容 |
|---------|------|------|
| **Railway** | $0-5/月 | 無料枠内でバックエンド |
| **Supabase** | $0/月 | PostgreSQL + Auth + Storage |
| **Apple Developer** | $8/月 | $99/年（iOS配信必須） |
| **Google Maps API** | $0/月 | $200/月 クレジット |
| **SendGrid** | $0/月 | 3,000通/月 無料 |
| **総額** | **$8/月** | |

#### 旧構成: Railway + Firebase（参考）
| サービス | 費用 | 内容 |
|---------|------|------|
| **Railway** | $0-5/月 | 無料枠内でバックエンド・DB |
| **Firebase Auth** | $0/月 | 50,000 MAU まで無料 |
| **Firebase Storage** | $0/月 | 5GB まで無料 |
| **Apple Developer** | $8/月 | iOS配信 |
| **総額** | **$8/月** | |

### 📈 初期リリース段階（月額 $5-13）

#### 推奨構成: Railway + Supabase
| サービス | 費用 | 内容 |
|---------|------|------|
| **Railway** | $5/月 | Spring Boot Web Service |
| **Supabase** | $0/月 | PostgreSQL + Auth + Storage 無料枠 |
| **Apple Developer** | $8/月 | iOS配信 |
| **Google Maps API** | $0/月 | 無料枠内 |
| **SendGrid** | $0/月 | 3,000通/月 無料 |
| **総額** | **$13/月** | |

### 🎯 中規模運用（月額 $40-70）

#### Supabase有料移行時
| サービス | 費用 | 内容 |
|---------|------|------|
| **Railway** | $10/月 | スケールアップ |
| **Supabase Pro** | $25/月 | 8GB DB + 高性能 |
| **Apple Developer** | $8/月 | iOS配信 |
| **Google Maps API** | $30/月 | 4,000回/日 表示 |
| **SendGrid** | $15/月 | メール送信 |
| **総額** | **$88/月** | |

### 🏢 大規模運用（月額 $400-800）

#### エンタープライズ構成
| サービス | 費用 | 内容 |
|---------|------|------|
| **Railway/AWS** | $150/月 | 高性能インフラ |
| **Supabase Pro** | $100/月 | 大容量DB |
| **Apple Developer** | $8/月 | iOS配信 |
| **Google Maps API** | $200/月 | 大量アクセス |
| **SendGrid** | $80/月 | 大量メール |
| **総額** | **$538/月** | |

## 🛠️ 推奨インフラ構成

### 💎 最推奨: Railway + Supabase 分離構成

```
Railway Web Service ($5-10/月)
├── Spring Boot API
├── Thymeleaf Web画面
└── 静的ファイル (LP)

Supabase ($0-25/月)
├── PostgreSQL Database
├── Authentication (Google/Email)
├── Storage (画像・ファイル)
└── リアルタイム機能

iOS App (React Native)
├── App Store配信
├── Railway API接続
└── Supabase直接接続（認証・画像）

外部サービス
└── Google Maps API（地図）
```

**メリット:**
- **DB費用が無料** (500MB無料枠)
- **認証・ストレージ統合**
- **高性能・自動スケール**
- **開発体験が最高**
- **1-2日で移行可能**

**移行必要作業:**
- MySQL → PostgreSQL (データ型調整)
- Firebase Auth → Supabase Auth
- Firebase Storage → Supabase Storage

### 旧構成: Railway集約構成（参考）

```
DemoWave Project (Railway)
├── Web Service ($5-10/月)
│   ├── Spring Boot API
│   ├── Thymeleaf Web画面
│   └── 静的ファイル (LP)
└── MySQL Database ($5/月)
    ├── ユーザーデータ
    ├── デモデータ
    └── コメントデータ

外部サービス
├── Firebase Auth（認証）
├── Firebase Storage（画像）
└── Google Maps API（地図）
```

**デメリット:**
- DB料金が発生
- サービス分散で管理複雑

### 将来的選択肢: フル分離構成

```
Vercel (フロントエンド)
├── Next.js アプリ
├── ランディングページ
└── 管理画面

Railway (バックエンド)
├── Spring Boot API

Supabase (データ・認証)
├── PostgreSQL
├── Authentication  
└── Storage
```

**適用時期:**
- 大規模運用時
- フロントエンド技術刷新時

## 📊 無料枠の活用

### 🔥 Supabase（商用利用OK・推奨）

| サービス | 無料枠 | Pro ($25/月) |
|---------|-------|--------|
| PostgreSQL Database | 500MB | 8GB |
| Authentication | 50,000 MAU | 100,000 MAU |
| Storage | 1GB | 100GB |
| API Requests | 無制限 | 無制限 |
| Realtime | 200 concurrent | 500 concurrent |

### Firebase（商用利用OK・参考）

| サービス | 無料枠 | 超過後 |
|---------|-------|--------|
| Authentication | 50,000 MAU | $0.0055/MAU |
| Storage | 5GB + 1GB/日転送 | $0.026/GB/月 |
| Firestore | 1GB + 50K読込/日 | $0.18/GB/月 |

### Google Cloud Platform

| サービス | 無料枠 | 超過後 |
|---------|-------|--------|
| Maps JavaScript API | $200/月クレジット | $7/1000回 |
| Places API | $200/月クレジット | $17/1000回 |
| Directions API | $200/月クレジット | $5/1000回 |

### Railway

| 項目 | Development | Pro |
|------|-------------|-----|
| 月額 | $5まで無料 | 従量課金 |
| プロジェクト数 | 3個まで | 無制限 |
| カスタムドメイン | ❌ | ✅ |
| チーム機能 | ❌ | ✅ |

## 🎯 段階的リリース戦略

### フェーズ1: MVP（最小実行可能製品）
```
目標: 基本機能のリリース
期間: 1-3ヶ月
費用: $8/月
構成: Railway + Supabase無料枠

技術構成:
- Railway Web Service (無料枠)
- Supabase PostgreSQL + Auth + Storage (無料枠)
- Apple Developer Program ($8/月)
```

### フェーズ2: グロース
```
目標: ユーザー獲得・機能拡張
期間: 3-12ヶ月
費用: $13-40/月
構成: Railway有料 + Supabase無料継続

技術構成:
- Railway Web Service ($5-10/月)
- Supabase 無料枠継続 (500MB内)
- Google Maps API活用開始
```

### フェーズ3: スケール
```
目標: 大規模運用・収益化
期間: 1年以上
費用: $50-200/月
構成: Railway + Supabase Pro

技術構成:
- Railway Web Service ($10-20/月)
- Supabase Pro ($25/月)
- Google Maps API本格利用
- SendGrid有料プラン
```

## ⚠️ 注意事項・制限

### 商用利用について
- **Supabase**: 商用利用OK（利用規約遵守）
- **Railway**: 商用利用OK
- **Google Maps**: 商用利用OK（$200無料クレジット適用）
- **SendGrid**: 商用利用OK

### 無料枠の監視
```bash
# Supabase Dashboard
- Database使用量確認 (500MB枠)
- Authentication MAU確認 (50,000枠)
- Storage使用量確認 (1GB枠)
- API Request数確認

# Railway Dashboard  
- CPU/RAM使用量確認
- 月額費用確認 ($5無料枠)

# Google Cloud Console
- Maps API使用量確認 ($200クレジット)
- 使用量アラート設定
```

### スケール時の課題と対策
- **Supabase**: 500MB超過時 → Pro ($25/月) への移行
- **Google Maps**: 表示回数増加で料金上昇 → Mapbox検討
- **Railway**: 大規模時はAWSより高額 → 段階的移行計画

### MySQL → PostgreSQL 移行時の注意点
```sql
-- データ型の違い
MySQL: TINYINT → PostgreSQL: SMALLINT
MySQL: DATETIME → PostgreSQL: TIMESTAMP
MySQL: AUTO_INCREMENT → PostgreSQL: SERIAL

-- 設定変更
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driver-class-name=org.postgresql.Driver
```

## 🔄 代替サービス検討

### データベース・認証統合サービス
| サービス | 無料枠 | 特徴 | 推奨度 |
|---------|-------|------|--------|
| **Supabase** | **500MB DB + 50,000 MAU** | **PostgreSQL + Auth + Storage** | **⭐⭐⭐⭐⭐** |
| Firebase | 1GB Firestore + 50,000 MAU | NoSQL + Auth + Storage | ⭐⭐⭐⭐ |
| PlanetScale | 5GB MySQL | MySQL特化・高性能 | ⭐⭐⭐ |

### 認証のみサービス
| サービス | 無料枠 | 特徴 |
|---------|-------|------|
| Supabase Auth | 50,000 MAU | PostgreSQL統合 |
| Firebase Auth | 50,000 MAU | 豊富な認証方法 |
| Auth0 | 7,000 MAU | エンタープライズ向け |

### 地図サービス
| サービス | 無料枠 | 特徴 |
|---------|-------|------|
| Google Maps | $200/月 | 高機能・高精度 |
| Mapbox | 50,000回/月 | カスタマイズ性 |
| OpenStreetMap | 完全無料 | オープンソース |

### メール送信
| サービス | 無料枠 | 特徴 |
|---------|-------|------|
| SendGrid | 100通/日 | 高い到達率 |
| Resend | 3,000通/月 | 開発者向け |
| Amazon SES | 62,000通/月 | AWS統合 |

## 🚀 移行ロードマップ

### ステップ1: Supabaseセットアップ（1日目）
```bash
1. Supabaseアカウント作成
2. プロジェクト作成
3. データベース接続情報取得
4. Authentication設定
5. Storage設定
```

### ステップ2: データベース移行（2日目）
```bash
1. 既存MySQLデータをエクスポート
2. PostgreSQL用にスキーマ調整
3. Supabaseにデータインポート
4. 接続テスト
```

### ステップ3: アプリケーション設定変更（半日）
```bash
1. application.propertiesの変更
2. JPA設定の調整
3. 認証ロジックの移行
4. テスト・デプロイ
```

## 📝 まとめ

**DemoWaveの最適リリース構成: Railway + Supabase**

DemoWaveのリリースは**月額$8-13**から始めることができ、ユーザー1000人程度まではSupabaseの無料枠内で運用可能です。

**🎯 推奨アプローチ:**
1. **Railway + Supabase** 構成に移行
2. **PostgreSQL + 統合認証** で開発効率向上
3. **DB費用完全無料** でコスト削減
4. ユーザー増加に応じて段階的にスケール

**💰 コスト比較:**
- 旧構成（Railway集約）: $10-18/月
- **新構成（Railway + Supabase）: $8-13/月**
- **初年度で$60-120の節約効果**

**🛠️ 移行メリット:**
- DB運用費用ゼロ
- 認証・ストレージ統合
- 高性能・自動スケール
- 優れた開発体験
- 2日で移行完了

**ユーザー1000人程度なら月額$13で継続運用可能**で、個人開発者にとって最も現実的で効率的な選択肢です。

**💡 長期運用の見通し:**
- 500MB DB容量内なら**永続的に無料**
- 50,000 MAU以内なら**認証費用ゼロ**
- 小〜中規模サービスなら**数年間この価格帯で運用可能**

---

*最終更新: 2025年8月*