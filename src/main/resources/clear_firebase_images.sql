-- Firebase Storageのプロフィール画像URLをクリア
-- Firebase StorageからSupabase Storageに移行したため、古いFirebase URLを削除

UPDATE users 
SET profile_image_path = NULL 
WHERE profile_image_path LIKE 'https://firebasestorage.googleapis.com/%'
   OR profile_image_path LIKE 'https://storage.googleapis.com/%'
   OR profile_image_path LIKE '%firebase%';

-- 実行結果を確認
SELECT id, name, email, profile_image_path 
FROM users 
WHERE profile_image_path IS NOT NULL;