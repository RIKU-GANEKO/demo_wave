-- =========================================
-- デモテーブルに活動報告URLカラムを追加
-- =========================================

ALTER TABLE demo
ADD COLUMN activity_report_url VARCHAR(500);

-- コメント
COMMENT ON COLUMN demo.activity_report_url IS '活動報告URL（TwitterスレッドなどのURL）';
