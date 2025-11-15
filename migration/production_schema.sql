--
-- PostgreSQL database dump
--

\restrict h6VDXKXz3xuIpvmMIq5fHE1tBMoVS5XU0YDxGwF4aXTUArM2caRi36ossxgvoyl

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.7 (Homebrew)

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

--
-- Name: category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    image_url character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone,
    ja_name character varying(255) NOT NULL
);


--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- Name: comment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.comment (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    content text NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);


--
-- Name: TABLE comment; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.comment IS 'Comments on demonstrations';


--
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.comment_id_seq OWNED BY public.comment.id;


--
-- Name: demo; Type: TABLE; Schema: public; Owner: -
--

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


--
-- Name: TABLE demo; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.demo IS 'Demonstration events';


--
-- Name: COLUMN demo.activity_report_url; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.demo.activity_report_url IS '活動報告URL（TwitterスレッドなどのURL）';


--
-- Name: demo_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.demo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: demo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.demo_id_seq OWNED BY public.demo.id;


--
-- Name: favorite_demo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.favorite_demo (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);


--
-- Name: TABLE favorite_demo; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.favorite_demo IS 'User favorites';


--
-- Name: favorite_demo_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.favorite_demo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: favorite_demo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.favorite_demo_id_seq OWNED BY public.favorite_demo.id;


--
-- Name: gift_transfer_details; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.gift_transfer_details (
    id integer NOT NULL,
    gift_transfer_id integer NOT NULL,
    user_id uuid NOT NULL,
    demo_id integer NOT NULL,
    amount numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);


--
-- Name: TABLE gift_transfer_details; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.gift_transfer_details IS 'ギフト送金明細（デモごとの受取金額詳細）';


--
-- Name: COLUMN gift_transfer_details.gift_transfer_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfer_details.gift_transfer_id IS '親の送金履歴ID（gift_transfers参照）';


--
-- Name: COLUMN gift_transfer_details.user_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfer_details.user_id IS '受取ユーザーID';


--
-- Name: COLUMN gift_transfer_details.demo_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfer_details.demo_id IS '対象デモID';


--
-- Name: COLUMN gift_transfer_details.amount; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfer_details.amount IS 'このデモから受け取った金額（円）';


--
-- Name: COLUMN gift_transfer_details.created_at; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfer_details.created_at IS '登録日時';


--
-- Name: COLUMN gift_transfer_details.deleted_at; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfer_details.deleted_at IS '論理削除日時';


--
-- Name: gift_transfer_details_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.gift_transfer_details_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: gift_transfer_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.gift_transfer_details_id_seq OWNED BY public.gift_transfer_details.id;


--
-- Name: gift_transfers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.gift_transfers (
    id integer NOT NULL,
    user_id uuid NOT NULL,
    transfer_month date NOT NULL,
    total_amount numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by uuid,
    deleted_at timestamp without time zone
);


--
-- Name: TABLE gift_transfers; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.gift_transfers IS 'ギフトカード送金履歴';


--
-- Name: COLUMN gift_transfers.user_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfers.user_id IS '受取ユーザーID';


--
-- Name: COLUMN gift_transfers.transfer_month; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfers.transfer_month IS '対象月（月初日を記録）';


--
-- Name: COLUMN gift_transfers.total_amount; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfers.total_amount IS '送金額（円）';


--
-- Name: COLUMN gift_transfers.created_by; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.gift_transfers.created_by IS 'CSV出力を実行した管理者のユーザーID';


--
-- Name: gift_transfers_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.gift_transfers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: gift_transfers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.gift_transfers_id_seq OWNED BY public.gift_transfers.id;


--
-- Name: location_logs; Type: TABLE; Schema: public; Owner: -
--

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


--
-- Name: location_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.location_logs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: location_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.location_logs_id_seq OWNED BY public.location_logs.id;


--
-- Name: participant; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.participant (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    user_id uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);


--
-- Name: TABLE participant; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.participant IS 'Users participating in demonstrations';


--
-- Name: participant_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.participant_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: participant_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.participant_id_seq OWNED BY public.participant.id;


--
-- Name: payment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.payment (
    id integer NOT NULL,
    demo_id integer NOT NULL,
    donate_user_id uuid NOT NULL,
    donate_amount numeric NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);


