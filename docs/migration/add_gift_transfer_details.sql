-- =========================================
-- ギフト送金明細テーブル（デモごとの受取金額）
-- =========================================

CREATE TABLE IF NOT EXISTS gift_transfer_details (
    id SERIAL PRIMARY KEY,
    gift_transfer_id INTEGER NOT NULL REFERENCES gift_transfers(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id),
    demo_id INTEGER NOT NULL REFERENCES demo(id),
    amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- インデックス
CREATE INDEX idx_gift_transfer_details_user ON gift_transfer_details(user_id);
CREATE INDEX idx_gift_transfer_details_demo ON gift_transfer_details(demo_id);
CREATE INDEX idx_gift_transfer_details_gift_transfer ON gift_transfer_details(gift_transfer_id);
CREATE INDEX idx_gift_transfer_details_user_created ON gift_transfer_details(user_id, created_at);

-- コメント
COMMENT ON TABLE gift_transfer_details IS 'ギフト送金明細（デモごとの受取金額詳細）';
COMMENT ON COLUMN gift_transfer_details.gift_transfer_id IS '親の送金履歴ID（gift_transfers参照）';
COMMENT ON COLUMN gift_transfer_details.user_id IS '受取ユーザーID';
COMMENT ON COLUMN gift_transfer_details.demo_id IS '対象デモID';
COMMENT ON COLUMN gift_transfer_details.amount IS 'このデモから受け取った金額（円）';
COMMENT ON COLUMN gift_transfer_details.created_at IS '登録日時';
COMMENT ON COLUMN gift_transfer_details.deleted_at IS '論理削除日時';
