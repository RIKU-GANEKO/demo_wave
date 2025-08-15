INSERT INTO account (
    id, name, contract_status, company_name, zip, address, tel,
    registered_business_number, bank_name, bank_code, branch_name,
    branch_code, account_type, account_number, account_holder,
    ad_unit_id, created_at, updated_at, deleted_at
) VALUES (
    1, 'GanekoRiku', 1, 'GanekoCampany', '1111111', '住所', '03-1111-0000',
    NULL, '銀行名', '銀行コード', '支店名', '支店コード', 1, '口座番号',
    '口座名義', NULL, NULL, NULL, NULL
);

INSERT INTO users (
    id, account_id, name, email, password, status, last_login,
    created_at, updated_at, deleted_at
) VALUES (
    1, 1, 'GanekoRiku', 'ganeko@test.com',
    '$2a$10$H0jlLW2mTBpATH19G5ie2ei/Wxj2.wRfdHa21rVHBq3pYOpCaFAza',
    1, '2024-09-27 14:50:50', NULL, NULL, NULL
);

INSERT INTO roles (id, role) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

INSERT INTO demo (id, title, content, demo_date, demo_place, demo_address, demo_address_latitude, demo_address_longitude, organizer_user_id, announcement_time, created_at, updated_at)
VALUES
(1, 'demo in shibuya', 'we have demo in 1/1', '2025-01-05 16:58:58', 'shibuya', '沖縄県那覇市上間1-19-3', 26.202222300, 127.708866200, 1, '2025-01-05 16:58:58', '2025-01-05 16:58:58', '2025-01-05 16:58:58'),
(2, 'demo in shinjuku', 'we have demo in 1/2', '2025-01-05 22:18:13', 'shinjuku', '沖縄県那覇市上間1-19-3', 26.202222300, 127.708866200, 1,'2025-01-05 22:18:13', '2025-01-05 22:18:13', '2025-01-05 22:18:13'),
(3, 'demo in ikebukuro', 'we have demo in 1/3', '2025-01-05 22:18:13', 'ikebukuro', '沖縄県那覇市上間1-19-3', 26.202222300, 127.708866200, 1, '2025-01-05 22:18:13', '2025-01-05 22:18:13', '2025-01-05
22:18:13');

-- コメントデータを挿入
INSERT INTO comment (demo_id, content, user_id, created_at, updated_at, deleted_at) VALUES
(1, 'We support you!', 1, NOW(), NOW(), NULL),
(1, 'このデモ活動は非常に興味深いです。', 1, NOW(), NOW(), NULL),
(1, '参加を検討しています！', 1, NOW(), NOW(), NULL),
(2, '詳細をもっと知りたいです。', 1, NOW(), NOW(), NULL),
(2, '素晴らしい活動ですね！応援しています。', 1, NOW(), NOW(), NULL),
(3, 'オンラインでの参加方法はありますか？', 1, NOW(), NOW(), NULL),
(3, 'このイベントの詳細なスケジュールはどこで確認できますか？', 1, NOW(), NOW(), NULL),
(4, '友人と一緒に参加する予定です！楽しみです！', 1, NOW(), NOW(), NULL),
(4, 'デモ会場の最寄り駅を教えてください。', 1, NOW(), NOW(), NULL),
(5, 'イベントの趣旨にとても共感しています。', 1, NOW(), NOW(), NULL),
(5, 'このような活動がもっと増えてほしいです。応援しています！', 1, NOW(), NOW(), NULL);

-- 参加者データを挿入
INSERT INTO participant (demo_id, user_id, created_at, updated_at)
VALUES (5, 1, NOW(), NOW());

-- カテゴリーデータを挿入
INSERT INTO category (name, image_url, created_at, updated_at) VALUES
('environment', '/images/category/environment.png', NOW(), NOW()),
('anti_war', '/images/category/anti_war.png', NOW(), NOW()),
('animal_rights', '/images/category/animal_rights.png', NOW(), NOW()),
('politics', '/images/category/politics.png', NOW(), NOW()),
('human_rights', '/images/category/human_rights.png', NOW(), NOW()),
('social_walfare', '/images/category/social_welfare.png', NOW(), NOW());

INSERT INTO prefecture (name) VALUES
('北海道'),
('青森県'),
('岩手県'),
('宮城県'),
('秋田県'),
('山形県'),
('福島県'),
('茨城県'),
('栃木県'),
('群馬県'),
('埼玉県'),
('千葉県'),
('東京都'),
('神奈川県'),
('新潟県'),
('富山県'),
('石川県'),
('福井県'),
('山梨県'),
('長野県'),
('岐阜県'),
('静岡県'),
('愛知県'),
('三重県'),
('滋賀県'),
('京都府'),
('大阪府'),
('兵庫県'),
('奈良県'),
('和歌山県'),
('鳥取県'),
('島根県'),
('岡山県'),
('広島県'),
('山口県'),
('徳島県'),
('香川県'),
('愛媛県'),
('高知県'),
('福岡県'),
('佐賀県'),
('長崎県'),
('熊本県'),
('大分県'),
('宮崎県'),
('鹿児島県'),
('沖縄県');