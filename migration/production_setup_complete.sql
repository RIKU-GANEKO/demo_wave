-- ========================================
-- DemoWave Production Database Setup
-- ========================================
-- このファイルは本番環境のSupabaseで実行するSQLです
-- 開発環境から自動生成されました

-- ========================================
-- STEP 1: TRIGGERで使用する関数を作成
-- ========================================

CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;

-- ========================================
-- STEP 2: テーブルとシーケンスを作成
-- ========================================

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';
SET default_table_access_method = heap;

-- category
CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    image_url character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone,
    ja_name character varying(255) NOT NULL
);

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;

-- comment
CREATE TABLE public.comment (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    content text NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.comment IS 'Comments on demonstrations';

CREATE SEQUENCE public.comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.comment_id_seq OWNED BY public.comment.id;

-- demo
CREATE TABLE public.demo (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    content text NOT NULL,
    category_id integer NOT NULL,
    demo_start_date timestamp without time zone NOT NULL,
    demo_end_date timestamp without time zone NOT NULL,
    demo_place character varying(100) NOT NULL,
    prefecture_id integer NOT NULL,
    demo_address_latitude numeric(12,9),
    demo_address_longitude numeric(12,9),
    organizer_user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone,
    activity_report_url character varying(500)
);

COMMENT ON TABLE public.demo IS 'Demonstration events';
COMMENT ON COLUMN public.demo.activity_report_url IS '活動報告URL（TwitterスレッドなどのURL）';

CREATE SEQUENCE public.demo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.demo_id_seq OWNED BY public.demo.id;

-- favorite_demo
CREATE TABLE public.favorite_demo (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.favorite_demo IS 'User favorites';

CREATE SEQUENCE public.favorite_demo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.favorite_demo_id_seq OWNED BY public.favorite_demo.id;

-- gift_transfer_details
CREATE TABLE public.gift_transfer_details (
    id integer NOT NULL,
    gift_transfer_id integer NOT NULL,
    user_id uuid NOT NULL,
    demo_id integer NOT NULL,
    amount numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.gift_transfer_details IS 'ギフト送金明細（デモごとの受取金額詳細）';
COMMENT ON COLUMN public.gift_transfer_details.gift_transfer_id IS '親の送金履歴ID（gift_transfers参照）';
COMMENT ON COLUMN public.gift_transfer_details.user_id IS '受取ユーザーID';
COMMENT ON COLUMN public.gift_transfer_details.demo_id IS '対象デモID';
COMMENT ON COLUMN public.gift_transfer_details.amount IS 'このデモから受け取った金額（円）';
COMMENT ON COLUMN public.gift_transfer_details.created_at IS '登録日時';
COMMENT ON COLUMN public.gift_transfer_details.deleted_at IS '論理削除日時';

CREATE SEQUENCE public.gift_transfer_details_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.gift_transfer_details_id_seq OWNED BY public.gift_transfer_details.id;

-- gift_transfers
CREATE TABLE public.gift_transfers (
    id integer NOT NULL,
    user_id uuid NOT NULL,
    transfer_month date NOT NULL,
    total_amount numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by uuid,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.gift_transfers IS 'ギフトカード送金履歴';
COMMENT ON COLUMN public.gift_transfers.user_id IS '受取ユーザーID';
COMMENT ON COLUMN public.gift_transfers.transfer_month IS '対象月（月初日を記録）';
COMMENT ON COLUMN public.gift_transfers.total_amount IS '送金額（円）';
COMMENT ON COLUMN public.gift_transfers.created_by IS 'CSV出力を実行した管理者のユーザーID';

CREATE SEQUENCE public.gift_transfers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.gift_transfers_id_seq OWNED BY public.gift_transfers.id;

-- location_logs
CREATE TABLE public.location_logs (
    id integer NOT NULL,
    user_id uuid NOT NULL,
    demo_id integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    latitude numeric(12,9) NOT NULL,
    longitude numeric(12,9) NOT NULL,
    is_within_radius boolean NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE public.location_logs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.location_logs_id_seq OWNED BY public.location_logs.id;

-- participant
CREATE TABLE public.participant (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.participant IS 'Users participating in demonstrations';

CREATE SEQUENCE public.participant_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.participant_id_seq OWNED BY public.participant.id;

-- payment
CREATE TABLE public.payment (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    donate_user_id uuid NOT NULL,
    donate_amount numeric NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.payment IS 'Donations to demonstrations';

CREATE SEQUENCE public.payment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.payment_id_seq OWNED BY public.payment.id;

-- prefecture
CREATE TABLE public.prefecture (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

CREATE SEQUENCE public.prefecture_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.prefecture_id_seq OWNED BY public.prefecture.id;

-- roles
CREATE TABLE public.roles (
    id integer NOT NULL,
    role character varying(45) NOT NULL
);

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;

-- user_role
CREATE TABLE public.user_role (
    id integer NOT NULL,
    user_id uuid NOT NULL,
    role_id integer NOT NULL,
    deleted_at timestamp without time zone
);

CREATE SEQUENCE public.user_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.user_role_id_seq OWNED BY public.user_role.id;

-- users
CREATE TABLE public.users (
    id uuid NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(255) NOT NULL,
    profile_image_path character varying(500),
    status boolean DEFAULT true NOT NULL,
    last_login timestamp without time zone,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);

COMMENT ON TABLE public.users IS 'User profile information (references auth.users)';

-- ========================================
-- STEP 3: デフォルト値の設定
-- ========================================

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);
ALTER TABLE ONLY public.comment ALTER COLUMN id SET DEFAULT nextval('public.comment_id_seq'::regclass);
ALTER TABLE ONLY public.demo ALTER COLUMN id SET DEFAULT nextval('public.demo_id_seq'::regclass);
ALTER TABLE ONLY public.favorite_demo ALTER COLUMN id SET DEFAULT nextval('public.favorite_demo_id_seq'::regclass);
ALTER TABLE ONLY public.gift_transfer_details ALTER COLUMN id SET DEFAULT nextval('public.gift_transfer_details_id_seq'::regclass);
ALTER TABLE ONLY public.gift_transfers ALTER COLUMN id SET DEFAULT nextval('public.gift_transfers_id_seq'::regclass);
ALTER TABLE ONLY public.location_logs ALTER COLUMN id SET DEFAULT nextval('public.location_logs_id_seq'::regclass);
ALTER TABLE ONLY public.participant ALTER COLUMN id SET DEFAULT nextval('public.participant_id_seq'::regclass);
ALTER TABLE ONLY public.payment ALTER COLUMN id SET DEFAULT nextval('public.payment_id_seq'::regclass);
ALTER TABLE ONLY public.prefecture ALTER COLUMN id SET DEFAULT nextval('public.prefecture_id_seq'::regclass);
ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
ALTER TABLE ONLY public.user_role ALTER COLUMN id SET DEFAULT nextval('public.user_role_id_seq'::regclass);

-- ========================================
-- STEP 4: PRIMARY KEY制約の追加
-- ========================================

ALTER TABLE ONLY public.category ADD CONSTRAINT category_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.comment ADD CONSTRAINT comment_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.demo ADD CONSTRAINT demo_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.favorite_demo ADD CONSTRAINT favorite_demo_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.gift_transfer_details ADD CONSTRAINT gift_transfer_details_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.gift_transfers ADD CONSTRAINT gift_transfers_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.location_logs ADD CONSTRAINT location_logs_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.participant ADD CONSTRAINT participant_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.payment ADD CONSTRAINT payment_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.prefecture ADD CONSTRAINT prefecture_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.roles ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.user_role ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);

-- ========================================
-- STEP 5: UNIQUE制約の追加
-- ========================================

ALTER TABLE ONLY public.gift_transfers ADD CONSTRAINT unique_user_month UNIQUE (user_id, transfer_month);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_email_key UNIQUE (email);

-- ========================================
-- STEP 6: インデックスの作成
-- ========================================

CREATE INDEX idx_comment_demo ON public.comment USING btree (demo_id);
CREATE INDEX idx_comment_user ON public.comment USING btree (user_id);
CREATE INDEX idx_demo_category ON public.demo USING btree (category_id);
CREATE INDEX idx_demo_organizer ON public.demo USING btree (organizer_user_id);
CREATE INDEX idx_demo_prefecture ON public.demo USING btree (prefecture_id);
CREATE INDEX idx_favorite_demo ON public.favorite_demo USING btree (demo_id);
CREATE INDEX idx_favorite_user ON public.favorite_demo USING btree (user_id);
CREATE INDEX idx_gift_transfer_details_demo ON public.gift_transfer_details USING btree (demo_id);
CREATE INDEX idx_gift_transfer_details_gift_transfer ON public.gift_transfer_details USING btree (gift_transfer_id);
CREATE INDEX idx_gift_transfer_details_user ON public.gift_transfer_details USING btree (user_id);
CREATE INDEX idx_gift_transfer_details_user_created ON public.gift_transfer_details USING btree (user_id, created_at);
CREATE INDEX idx_gift_transfers_created_at ON public.gift_transfers USING btree (created_at);
CREATE INDEX idx_gift_transfers_user_month ON public.gift_transfers USING btree (user_id, transfer_month);
CREATE INDEX idx_participant_demo ON public.participant USING btree (demo_id);
CREATE INDEX idx_participant_user ON public.participant USING btree (user_id);
CREATE INDEX idx_payment_demo ON public.payment USING btree (demo_id);
CREATE INDEX idx_payment_user ON public.payment USING btree (donate_user_id);
CREATE INDEX idx_users_email ON public.users USING btree (email);

-- ========================================
-- STEP 7: TRIGGERの作成
-- ========================================

CREATE TRIGGER update_category_updated_at BEFORE UPDATE ON public.category FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_comment_updated_at BEFORE UPDATE ON public.comment FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_demo_updated_at BEFORE UPDATE ON public.demo FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_favorite_demo_updated_at BEFORE UPDATE ON public.favorite_demo FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_participant_updated_at BEFORE UPDATE ON public.participant FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_payment_updated_at BEFORE UPDATE ON public.payment FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_prefecture_updated_at BEFORE UPDATE ON public.prefecture FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();

-- ========================================
-- STEP 8: 外部キー制約の追加
-- ========================================

ALTER TABLE ONLY public.comment ADD CONSTRAINT comment_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.comment ADD CONSTRAINT comment_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.demo ADD CONSTRAINT demo_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(id);
ALTER TABLE ONLY public.demo ADD CONSTRAINT demo_organizer_user_id_fkey FOREIGN KEY (organizer_user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.demo ADD CONSTRAINT demo_prefecture_id_fkey FOREIGN KEY (prefecture_id) REFERENCES public.prefecture(id);
ALTER TABLE ONLY public.favorite_demo ADD CONSTRAINT favorite_demo_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.favorite_demo ADD CONSTRAINT favorite_demo_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.gift_transfer_details ADD CONSTRAINT gift_transfer_details_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id);
ALTER TABLE ONLY public.gift_transfer_details ADD CONSTRAINT gift_transfer_details_gift_transfer_id_fkey FOREIGN KEY (gift_transfer_id) REFERENCES public.gift_transfers(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.gift_transfer_details ADD CONSTRAINT gift_transfer_details_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
ALTER TABLE ONLY public.gift_transfers ADD CONSTRAINT gift_transfers_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);
ALTER TABLE ONLY public.gift_transfers ADD CONSTRAINT gift_transfers_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
ALTER TABLE ONLY public.location_logs ADD CONSTRAINT location_logs_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.location_logs ADD CONSTRAINT location_logs_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.participant ADD CONSTRAINT participant_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.participant ADD CONSTRAINT participant_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.payment ADD CONSTRAINT payment_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.payment ADD CONSTRAINT payment_donate_user_id_fkey FOREIGN KEY (donate_user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.user_role ADD CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.user_role ADD CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;

-- ⚠️ 重要: この外部キー制約はSupabase Authが有効になっている場合のみ機能します
ALTER TABLE ONLY public.users ADD CONSTRAINT users_id_fkey FOREIGN KEY (id) REFERENCES auth.users(id) ON DELETE CASCADE;

-- ========================================
-- STEP 9: 初期データの投入
-- ========================================

-- Roles
INSERT INTO public.roles (id, role) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- Category
INSERT INTO public.category (id, name, image_url, ja_name) VALUES
(1, 'environment', '/images/category/environment.png', '環境'),
(2, 'anti_war', '/images/category/anti_war.png', '反戦'),
(3, 'animal_rights', '/images/category/animal_rights.png', '動物の権利'),
(4, 'politics', '/images/category/politics.png', '政治'),
(5, 'human_rights', '/images/category/human_rights.png', '人権'),
(6, 'social_welfare', '/images/category/social_welfare.png', '社会福祉');

-- Prefecture (全47都道府県)
INSERT INTO public.prefecture (id, name) VALUES
(1, '北海道'), (2, '青森県'), (3, '岩手県'), (4, '宮城県'), (5, '秋田県'),
(6, '山形県'), (7, '福島県'), (8, '茨城県'), (9, '栃木県'), (10, '群馬県'),
(11, '埼玉県'), (12, '千葉県'), (13, '東京都'), (14, '神奈川県'), (15, '新潟県'),
(16, '富山県'), (17, '石川県'), (18, '福井県'), (19, '山梨県'), (20, '長野県'),
(21, '岐阜県'), (22, '静岡県'), (23, '愛知県'), (24, '三重県'), (25, '滋賀県'),
(26, '京都府'), (27, '大阪府'), (28, '兵庫県'), (29, '奈良県'), (30, '和歌山県'),
(31, '鳥取県'), (32, '島根県'), (33, '岡山県'), (34, '広島県'), (35, '山口県'),
(36, '徳島県'), (37, '香川県'), (38, '愛媛県'), (39, '高知県'), (40, '福岡県'),
(41, '佐賀県'), (42, '長崎県'), (43, '熊本県'), (44, '大分県'), (45, '宮崎県'),
(46, '鹿児島県'), (47, '沖縄県');

-- シーケンスの値を更新
SELECT pg_catalog.setval('public.category_id_seq', 6, true);
SELECT pg_catalog.setval('public.prefecture_id_seq', 47, true);
SELECT pg_catalog.setval('public.roles_id_seq', 2, true);

-- ========================================
-- セットアップ完了
-- ========================================
