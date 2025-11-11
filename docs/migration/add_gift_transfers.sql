-- =========================================
-- ギフト送金履歴テーブル
-- =========================================

-- 1. gift_transfers テーブル（送金履歴）
CREATE TABLE IF NOT EXISTS gift_transfers (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    transfer_month DATE NOT NULL,  -- 対象月（例: 2025-01-01）
    total_amount DECIMAL(10, 2) NOT NULL,  -- 送金額
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),  -- 管理者ID
    deleted_at TIMESTAMP,

    CONSTRAINT unique_user_month UNIQUE(user_id, transfer_month)  -- 同じ月に複数回登録できない
);

CREATE INDEX idx_gift_transfers_user_month ON gift_transfers(user_id, transfer_month);
CREATE INDEX idx_gift_transfers_created_at ON gift_transfers(created_at);

COMMENT ON TABLE gift_transfers IS 'ギフトカード送金履歴';
COMMENT ON COLUMN gift_transfers.user_id IS '受取ユーザーID';
COMMENT ON COLUMN gift_transfers.transfer_month IS '対象月（月初日を記録）';
COMMENT ON COLUMN gift_transfers.total_amount IS '送金額（円）';
COMMENT ON COLUMN gift_transfers.created_by IS 'CSV出力を実行した管理者のユーザーID';
