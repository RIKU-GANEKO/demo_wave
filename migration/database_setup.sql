-- ========================================
-- DemoWave Database Setup (PostgreSQL)
-- ========================================
-- このファイルは新規環境でDemoWaveのデータベースをセットアップするためのSQLです
-- 機密情報は含まれていません

-- ========================================
-- データベース作成
-- ========================================
-- CREATE DATABASE demo_wave;
-- \c demo_wave;

-- ========================================
-- テーブル作成
-- ========================================

-- accountテーブルの作成
DROP TABLE IF EXISTS account CASCADE;
CREATE TABLE account (
    id SERIAL PRIMARY KEY,
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
    created_at TIMESTAMP DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- usersテーブルの作成
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    firebase_uid VARCHAR(128) NOT NULL,
    account_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    profile_image_path VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    status CHAR(1) NOT NULL,
    last_login TIMESTAMP DEFAULT NULL,
    created_at TIMESTAMP DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- rolesテーブルの作成
DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role VARCHAR(45) NOT NULL
);

-- user_roleテーブルの作成
DROP TABLE IF EXISTS user_role CASCADE;
CREATE TABLE user_role (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- prefectureテーブルの作成
DROP TABLE IF EXISTS prefecture CASCADE;
CREATE TABLE prefecture (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- categoryテーブルの作成
DROP TABLE IF EXISTS category CASCADE;
CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- demoテーブルの作成
DROP TABLE IF EXISTS demo CASCADE;
CREATE TABLE demo (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category_id INT NOT NULL,
    demo_date TIMESTAMP NOT NULL,
    demo_place VARCHAR(100) NOT NULL,
    prefecture_id INT NOT NULL,
    demo_address VARCHAR(255) NOT NULL,
    demo_address_latitude DECIMAL(12,9),
    demo_address_longitude DECIMAL(12,9),
    organizer_user_id INT NOT NULL,
    announcement_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (prefecture_id) REFERENCES prefecture(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- commentテーブルの作成
DROP TABLE IF EXISTS comment CASCADE;
CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    demo_id INT NOT NULL,
    content TEXT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (demo_id) REFERENCES demo(id)
);

-- participantテーブルの作成
DROP TABLE IF EXISTS participant CASCADE;
CREATE TABLE participant (
    id SERIAL PRIMARY KEY,
    demo_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- paymentテーブルの作成
DROP TABLE IF EXISTS payment CASCADE;
CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    demo_id INT NOT NULL,
    donate_user_id INT NOT NULL,
    donate_amount DECIMAL NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- location_logsテーブルの作成
DROP TABLE IF EXISTS location_logs CASCADE;
CREATE TABLE location_logs (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    demo_id INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    latitude DECIMAL(12,9) NOT NULL,
    longitude DECIMAL(12,9) NOT NULL,
    is_within_radius BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- favorite_demoテーブルの作成
DROP TABLE IF EXISTS favorite_demo CASCADE;
CREATE TABLE favorite_demo (
    id SERIAL PRIMARY KEY,
    demo_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- ========================================
-- インデックス作成（パフォーマンス最適化）
-- ========================================
CREATE INDEX idx_users_firebase_uid ON users(firebase_uid);
CREATE INDEX idx_demo_date ON demo(demo_date);
CREATE INDEX idx_demo_category ON demo(category_id);
CREATE INDEX idx_payment_demo ON payment(demo_id);
CREATE INDEX idx_comment_demo ON comment(demo_id);

-- ========================================
-- セットアップ完了
-- ========================================
