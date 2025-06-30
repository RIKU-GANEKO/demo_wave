-- accountテーブルの作成
DROP TABLE IF EXISTS account;
CREATE TABLE account (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contract_status CHAR(1) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    zip VARCHAR(7) NOT NULL,
    address VARCHAR(255) NOT NULL,
    tel VARCHAR(255) NOT NULL,
    registered_business_number VARCHAR(14),
    bank_name VARCHAR(255) NOT NULL,
    bank_code VARCHAR(255) NOT NULL,
    branch_name VARCHAR(255) NOT NULL,
    branch_code VARCHAR(255) NOT NULL,
    account_type INT NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    account_holder VARCHAR(255),
    ad_unit_id INT,
    created_at TIMESTAMP NULL DEFAULT NULL,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    deleted_at TIMESTAMP NULL DEFAULT NULL
);

-- usersテーブルの作成
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    account_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status CHAR(1) NOT NULL,
    last_login TIMESTAMP NULL DEFAULT NULL,
    created_at TIMESTAMP NULL DEFAULT NULL,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

-- rolesテーブルの作成
DROP TABLE IF EXISTS `roles`;
CREATE TABLE roles (
    id INT NOT NULL AUTO_INCREMENT,
    role VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- user_roleテーブルの作成
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE user_role (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- demoテーブルの作成
DROP TABLE IF EXISTS `demo`;
CREATE TABLE demo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category_id INT NOT NULL,
    demo_date DATETIME NOT NULL,
    demo_place VARCHAR(100) NOT NULL,
    demo_address varchar(255) NOT NULL,
    demo_address_latitude DECIMAL(12,9) NULL,
    demo_address_longitude DECIMAL(12,9) NULL,
    organizer_user_id INT NOT NULL,
    announcement_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

-- commentテーブルの作成
DROP TABLE IF EXISTS `comment`;
CREATE TABLE comment (
    id INT AUTO_INCREMENT PRIMARY KEY,                -- 主キー
    demo_id INT NOT NULL,                      -- 外部キー (demo.id と型を一致させる)
    content TEXT NOT NULL,                            -- コメント内容
    user_id INT NOT NULL,                             -- ユーザーID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 作成日時
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新日時
    deleted_at TIMESTAMP DEFAULT NULL,                -- 削除日時（ソフトデリート用）
    FOREIGN KEY (demo_id) REFERENCES demo(id) -- 外部キー制約
);

-- participantテーブルの作成
DROP TABLE IF EXISTS `participant`;
CREATE TABLE participant (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 中間テーブルの主キー
    demo_id INT NOT NULL,      -- デモID (外部キー)
    user_id INT NOT NULL,             -- ユーザーID (外部キー)
    created_at TIMESTAMP NULL DEFAULT NULL,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    deleted_at TIMESTAMP NULL DEFAULT NULL
--    UNIQUE (demo_id, user_id) -- デモとユーザーの組み合わせはユニーク
);

-- paymentテーブルの作成
DROP TABLE IF EXISTS `payment`;
CREATE TABLE payment (
    id INT AUTO_INCREMENT PRIMARY KEY,                -- 主キー
    demo_id INT NOT NULL, -- 情報ID
    donate_user_id INT NOT NULL, -- 寄付ユーザーID
    donate_amount DECIMAL NOT NULL, -- 寄付金額
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 作成日時
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新日時
    deleted_at TIMESTAMP NULL DEFAULT NULL
);

-- categoryテーブルの作成
DROP TABLE IF EXISTS `category`;
CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,                   -- 主キー
    name VARCHAR(255) NOT NULL,                          -- カテゴリ名
    image_url VARCHAR(255) NOT NULL,                     -- 画像URL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- 作成日時
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新日時
    deleted_at TIMESTAMP NULL DEFAULT NULL               -- 削除日時（論理削除）
);

ALTER TABLE Users
ADD COLUMN firebase_uid VARCHAR(128) NOT NULL AFTER id;

ALTER TABLE users
ADD COLUMN profile_image_path VARCHAR(255) NULL
AFTER email;

DROP TABLE IF EXISTS `prefecture`;
CREATE TABLE prefecture (
    id INT AUTO_INCREMENT PRIMARY KEY,                   -- 主キー
    name VARCHAR(255) NOT NULL,                          -- 都道府県名
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,      -- 作成日時
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新日時
    deleted_at TIMESTAMP NULL DEFAULT NULL               -- 削除日時（論理削除）
);

ALTER TABLE demo
ADD COLUMN prefecture_id INT NOT NULL;
AFTER demo_place;

ALTER TABLE demo
ADD CONSTRAINT fk_demo_prefecture
FOREIGN KEY (prefecture_id) REFERENCES prefecture(id);

CREATE TABLE location_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    demo_id INT NOT NULL,
    timestamp DATETIME NOT NULL,
    latitude DECIMAL(12,9) NOT NULL,
    longitude DECIMAL(12,9) NOT NULL,
    is_within_radius BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
