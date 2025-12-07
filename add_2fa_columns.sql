-- 二要素認証（2FA）用のカラムをusersテーブルに追加
-- 管理者ログインのセキュリティ強化のため

-- 2FAシークレットキー用カラム（Google Authenticatorで使用）
ALTER TABLE users ADD COLUMN IF NOT EXISTS two_factor_secret VARCHAR(255);

-- 2FAが有効かどうかのフラグ
ALTER TABLE users ADD COLUMN IF NOT EXISTS two_factor_enabled BOOLEAN DEFAULT FALSE;

-- コメント追加
COMMENT ON COLUMN users.two_factor_secret IS 'Google Authenticator用のTOTPシークレットキー';
COMMENT ON COLUMN users.two_factor_enabled IS '二要素認証が有効かどうか（管理者用）';
