# Railway + Supabase 移行手順書

## 📋 概要

DemoWaveを現在のMySQL + Firebase構成から、**Railway + Supabase**構成に移行するための詳細手順書です。

### 移行後の構成
- **Web Service**: Railway (Spring Boot)
- **Database**: Supabase PostgreSQL
- **Authentication**: Supabase Auth
- **Storage**: Supabase Storage
- **Maps**: Google Maps API（継続）

### 予想作業時間
- **準備・セットアップ**: 2-3時間
- **データ移行**: 2-4時間
- **アプリケーション修正**: 4-6時間
- **テスト・デプロイ**: 1-2時間
- **合計**: 1-2日

## 🎯 移行の流れ

```
フェーズ1: 環境準備
├── Supabaseアカウント作成
├── Railway準備
└── 開発環境セットアップ

フェーズ2: データベース移行
├── スキーマ変換
├── データエクスポート
└── Supabaseインポート

フェーズ3: アプリケーション修正
├── 依存関係更新
├── 設定ファイル変更
├── 認証ロジック移行
└── ストレージ移行

フェーズ4: デプロイ・テスト
├── Railway デプロイ
├── 動作確認
└── 本番移行
```

---

## 📅 フェーズ1: 環境準備（2-3時間）

### Step 1-1: Supabase アカウント作成

#### 1. Supabase サインアップ
```bash
1. https://supabase.com にアクセス
2. "Start your project" をクリック
3. GitHub アカウントで認証
4. Organization 作成（個人名でOK）
```

#### 2. プロジェクト作成
```bash
1. "New project" をクリック
2. 設定項目:
   - Name: demo-wave
   - Database Password: 強固なパスワード設定
   - Region: Northeast Asia (Tokyo)
   - Pricing Plan: Free (無料枠)
3. "Create new project" をクリック
4. 初期化完了まで待機（2-3分）
```

#### 3. 接続情報取得
```bash
1. Settings > Database へ移動
2. Connection string をコピー:
   postgresql://postgres:[PASSWORD]@[HOST]:5432/postgres
3. 安全な場所に保存
```

### Step 1-2: Railway アカウント作成

#### 1. Railway サインアップ
```bash
1. https://railway.app にアクセス
2. "Start Building" をクリック
3. GitHub アカウントで認証
4. メール認証完了
```

#### 2. GitHub リポジトリ連携準備
```bash
1. GitHub で demo_wave リポジトリの public 化
   または Railway の private repo アクセス許可
2. Railway Dashboard で "Deploy from GitHub repo" 準備
```

### Step 1-3: 開発環境セットアップ

#### 1. PostgreSQL ドライバー追加
```xml
<!-- pom.xml に追加 -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- MySQL ドライバーは当面残しておく -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### 2. Supabase Java クライアント追加
```xml
<dependency>
    <groupId>io.supabase</groupId>
    <artifactId>supabase-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 📊 フェーズ2: データベース移行（2-4時間）

### Step 2-1: スキーマ分析・変換

#### 1. 現在のMySQLスキーマ確認
```sql
-- 現在のテーブル構造確認
SHOW TABLES;
DESCRIBE users;
DESCRIBE demos;
DESCRIBE comments;
-- その他全テーブル
```

#### 2. PostgreSQL用スキーマ変換
```sql
-- MySQL → PostgreSQL 変換例

-- users テーブル
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,                    -- AUTO_INCREMENT → SERIAL
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- DATETIME → TIMESTAMP
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- demos テーブル  
CREATE TABLE demos (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    demo_place VARCHAR(255),
    demo_start_date TIMESTAMP NOT NULL,          -- DATETIME → TIMESTAMP
    demo_end_date TIMESTAMP NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    prefecture_id BIGINT REFERENCES prefectures(id),
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- comments テーブル
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    demo_id BIGINT REFERENCES demos(id),
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- その他のテーブルも同様に変換
```

### Step 2-2: データエクスポート

#### 1. MySQL データダンプ
```bash
# 本番データがある場合
mysqldump -h localhost -u root -p demo_wave > demo_wave_backup.sql

# 開発データのみの場合
mysqldump -h localhost -u root -p demo_wave \
  --no-create-info \
  --complete-insert \
  > demo_wave_data.sql
```

#### 2. PostgreSQL用に変換
```bash
# sed コマンドで基本的な変換
sed -i '' 's/AUTO_INCREMENT/SERIAL/g' demo_wave_backup.sql
sed -i '' 's/DATETIME/TIMESTAMP/g' demo_wave_backup.sql
sed -i '' 's/TINYINT(1)/BOOLEAN/g' demo_wave_backup.sql

# より複雑な変換が必要な場合は手動で修正
```