--
-- Name: TABLE payment; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.payment IS 'Donations to demonstrations';


--
-- Name: payment_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.payment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: payment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.payment_id_seq OWNED BY public.payment.id;


--
-- Name: prefecture; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.prefecture (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp without time zone
);


--
-- Name: prefecture_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.prefecture_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: prefecture_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.prefecture_id_seq OWNED BY public.prefecture.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    role character varying(45) NOT NULL
);


--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: user_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_role (
    id integer NOT NULL,
    user_id uuid NOT NULL,
    role_id integer NOT NULL,
    deleted_at timestamp without time zone
);


--
-- Name: user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.user_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.user_role_id_seq OWNED BY public.user_role.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

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


--
-- Name: TABLE users; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.users IS 'User profile information (references auth.users)';


--
-- Name: category id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment ALTER COLUMN id SET DEFAULT nextval('public.comment_id_seq'::regclass);


--
-- Name: demo id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demo ALTER COLUMN id SET DEFAULT nextval('public.demo_id_seq'::regclass);


--
-- Name: favorite_demo id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite_demo ALTER COLUMN id SET DEFAULT nextval('public.favorite_demo_id_seq'::regclass);


--
-- Name: gift_transfer_details id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfer_details ALTER COLUMN id SET DEFAULT nextval('public.gift_transfer_details_id_seq'::regclass);


--
-- Name: gift_transfers id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfers ALTER COLUMN id SET DEFAULT nextval('public.gift_transfers_id_seq'::regclass);


--
-- Name: location_logs id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.location_logs ALTER COLUMN id SET DEFAULT nextval('public.location_logs_id_seq'::regclass);


--
-- Name: participant id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.participant ALTER COLUMN id SET DEFAULT nextval('public.participant_id_seq'::regclass);


--
-- Name: payment id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment ALTER COLUMN id SET DEFAULT nextval('public.payment_id_seq'::regclass);


