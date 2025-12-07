-- ================================================================
-- Migration: 二要素認証（2FA）機能の追加
-- Date: 2025-11-30
-- Purpose: 管理者アカウントのセキュリティ強化のため、
--          Google Authenticator (TOTP) による二要素認証を実装
-- ================================================================

-- 二要素認証用のカラムをusersテーブルに追加
ALTER TABLE users ADD COLUMN IF NOT EXISTS two_factor_secret VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS two_factor_enabled BOOLEAN DEFAULT FALSE;

-- カラムの説明を追加
COMMENT ON COLUMN users.two_factor_secret IS 'Google Authenticator用のTOTPシークレットキー（管理者用）';
COMMENT ON COLUMN users.two_factor_enabled IS '二要素認証が有効かどうか（管理者用）';

-- ================================================================
-- Notes:
-- - two_factor_secret: Google Authenticatorで使用するTOTP秘密鍵
-- - two_factor_enabled: 2FAが有効化されているかのフラグ
-- - 一般ユーザーは2FA不要、管理者のみ使用
-- - Stripe セキュリティチェックリスト要件に対応
-- ================================================================

-- 実行確認
SELECT
    column_name,
    data_type,
    character_maximum_length,
    column_default,
    is_nullable
FROM information_schema.columns
WHERE table_name = 'users'
  AND column_name IN ('two_factor_secret', 'two_factor_enabled')
ORDER BY column_name;