### Step 2-3: Supabase にインポート

#### 1. Supabase SQL Editor でスキーマ作成
```bash
1. Supabase Dashboard > SQL Editor へ移動
2. "New query" をクリック
3. 変換したスキーマを貼り付け
4. "Run" をクリックして実行
5. エラーがないことを確認
```

#### 2. テストデータ投入
```sql
-- Supabase SQL Editor で実行
INSERT INTO categories (name) VALUES 
('人権'),
('環境'),
('反戦'),
('政治'),
('社会福祉'),
('動物の権利');

INSERT INTO prefectures (name) VALUES
('東京都'),
('大阪府'),
('愛知県'),
('神奈川県'),
('福岡県');

-- 必要最小限のテストデータを投入
```

---

## ⚙️ フェーズ3: アプリケーション修正（4-6時間）

### Step 3-1: 設定ファイル更新

#### 1. application.properties 修正
```properties
# application.properties

# 開発環境（ローカル）
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_wave_local
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA設定
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.sql.init.mode=never

# その他設定は既存のまま
```

#### 2. application-production.properties 作成
```properties
# application-production.properties（Railway用）

# Supabase PostgreSQL
spring.datasource.url=${DATABASE_URL}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Supabase 設定
supabase.url=${SUPABASE_URL}
supabase.key=${SUPABASE_ANON_KEY}
supabase.secret=${SUPABASE_SECRET_KEY}

# その他本番設定
logging.level.org.springframework=INFO
logging.level.product.demo_wave=INFO
```

### Step 3-2: Supabase 設定クラス作成

#### 1. SupabaseConfig.java 作成
```java
package product.demo_wave.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.supabase.Supabase;

@Configuration
public class SupabaseConfig {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Bean
    public Supabase supabaseClient() {
        return Supabase.createClient(supabaseUrl, supabaseKey);
    }
}
```

### Step 3-3: 認証ロジック移行

#### 1. Supabase Auth サービス作成
```java
package product.demo_wave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.supabase.Supabase;

@Service
public class SupabaseAuthService {

    @Autowired
    private Supabase supabaseClient;

    public String signInWithEmail(String email, String password) {
        try {
            var response = supabaseClient.auth().signInWithEmailAndPassword(email, password);
            return response.getUser().getId();
        } catch (Exception e) {
            throw new RuntimeException("認証に失敗しました", e);
        }
    }

    public String signUpWithEmail(String email, String password, String name) {
        try {
            var response = supabaseClient.auth().signUpWithEmailAndPassword(email, password);
            // プロフィール情報の追加処理
            return response.getUser().getId();
        } catch (Exception e) {
            throw new RuntimeException("ユーザー登録に失敗しました", e);
        }
    }

    public void signOut() {
        try {
            supabaseClient.auth().signOut();
        } catch (Exception e) {
            throw new RuntimeException("ログアウトに失敗しました", e);
        }
    }
}
```

#### 2. 既存認証ロジックを段階的に置換
```java
// LoginController.java の修正例
@Autowired
private SupabaseAuthService supabaseAuthService;

@PostMapping("/login")
public String login(@RequestParam String email, 
                   @RequestParam String password,
                   HttpSession session) {
    try {
        String userId = supabaseAuthService.signInWithEmail(email, password);
        session.setAttribute("userId", userId);
        return "redirect:/demo";
    } catch (Exception e) {
        return "login/login?error=true";
    }
}
```

### Step 3-4: ストレージ移行

#### 1. Supabase Storage サービス作成
```java
package product.demo_wave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.supabase.Supabase;

@Service
public class SupabaseStorageService {

    @Autowired
    private Supabase supabaseClient;

    public String uploadFile(MultipartFile file, String bucketName, String fileName) {
        try {
            byte[] fileBytes = file.getBytes();
            var response = supabaseClient.storage()
                .from(bucketName)
                .upload(fileName, fileBytes);
            
            return getPublicUrl(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException("ファイルアップロードに失敗しました", e);
        }
    }

    public String getPublicUrl(String bucketName, String fileName) {
        return supabaseClient.storage()
            .from(bucketName)
            .getPublicUrl(fileName);
    }

    public void deleteFile(String bucketName, String fileName) {
        try {
            supabaseClient.storage()
                .from(bucketName)
                .remove(fileName);
        } catch (Exception e) {
            throw new RuntimeException("ファイル削除に失敗しました", e);
        }
    }
}
```