--
-- Name: prefecture id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prefecture ALTER COLUMN id SET DEFAULT nextval('public.prefecture_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: user_role id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role ALTER COLUMN id SET DEFAULT nextval('public.user_role_id_seq'::regclass);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: demo demo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demo
    ADD CONSTRAINT demo_pkey PRIMARY KEY (id);


--
-- Name: favorite_demo favorite_demo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite_demo
    ADD CONSTRAINT favorite_demo_pkey PRIMARY KEY (id);


--
-- Name: gift_transfer_details gift_transfer_details_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfer_details
    ADD CONSTRAINT gift_transfer_details_pkey PRIMARY KEY (id);


--
-- Name: gift_transfers gift_transfers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfers
    ADD CONSTRAINT gift_transfers_pkey PRIMARY KEY (id);


--
-- Name: location_logs location_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.location_logs
    ADD CONSTRAINT location_logs_pkey PRIMARY KEY (id);


--
-- Name: participant participant_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.participant
    ADD CONSTRAINT participant_pkey PRIMARY KEY (id);


--
-- Name: payment payment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (id);


--
-- Name: prefecture prefecture_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.prefecture
    ADD CONSTRAINT prefecture_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: gift_transfers unique_user_month; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfers
    ADD CONSTRAINT unique_user_month UNIQUE (user_id, transfer_month);


--
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: idx_comment_demo; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_comment_demo ON public.comment USING btree (demo_id);


--
-- Name: idx_comment_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_comment_user ON public.comment USING btree (user_id);


--
-- Name: idx_demo_category; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_demo_category ON public.demo USING btree (category_id);


--
-- Name: idx_demo_organizer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_demo_organizer ON public.demo USING btree (organizer_user_id);


--
-- Name: idx_demo_prefecture; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_demo_prefecture ON public.demo USING btree (prefecture_id);


--
-- Name: idx_favorite_demo; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_favorite_demo ON public.favorite_demo USING btree (demo_id);


--
-- Name: idx_favorite_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_favorite_user ON public.favorite_demo USING btree (user_id);


--
-- Name: idx_gift_transfer_details_demo; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_gift_transfer_details_demo ON public.gift_transfer_details USING btree (demo_id);


--
-- Name: idx_gift_transfer_details_gift_transfer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_gift_transfer_details_gift_transfer ON public.gift_transfer_details USING btree (gift_transfer_id);


--
-- Name: idx_gift_transfer_details_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_gift_transfer_details_user ON public.gift_transfer_details USING btree (user_id);


--
-- Name: idx_gift_transfer_details_user_created; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_gift_transfer_details_user_created ON public.gift_transfer_details USING btree (user_id, created_at);


--
-- Name: idx_gift_transfers_created_at; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_gift_transfers_created_at ON public.gift_transfers USING btree (created_at);


--
-- Name: idx_gift_transfers_user_month; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_gift_transfers_user_month ON public.gift_transfers USING btree (user_id, transfer_month);


--
-- Name: idx_participant_demo; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_participant_demo ON public.participant USING btree (demo_id);


--
-- Name: idx_participant_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_participant_user ON public.participant USING btree (user_id);


--
-- Name: idx_payment_demo; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_payment_demo ON public.payment USING btree (demo_id);


--
-- Name: idx_payment_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_payment_user ON public.payment USING btree (donate_user_id);


--
-- Name: idx_users_email; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_users_email ON public.users USING btree (email);


--
-- Name: category update_category_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_category_updated_at BEFORE UPDATE ON public.category FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: comment update_comment_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_comment_updated_at BEFORE UPDATE ON public.comment FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: demo update_demo_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_demo_updated_at BEFORE UPDATE ON public.demo FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: favorite_demo update_favorite_demo_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_favorite_demo_updated_at BEFORE UPDATE ON public.favorite_demo FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: participant update_participant_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_participant_updated_at BEFORE UPDATE ON public.participant FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: payment update_payment_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_payment_updated_at BEFORE UPDATE ON public.payment FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: prefecture update_prefecture_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_prefecture_updated_at BEFORE UPDATE ON public.prefecture FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: users update_users_updated_at; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


--
-- Name: comment comment_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;


--
-- Name: comment comment_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: demo demo_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demo
    ADD CONSTRAINT demo_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: demo demo_organizer_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demo
    ADD CONSTRAINT demo_organizer_user_id_fkey FOREIGN KEY (organizer_user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: demo demo_prefecture_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.demo
    ADD CONSTRAINT demo_prefecture_id_fkey FOREIGN KEY (prefecture_id) REFERENCES public.prefecture(id);


--
-- Name: favorite_demo favorite_demo_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite_demo
    ADD CONSTRAINT favorite_demo_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;


--
-- Name: favorite_demo favorite_demo_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite_demo
    ADD CONSTRAINT favorite_demo_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: gift_transfer_details gift_transfer_details_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfer_details
    ADD CONSTRAINT gift_transfer_details_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id);


--
-- Name: gift_transfer_details gift_transfer_details_gift_transfer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfer_details
    ADD CONSTRAINT gift_transfer_details_gift_transfer_id_fkey FOREIGN KEY (gift_transfer_id) REFERENCES public.gift_transfers(id) ON DELETE CASCADE;


--
-- Name: gift_transfer_details gift_transfer_details_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfer_details
    ADD CONSTRAINT gift_transfer_details_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: gift_transfers gift_transfers_created_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfers
    ADD CONSTRAINT gift_transfers_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: gift_transfers gift_transfers_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.gift_transfers
    ADD CONSTRAINT gift_transfers_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: location_logs location_logs_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.location_logs
    ADD CONSTRAINT location_logs_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;


--
-- Name: location_logs location_logs_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.location_logs
    ADD CONSTRAINT location_logs_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: participant participant_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.participant
    ADD CONSTRAINT participant_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;


--
-- Name: participant participant_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.participant
    ADD CONSTRAINT participant_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: payment payment_demo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_demo_id_fkey FOREIGN KEY (demo_id) REFERENCES public.demo(id) ON DELETE CASCADE;


--
-- Name: payment payment_donate_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_donate_user_id_fkey FOREIGN KEY (donate_user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: user_role user_role_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- Name: user_role user_role_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: users users_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_id_fkey FOREIGN KEY (id) REFERENCES auth.users(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

\unrestrict h6VDXKXz3xuIpvmMIq5fHE1tBMoVS5XU0YDxGwF4aXTUArM2caRi36ossxgvoyl

