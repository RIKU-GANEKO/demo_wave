--
-- PostgreSQL database dump
--

\restrict BmI5MS28jqYsAzY6CDapQXfA9CTdoc1qb69FgBZEFcWx8xcCKbUOYWzYUo8tD5n

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

--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.category (id, name, image_url, created_at, updated_at, deleted_at, ja_name) FROM stdin;
1	environment	/images/category/environment.png	2025-05-29 12:41:05	2025-11-13 05:47:48.023636	\N	環境
2	anti_war	/images/category/anti_war.png	2025-05-29 12:41:05	2025-11-13 05:47:48.023636	\N	反戦
3	animal_rights	/images/category/animal_rights.png	2025-05-29 12:41:05	2025-11-13 05:47:48.023636	\N	動物の権利
4	politics	/images/category/politics.png	2025-05-29 12:41:05	2025-11-13 05:47:48.023636	\N	政治
5	human_rights	/images/category/human_rights.png	2025-05-29 12:41:05	2025-11-13 05:47:48.023636	\N	人権
6	social_welfare	/images/category/social_welfare.png	2025-05-29 12:41:05	2025-11-13 05:47:48.023636	\N	社会福祉
\.


--
-- Data for Name: prefecture; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.prefecture (id, name, created_at, updated_at, deleted_at) FROM stdin;
1	北海道	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
2	青森県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
3	岩手県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
4	宮城県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
5	秋田県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
6	山形県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
7	福島県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
8	茨城県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
9	栃木県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
10	群馬県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
11	埼玉県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
12	千葉県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
13	東京都	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
14	神奈川県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
15	新潟県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
16	富山県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
17	石川県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
18	福井県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
19	山梨県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
20	長野県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
21	岐阜県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
22	静岡県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
23	愛知県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
24	三重県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
25	滋賀県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
26	京都府	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
27	大阪府	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
28	兵庫県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
29	奈良県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
30	和歌山県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
31	鳥取県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
32	島根県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
33	岡山県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
34	広島県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
35	山口県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
36	徳島県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
37	香川県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
38	愛媛県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
39	高知県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
40	福岡県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
41	佐賀県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
42	長崎県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
43	熊本県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
44	大分県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
45	宮崎県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
46	鹿児島県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
47	沖縄県	2025-06-16 21:31:59	2025-06-16 21:31:59	\N
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.roles (id, role) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
\.


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.category_id_seq', 1, false);


--
-- Name: prefecture_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.prefecture_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.roles_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--

\unrestrict BmI5MS28jqYsAzY6CDapQXfA9CTdoc1qb69FgBZEFcWx8xcCKbUOYWzYUo8tD5n