---

## 🚀 フェーズ4: デプロイ・テスト（1-2時間）

### Step 4-1: Railway デプロイ

#### 1. Railway プロジェクト作成
```bash
1. Railway Dashboard > "New Project"
2. "Deploy from GitHub repo" 選択
3. demo_wave リポジトリ選択
4. "Deploy Now" をクリック
```

#### 2. 環境変数設定
```bash
# Railway Dashboard > Variables
DATABASE_URL=postgresql://postgres:[PASSWORD]@[HOST]:5432/postgres
SUPABASE_URL=https://[PROJECT_ID].supabase.co
SUPABASE_ANON_KEY=[ANON_KEY]
SUPABASE_SECRET_KEY=[SECRET_KEY]
SPRING_PROFILES_ACTIVE=production
```

#### 3. ドメイン設定
```bash
1. Railway Dashboard > Settings
2. "Generate Domain" または "Custom Domain" 設定
3. 生成されたURLを確認
```

### Step 4-2: 動作確認

#### 1. 基本機能テスト
```bash
✅ ログイン/ログアウト
✅ ユーザー登録
✅ デモ一覧表示
✅ デモ詳細表示
✅ デモ作成
✅ コメント投稿
✅ マイページ表示
```

#### 2. データベース確認
```bash
1. Supabase Dashboard > Table Editor
2. 各テーブルにデータが正しく保存されているか確認
3. リレーションが正しく機能しているか確認
```

#### 3. 認証確認
```bash
1. Supabase Dashboard > Authentication > Users
2. 新規登録ユーザーが表示されるか確認
3. ログイン/ログアウトが正常に動作するか確認
```

### Step 4-3: 本番移行

#### 1. DNS設定（独自ドメインがある場合）
```bash
1. Railway でカスタムドメイン設定
2. DNS レコード更新
3. SSL証明書自動取得確認
```

#### 2. 監視設定
```bash
1. Railway Dashboard でメトリクス確認
2. Supabase Dashboard で使用量確認
3. アラート設定（必要に応じて）
```

---

## ⚠️ 注意事項・トラブルシューティング

### データ型変換の注意点

```sql
-- よくある変換問題
MySQL: TINYINT(1) → PostgreSQL: BOOLEAN
MySQL: DATETIME → PostgreSQL: TIMESTAMP
MySQL: AUTO_INCREMENT → PostgreSQL: SERIAL
MySQL: TEXT → PostgreSQL: TEXT（そのまま）
```

### パフォーマンス最適化

```sql
-- インデックス作成（必要に応じて）
CREATE INDEX idx_demos_user_id ON demos(user_id);
CREATE INDEX idx_demos_category_id ON demos(category_id);
CREATE INDEX idx_comments_demo_id ON comments(demo_id);
```

### バックアップ計画

```bash
# 移行前に必ずバックアップ
1. MySQL データ完全バックアップ
2. Firebase データエクスポート
3. アプリケーションコードのGitタグ作成
```

### ロールバック手順

```bash
緊急時のロールバック:
1. Railway から旧構成に戻す
2. DNS設定を旧環境に戻す
3. データベース接続を MySQL に戻す
```

---

## 📋 チェックリスト

### 移行前チェック
- [ ] 現在のMySQLデータのバックアップ完了
- [ ] Supabaseアカウント作成完了
- [ ] Railwayアカウント作成完了
- [ ] 開発環境での動作確認完了

### 移行中チェック
- [ ] スキーマ変換完了
- [ ] テストデータ投入完了
- [ ] アプリケーション設定変更完了
- [ ] 認証ロジック移行完了
- [ ] ストレージ移行完了

### 移行後チェック
- [ ] 全機能の動作確認完了
- [ ] パフォーマンス確認完了
- [ ] 監視設定完了
- [ ] ドキュメント更新完了

---

## 📞 サポート・リソース

### 公式ドキュメント
- [Supabase Documentation](https://supabase.com/docs)
- [Railway Documentation](https://docs.railway.app/)
- [Spring Boot PostgreSQL Guide](https://spring.io/guides/gs/accessing-data-postgresql/)

### よくある質問
- Q: 移行中にサービスを停止する必要はありますか？
- A: いいえ、段階的移行により無停止で移行可能です。

- Q: 無料枠を超過した場合はどうなりますか？
- A: 事前にアラート設定をして、有料プランへの移行を検討してください。

- Q: データが失われる可能性はありますか？
- A: 適切なバックアップを取得していれば、データ損失のリスクは最小限です。

---

*最終更新: 2025年8月*