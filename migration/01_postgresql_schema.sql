-- ============================================
-- Demo Wave PostgreSQL Schema (Supabase)
-- MySQL to PostgreSQL Migration
-- ============================================

-- Note: auth.users is managed by Supabase Auth
-- We only create public schema tables

-- ============================================
-- 1. USERS TABLE (Profile)
-- ============================================
CREATE TABLE IF NOT EXISTS public.users (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    account_id INTEGER,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    profile_image_path VARCHAR(500),
    status CHAR(1) NOT NULL DEFAULT '1',
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 2. ACCOUNT TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.account (
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
    account_type INTEGER NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    account_holder VARCHAR(255),
    ad_unit_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 3. ROLES TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.roles (
    id SERIAL PRIMARY KEY,
    role VARCHAR(45) NOT NULL
);

-- ============================================
-- 4. USER_ROLE TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.user_role (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES public.roles(id) ON DELETE CASCADE,
    deleted_at TIMESTAMP
);

-- ============================================
-- 5. CATEGORY TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 6. PREFECTURE TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.prefecture (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 7. DEMO TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.demo (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category_id INTEGER NOT NULL REFERENCES public.category(id),
    demo_start_date TIMESTAMP NOT NULL,
    demo_end_date TIMESTAMP NOT NULL,
    demo_place VARCHAR(100) NOT NULL,
    prefecture_id INTEGER NOT NULL REFERENCES public.prefecture(id),
    demo_address_latitude DECIMAL(12,9),
    demo_address_longitude DECIMAL(12,9),
    organizer_user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 8. COMMENT TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.comment (
    id SERIAL PRIMARY KEY,
    demo_id INTEGER NOT NULL REFERENCES public.demo(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 9. PARTICIPANT TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.participant (
    id SERIAL PRIMARY KEY,
    demo_id INTEGER NOT NULL REFERENCES public.demo(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 10. PAYMENT TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.payment (
    id SERIAL PRIMARY KEY,
    demo_id INTEGER NOT NULL REFERENCES public.demo(id) ON DELETE CASCADE,
    donate_user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    donate_amount DECIMAL NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- 11. LOCATION_LOGS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.location_logs (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    demo_id INTEGER NOT NULL REFERENCES public.demo(id) ON DELETE CASCADE,
    timestamp TIMESTAMP NOT NULL,
    latitude DECIMAL(12,9) NOT NULL,
    longitude DECIMAL(12,9) NOT NULL,
    is_within_radius BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 12. FAVORITE_DEMO TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS public.favorite_demo (
    id SERIAL PRIMARY KEY,
    demo_id INTEGER NOT NULL REFERENCES public.demo(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES public.users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- ============================================
-- INDEXES
-- ============================================
CREATE INDEX IF NOT EXISTS idx_users_email ON public.users(email);
CREATE INDEX IF NOT EXISTS idx_demo_organizer ON public.demo(organizer_user_id);
CREATE INDEX IF NOT EXISTS idx_demo_category ON public.demo(category_id);
CREATE INDEX IF NOT EXISTS idx_demo_prefecture ON public.demo(prefecture_id);
CREATE INDEX IF NOT EXISTS idx_comment_demo ON public.comment(demo_id);
CREATE INDEX IF NOT EXISTS idx_comment_user ON public.comment(user_id);
CREATE INDEX IF NOT EXISTS idx_participant_demo ON public.participant(demo_id);
CREATE INDEX IF NOT EXISTS idx_participant_user ON public.participant(user_id);
CREATE INDEX IF NOT EXISTS idx_payment_demo ON public.payment(demo_id);
CREATE INDEX IF NOT EXISTS idx_payment_user ON public.payment(donate_user_id);
CREATE INDEX IF NOT EXISTS idx_favorite_demo ON public.favorite_demo(demo_id);
CREATE INDEX IF NOT EXISTS idx_favorite_user ON public.favorite_demo(user_id);

-- ============================================
-- FUNCTIONS & TRIGGERS
-- ============================================

-- Auto-update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Apply trigger to all tables with updated_at
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON public.users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_account_updated_at BEFORE UPDATE ON public.account
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_category_updated_at BEFORE UPDATE ON public.category
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_prefecture_updated_at BEFORE UPDATE ON public.prefecture
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_demo_updated_at BEFORE UPDATE ON public.demo
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_comment_updated_at BEFORE UPDATE ON public.comment
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_participant_updated_at BEFORE UPDATE ON public.participant
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_payment_updated_at BEFORE UPDATE ON public.payment
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_favorite_demo_updated_at BEFORE UPDATE ON public.favorite_demo
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ============================================
-- COMMENTS
-- ============================================
COMMENT ON TABLE public.users IS 'User profile information (references auth.users)';
COMMENT ON TABLE public.demo IS 'Demonstration events';
COMMENT ON TABLE public.comment IS 'Comments on demonstrations';
COMMENT ON TABLE public.participant IS 'Users participating in demonstrations';
COMMENT ON TABLE public.payment IS 'Donations to demonstrations';
COMMENT ON TABLE public.favorite_demo IS 'User favorites';
