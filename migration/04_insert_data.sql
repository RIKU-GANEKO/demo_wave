-- ============================================
-- Data Migration: MySQL to PostgreSQL
-- User ID Mapping (Old INT -> New UUID)
-- ============================================

-- User ID Mapping:
-- 1 -> 37eaf714-043a-4f2e-a0e4-5aa76092e0d0
-- 2 -> 45b86f55-eca3-466d-9561-082616921ca3
-- 3 -> 87a89127-bea0-4122-b37a-0b785948532b
-- 4 -> 37eaf714-043a-4f2e-a0e4-5aa76092e0d0
-- 6 -> 87a89127-bea0-4122-b37a-0b785948532b
-- 8 -> 37eaf714-043a-4f2e-a0e4-5aa76092e0d0
-- 10 -> 37eaf714-043a-4f2e-a0e4-5aa76092e0d0
-- 12 -> 57620eda-417d-45f4-878d-dfa9ed7b7e3c
-- 13 -> 57620eda-417d-45f4-878d-dfa9ed7b7e3c
-- 23 -> 87a89127-bea0-4122-b37a-0b785948532b

-- Category Data
INSERT INTO public.category (id, name, image_url, created_at, updated_at, deleted_at) VALUES (1, 'environment', '/images/category/environment.png', '2025-05-29 12:41:05', '2025-05-29 16:36:39', NULL);
INSERT INTO public.category (id, name, image_url, created_at, updated_at, deleted_at) VALUES (2, 'anti_war', '/images/category/anti_war.png', '2025-05-29 12:41:05', '2025-05-29 16:36:39', NULL);
INSERT INTO public.category (id, name, image_url, created_at, updated_at, deleted_at) VALUES (3, 'animal_rights', '/images/category/animal_rights.png', '2025-05-29 12:41:05', '2025-05-29 16:36:39', NULL);
INSERT INTO public.category (id, name, image_url, created_at, updated_at, deleted_at) VALUES (4, 'politics', '/images/category/politics.png', '2025-05-29 12:41:05', '2025-05-29 16:36:39', NULL);
INSERT INTO public.category (id, name, image_url, created_at, updated_at, deleted_at) VALUES (5, 'human_rights', '/images/category/human_rights.png', '2025-05-29 12:41:05', '2025-05-29 16:36:39', NULL);
INSERT INTO public.category (id, name, image_url, created_at, updated_at, deleted_at) VALUES (6, 'social_welfare', '/images/category/social_welfare.png', '2025-05-29 12:41:05', '2025-05-29 16:49:35', NULL);

-- Prefecture Data
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (1, '北海道', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (2, '青森県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (3, '岩手県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (4, '宮城県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (5, '秋田県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (6, '山形県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (7, '福島県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (8, '茨城県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (9, '栃木県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (10, '群馬県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (11, '埼玉県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (12, '千葉県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (13, '東京都', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (14, '神奈川県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (15, '新潟県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (16, '富山県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (17, '石川県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (18, '福井県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (19, '山梨県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (20, '長野県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (21, '岐阜県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (22, '静岡県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (23, '愛知県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (24, '三重県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (25, '滋賀県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (26, '京都府', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (27, '大阪府', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (28, '兵庫県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (29, '奈良県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (30, '和歌山県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (31, '鳥取県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (32, '島根県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (33, '岡山県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (34, '広島県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (35, '山口県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (36, '徳島県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (37, '香川県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (38, '愛媛県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (39, '高知県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (40, '福岡県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (41, '佐賀県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (42, '長崎県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (43, '熊本県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (44, '大分県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (45, '宮崎県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (46, '鹿児島県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);
INSERT INTO public.prefecture (id, name, created_at, updated_at, deleted_at) VALUES (47, '沖縄県', '2025-06-16 21:31:59', '2025-06-16 21:31:59', NULL);

-- Demo Data
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            1,
            '地域清掃ボランティア',
            '地域の環境を守るため、毎週末に有志が集まり、街のごみ拾いや草刈りなどの清掃活動を行っています。',
            1,
            '2025-01-05 08:00:00',
            '2025-01-05 09:30:00',
            'shibuya',
            1,
            35.661777,
            139.704051,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-05 16:58:58',
            '2025-07-10 10:50:01',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            2,
            '災害復旧支援活動',
            'この活動は、自然災害で被災した地域において、がれきの撤去や仮設住宅の整備、避難所での生活支援などを行うもので、全国から集まったボランティアが協力して住民の生活再建を目指しています。活動の中では安全確認や衛生管理も徹底され、被災者との信頼関係の構築が最重要とされます。毎日数十名単位で現場に入り、作業を分担しながら効率的に支援を行っています。復旧作業に加えて、心のケアを目的とした交流イベントや相談窓口の運営も行っており、子どもから高齢者までが安心して過ごせる環境づくりに寄与しています。さらに、ボランティアの宿泊や食事、移動に関する支援体制も整備され、長期的な活動が可能となっています。',
            2,
            '2025-01-05 10:00:00',
            '2025-01-05 12:00:00',
            'shinjuku',
            1,
            35.689592,
            139.700413,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-05 22:18:13',
            '2025-07-10 10:50:01',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            3,
            '子ども食堂支援',
            '子どもたちに温かい食事と安心できる場所を提供する地域密着型の取り組みです。',
            3,
            '2025-01-05 13:00:00',
            '2025-01-05 14:30:00',
            'ikebukuro',
            1,
            35.728926,
            139.71038,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-05 22:18:13',
            '2025-07-10 10:50:01',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            4,
            '食料支援プロジェクト',
            '経済的な理由で食事に困っている家庭や学生を対象に、週に数回、炊き出しや食品配布を行うプロジェクトです。この活動には地元の飲食店や農家、企業も協賛しており、食品ロスの削減にも貢献しています。また、支援を受ける人々との対話を通じて、それぞれの困りごとを聞き、必要に応じて生活相談や就労支援などの制度に繋げる支援も行われています。食事の提供だけでなく、居場所づくりや地域の交流の場としての機能も果たしており、誰一人取り残さない社会の実現を目指しています。',
            4,
            '2025-01-15 09:00:00',
            '2025-01-15 11:00:00',
            '那覇',
            1,
            35.714765,
            139.796655,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-08 01:21:18',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            5,
            '平和を願う集い',
            '戦争のない世界を目指し、平和について考える機会を提供する集まりです。',
            5,
            '2025-01-10 14:00:00',
            '2025-01-10 15:30:00',
            '辺野古',
            1,
            26.2022223,
            127.7088662,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-10 19:22:59',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            6,
            '被爆者支援と証言継承',
            '広島・長崎の被爆者から直接体験談を聞く機会を設け、次世代への平和の継承を目指します。証言会の開催、ドキュメント映像の上映、語り部養成講座など、多面的な取り組みを通して、核兵器廃絶への理解を深めています。また、被爆者の高齢化が進む中で、その記憶を記録として残すデジタルアーカイブ化も進められています。イベントでは若者の参加を促進し、SNSや動画配信を活用した発信にも力を入れています。',
            6,
            '2025-01-13 08:30:00',
            '2025-01-13 10:30:00',
            '国会議事堂前',
            1,
            35.675888,
            139.744858,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-01-13 04:37:15',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            7,
            '子ども遊びサポート',
            '地域の公園で子どもたちと遊んだり、見守りを行ったりする活動です。',
            3,
            '2025-01-13 13:00:00',
            '2025-01-13 15:00:00',
            '渋谷',
            1,
            35.6590699,
            139.7007259,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-01-13 04:51:34',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            8,
            '地域学習支援教室',
            '経済的な理由で塾に通えない子どもたちに、無料で学習支援を行うボランティア活動です。対象は小学生から高校生までで、ボランティア講師が個別に対応し、学校の宿題や受験対策をサポートします。多くの支援者が週末に集まり、教科ごとにブースを設けて丁寧に教えています。学力向上だけでなく、自信を持つこと、夢を描く力を育むことを大切にしています。',
            2,
            '2025-01-18 09:00:00',
            '2025-01-18 11:00:00',
            '新潟市',
            1,
            37.9062884,
            139.1149298,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-18 15:24:38',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            9,
            '自然観察会サポート',
            '地域の自然を観察し、子どもたちに自然の大切さを伝えるイベントのサポート活動です。',
            1,
            '2025-03-02 10:00:00',
            '2025-03-02 12:00:00',
            '那覇',
            1,
            26.2123793,
            127.6806877,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-22 01:39:29',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            10,
            '環境教育イベント企画',
            '地域の小学校や中学校と連携し、森林や川の保全、生態系の学びを深めるための体験型イベントを企画・運営します。参加する子どもたちは、実際に森に入り、専門家の指導のもと昆虫や植物の観察、間伐体験、木工ワークショップなどを行います。保護者も一緒に参加することで、家庭での環境意識の醸成にもつながっています。',
            2,
            '2025-06-03 11:00:00',
            '2025-06-03 12:30:00',
            '東京都渋谷区',
            1,
            35.661777,
            139.704051,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-03 14:37:03',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            11,
            '移民支援交流会',
            '地域で暮らす外国人との交流イベントを通じ、相互理解と支援を進めます。',
            2,
            '2025-06-03 14:00:00',
            '2025-06-03 16:00:00',
            '東京都渋谷区',
            1,
            35.661777,
            139.704051,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-03 16:27:57',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            12,
            '外国人の日本語学習支援',
            '移住者や技能実習生など、日本語に不慣れな外国人に対して、会話練習や読み書きの指導を行う活動です。日本の文化や制度についても紹介し、生活の不安を解消します。ボランティア講師は日本語教育の経験がなくても参加可能で、研修制度も整っており、安心して支援に関われます。',
            4,
            '2025-06-14 08:00:00',
            '2025-06-14 09:30:00',
            '渋谷駅',
            1,
            35.6580339,
            139.7016358,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-03 16:36:26',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            13,
            '高齢者の話し相手ボランティア',
            '独居高齢者宅を訪問し、お話し相手や簡単な家事の手伝いを行う活動です。',
            6,
            '2025-06-28 09:00:00',
            '2025-06-28 11:00:00',
            '那覇市上間郵便局',
            1,
            26.2019131,
            127.7083801,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-03 16:39:12',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            14,
            '認知症サポーター育成講座',
            '認知症への理解を深め、地域で支え合う力を育てる講座を定期開催しています。受講者はオレンジリングを身に着け、街中で困っている方に声をかけたり、必要な支援につなげたりする役割を担います。講座は医療・介護の専門職が講師を務め、実例に基づく学びが得られます。',
            6,
            '2025-06-28 13:00:00',
            '2025-06-28 15:30:00',
            '那覇市上間郵便局',
            1,
            26.2019131,
            127.7083801,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-03 16:39:43',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            15,
            '若者キャリア相談会',
            '進学や就職に悩む若者に対し、専門家によるキャリアカウンセリングを実施します。',
            6,
            '2025-06-28 16:00:00',
            '2025-06-28 17:30:00',
            '那覇市上間郵便局',
            1,
            26.2019131,
            127.7083801,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-03 16:39:59',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            16,
            '不登校支援スペース運営',
            '学校に行けない子どもたちの居場所を提供するためのフリースペースを運営。学習支援のほか、工作、料理、音楽など自由な活動を通じて自己肯定感を高めます。スタッフは福祉・教育分野の経験者が中心で、子どもや保護者と信頼関係を築きながら個々の課題に寄り添った支援を行います。',
            4,
            '2025-06-28 10:00:00',
            '2025-06-28 12:00:00',
            '新宿駅前',
            1,
            35.6900241,
            139.6972237,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-04 10:18:18',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            17,
            '献血キャンペーン案内',
            '街頭での献血協力呼びかけを行い、必要な血液量の確保に貢献する活動です。',
            5,
            '2025-06-28 14:00:00',
            '2025-06-28 16:00:00',
            '新宿御苑',
            1,
            35.6851763,
            139.7100517,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-04 17:06:45',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            18,
            '医療従事者応援プロジェクト',
            'コロナ禍で奮闘する医療従事者に感謝と応援の気持ちを届けるプロジェクト。寄付による差し入れや応援メッセージの掲示、子どもたちからの絵手紙展示などを通じて、医療現場に笑顔と力を届けています。',
            1,
            '2025-06-28 08:00:00',
            '2025-06-28 10:00:00',
            '大宮駅',
            1,
            35.9064485,
            139.6238548,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-04 18:02:47',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            23,
            'ホームレス支援活動',
            '定期的な炊き出しや衣類配布、生活相談会を通じて、路上生活者の生活支援を行います。',
            5,
            '2025-06-21 08:00:00',
            '2025-06-21 10:00:00',
            '那覇メインプレイス',
            1,
            26.2252975,
            127.6948569,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-12 01:18:39',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            24,
            '生活困窮者自立支援プログラム',
            'ホームレスや生活困窮者が自立できるよう、住居支援、就労支援、心のケアを含めた包括的な支援を行っています。地域の支援団体と行政が連携し、伴走型の支援体制を構築しています。',
            1,
            '2025-06-16 18:51:30',
            '2025-06-16 18:51:30',
            '渋谷駅',
            1,
            35.6580339,
            139.7016358,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-16 20:13:37',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            25,
            '若者の地域参加促進',
            '地域課題に若者の視点を取り入れるため、ワークショップや地域活動への参加を促進します。',
            1,
            '2025-06-16 20:14:22',
            '2025-06-16 20:14:22',
            '那覇市立上間小学校',
            1,
            26.2022275,
            127.7029592,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-16 20:14:53',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            26,
            '地域自治体と連携した政策提言活動',
            '若者の声を地域行政に届けるため、自治体との意見交換会やアンケート調査を実施し、住みよい街づくりに向けた提言を行っています。学生や若手社会人が中心となって構成され、年に数回、実際に市議会へ政策案を提出しています。',
            3,
            '2025-06-28 10:00:00',
            '2025-06-28 13:00:00',
            '那覇国際高校',
            47,
            26.2313877,
            127.6935689,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-16 21:58:44',
            '2025-06-17 01:15:08',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            27,
            '国民民主党を後押ししよう！',
            '我々の生活を守る党を応援しよう。',
            4,
            '2025-06-22 10:00:00',
            '2025-06-22 14:00:00',
            '乃木坂駅',
            13,
            35.6668266,
            139.7264971,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-22 00:05:25',
            '2025-06-22 00:05:25',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            28,
            '沖縄共産党を応援しよう。',
            '沖縄の主権を守る！',
            4,
            '2025-06-22 11:30:00',
            '2025-06-22 14:30:00',
            '沖縄県庁前',
            47,
            26.2123793,
            127.6806877,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-22 00:26:28',
            '2025-06-22 00:26:28',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            29,
            'あああ',
            'あああああああああああああああああああああああああああああああああああああああああ。\\n\\nあああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ。\\nあああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ。\\n\\nあああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ。',
            1,
            '2025-06-30 13:00:00',
            '2025-06-30 16:00:00',
            '東京駅',
            13,
            35.6812996,
            139.7670658,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-30 00:00:00',
            '2025-06-30 00:00:00',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            30,
            '背景色テスト',
            '灰色になっているか',
            1,
            '2025-07-19 18:18:00',
            '2025-07-19 20:18:00',
            '東京駅',
            13,
            35.6812996,
            139.7670658,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-04 18:19:28',
            '2025-07-04 18:19:28',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            32,
            'ナルトのテスト',
            'いいいいいいい',
            1,
            '2025-07-26 01:51:00',
            '2025-07-26 12:51:00',
            '神泉駅',
            13,
            35.6572704,
            139.6936227,
            '57620eda-417d-45f4-878d-dfa9ed7b7e3c',
            '2025-07-06 01:52:06',
            '2025-07-06 01:52:06',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            33,
            'りくのテスト',
            'うううううう',
            3,
            '2025-07-26 01:52:00',
            '2025-07-26 07:52:00',
            '恵比寿駅',
            13,
            35.6467145,
            139.7100818,
            '57620eda-417d-45f4-878d-dfa9ed7b7e3c',
            '2025-07-06 01:53:14',
            '2025-07-06 01:53:14',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            34,
            '参政党を応援しよう。',
            '日本を取り戻そう！',
            4,
            '2025-08-30 08:05:00',
            '2025-08-30 12:05:00',
            '新宿駅',
            13,
            35.6896067,
            139.7005713,
            '87a89127-bea0-4122-b37a-0b785948532b',
            '2025-08-10 02:06:31',
            '2025-08-10 02:06:31',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            35,
            '農家の尊厳を取り戻す',
            '時給10円と言われる農家の必要性を国に訴える。',
            5,
            '2025-10-04 12:00:00',
            '2025-10-04 15:00:00',
            '国会議事堂前',
            13,
            35.6738959,
            139.7449634,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-08-13 22:41:29',
            '2025-08-13 22:41:29',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            36,
            '農家の尊厳を取り戻す',
            '時給10円と言われる農家の必要性を国に訴える。',
            5,
            '2025-10-04 10:00:00',
            '2025-10-04 15:00:00',
            '国会議事堂前',
            13,
            35.6738959,
            139.7449634,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-08-13 22:46:12',
            '2025-08-13 22:46:12',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            37,
            '石破やめろデモ',
            '退陣を訴えるデモ',
            4,
            '2025-10-25 14:00:00',
            '2025-10-25 17:00:00',
            '国会議事堂前',
            13,
            35.6738959,
            139.7449634,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 11:49:07',
            '2025-10-05 11:49:07',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            38,
            '世襲をやめさせる',
            '政治家の世襲をやめさせましょう。',
            4,
            '2025-11-08 12:00:00',
            '2025-11-08 15:00:00',
            '渋谷駅',
            13,
            35.6580339,
            139.7016358,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 12:49:35',
            '2025-10-05 12:49:35',
            NULL
        );
INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            39,
            '最低賃金をあげよう',
            '地方の最低賃金をあげよう',
            5,
            '2025-12-06 13:00:00',
            '2025-12-06 17:00:00',
            '秋田駅',
            5,
            39.7169121,
            140.1297397,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 13:01:10',
            '2025-10-05 13:01:10',
            NULL
        );

-- Comment Data
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            1,
            5,
            'We support you!',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-11 00:18:16',
            '2025-01-11 00:39:49',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            2,
            5,
            '頑張ってください！',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-11 01:31:57',
            '2025-01-11 01:31:57',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            3,
            5,
            '寄付金送ります！',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-11 01:36:03',
            '2025-01-11 01:36:03',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            4,
            5,
            '一緒に頑張りましょう',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-12 15:09:41',
            '2025-01-12 15:09:41',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            6,
            8,
            '私も参加します！',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-18 15:24:57',
            '2025-01-18 15:24:57',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            7,
            5,
            'ついでに海の掃除しましょか',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-22 01:23:10',
            '2025-02-22 01:23:10',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            8,
            5,
            'APIテスト',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-24 17:11:54',
            '2025-02-24 17:11:54',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            9,
            5,
            'APIテスト2',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-24 21:24:36',
            '2025-02-24 21:24:36',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            10,
            5,
            'APIテスト3',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-24 21:40:41',
            '2025-02-24 21:40:41',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            11,
            5,
            'APIテスト4',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-24 21:42:36',
            '2025-02-24 21:42:36',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            12,
            5,
            'APIテスト5',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-05-29 11:07:16',
            '2025-05-29 11:07:16',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            13,
            5,
            'APIテスト6だよ',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-04 17:05:50',
            '2025-06-04 17:05:50',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            14,
            5,
            'APIテスト7',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-04 18:02:10',
            '2025-06-04 18:02:10',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            15,
            5,
            'あはは',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-07 16:49:37',
            '2025-06-07 16:49:37',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            16,
            5,
            'へへへ',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-08 02:50:54',
            '2025-06-08 02:50:54',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            17,
            5,
            'てすと',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-10 08:58:16',
            '2025-06-10 08:58:16',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            19,
            5,
            'Test3',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-12 00:01:04',
            '2025-06-12 00:01:04',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            20,
            5,
            'ユーザープロフィール画像追加テスト',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-12 20:58:12',
            '2025-06-12 20:58:12',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            21,
            5,
            '私も基地に反対です！今後の将来を担う子供達に負の遺産を残さないためにも私たちの代でケリをつけましょう！戦争だめ絶対！！！',
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-12 21:10:57',
            '2025-06-12 21:10:57',
            NULL
        );
INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            22,
            5,
            'Webからのコメントです',
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-08-14 02:28:24',
            '2025-08-14 02:28:24',
            NULL
        );

-- Participant Data
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            1,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-11 02:42:19',
            '2025-01-11 02:42:19',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            7,
            5,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-01-13 04:29:05',
            '2025-01-13 04:29:05',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            8,
            4,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-01-13 04:30:15',
            '2025-01-13 04:30:15',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            9,
            7,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-01-13 04:51:34',
            '2025-01-13 04:51:34',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            10,
            8,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-01-18 15:24:38',
            '2025-01-18 15:24:38',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            12,
            9,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-02-22 01:39:29',
            '2025-02-22 01:39:29',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            17,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-10 07:36:03',
            '2025-06-10 07:36:03',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            18,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-17 01:27:15',
            '2025-06-17 01:27:15',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            21,
            27,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-22 00:05:42',
            '2025-06-22 00:05:42',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            22,
            28,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-22 00:26:35',
            '2025-06-22 00:26:35',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            23,
            29,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-29 03:48:24',
            '2025-06-29 03:48:24',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            24,
            1,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-06-29 05:26:49',
            '2025-06-29 05:26:49',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            25,
            30,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-04 18:29:12',
            '2025-07-04 18:29:12',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            26,
            6,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-07-12 08:23:41',
            '2025-07-12 08:23:41',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            27,
            6,
            '87a89127-bea0-4122-b37a-0b785948532b',
            '2025-07-12 08:23:41',
            '2025-07-12 08:23:41',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            28,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-12 08:23:41',
            '2025-07-12 08:23:41',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            30,
            32,
            '87a89127-bea0-4122-b37a-0b785948532b',
            '2025-07-26 00:05:14',
            '2025-07-26 00:05:14',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            32,
            35,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-08-13 22:41:29',
            '2025-08-13 22:41:29',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            33,
            36,
            '45b86f55-eca3-466d-9561-082616921ca3',
            '2025-08-13 22:46:12',
            '2025-08-13 22:46:12',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            34,
            37,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 11:49:07',
            '2025-10-05 11:49:07',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            35,
            38,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 12:49:35',
            '2025-10-05 12:49:35',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            36,
            39,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 13:01:10',
            '2025-10-05 13:01:10',
            NULL
        );
INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            39,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-11-02 01:40:53',
            '2025-11-02 01:40:53',
            NULL
        );

-- Payment Data
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            1,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            2000,
            '2025-01-18 16:08:16',
            '2025-01-18 16:08:16',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            2,
            5,
            '45b86f55-eca3-466d-9561-082616921ca3',
            500,
            '2025-01-19 00:39:54',
            '2025-01-19 00:39:54',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            3,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            200,
            '2025-01-20 10:15:55',
            '2025-01-20 10:15:55',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            4,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            400,
            '2025-01-20 10:20:08',
            '2025-01-20 10:20:08',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            5,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            600,
            '2025-01-20 10:40:20',
            '2025-01-20 10:40:20',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            6,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            600,
            '2025-01-20 10:47:02',
            '2025-01-20 10:47:02',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            7,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            700,
            '2025-01-20 11:34:47',
            '2025-01-20 11:34:47',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            8,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            800,
            '2025-01-20 11:43:49',
            '2025-01-20 11:43:49',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            9,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            1100,
            '2025-01-20 11:50:07',
            '2025-01-20 11:50:07',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            10,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            2100,
            '2025-01-20 12:16:09',
            '2025-01-20 12:16:09',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            11,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            1500,
            '2025-01-20 12:39:04',
            '2025-01-20 12:39:04',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            12,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            3300,
            '2025-01-21 00:49:45',
            '2025-01-21 00:49:45',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            13,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            340,
            '2025-01-21 01:04:07',
            '2025-01-21 01:04:07',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            14,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            66,
            '2025-06-15 16:24:15',
            '2025-06-15 16:24:15',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            15,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            72,
            '2025-06-15 17:01:46',
            '2025-06-15 17:01:46',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            16,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            88,
            '2025-06-15 17:07:44',
            '2025-06-15 17:07:44',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            17,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            99,
            '2025-06-15 17:25:59',
            '2025-06-15 17:25:59',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            18,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            100,
            '2025-06-15 17:31:54',
            '2025-06-15 17:31:54',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            19,
            6,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            55,
            '2025-06-15 17:37:29',
            '2025-06-15 17:37:29',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            20,
            1,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            50,
            '2025-06-20 22:33:43',
            '2025-06-20 22:33:43',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            21,
            2,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            50,
            '2025-06-29 02:58:27',
            '2025-06-29 02:58:27',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            22,
            1,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            100,
            '2025-06-29 03:01:44',
            '2025-06-29 03:01:44',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            23,
            16,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            1500,
            '2025-07-05 12:01:33',
            '2025-07-05 12:01:33',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            24,
            33,
            '87a89127-bea0-4122-b37a-0b785948532b',
            200,
            '2025-07-26 00:06:23',
            '2025-07-26 00:06:23',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            25,
            33,
            '87a89127-bea0-4122-b37a-0b785948532b',
            230,
            '2025-07-26 00:07:26',
            '2025-07-26 00:07:26',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            26,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            123,
            '2025-08-10 04:15:26',
            '2025-08-10 04:15:26',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            27,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            123,
            '2025-08-10 04:16:38',
            '2025-08-10 04:16:38',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            28,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            279,
            '2025-08-10 04:17:52',
            '2025-08-10 04:17:52',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            29,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            1000,
            '2025-08-10 04:21:56',
            '2025-08-10 04:21:56',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            30,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            2000,
            '2025-08-10 04:22:58',
            '2025-08-10 04:22:58',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            31,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            3000,
            '2025-08-10 04:30:01',
            '2025-08-10 04:30:01',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            32,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            4000,
            '2025-08-10 04:34:47',
            '2025-08-10 04:34:47',
            NULL
        );
INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            33,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            700,
            '2025-08-10 17:20:21',
            '2025-08-10 17:20:21',
            NULL
        );

-- Favorite Demo Data
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            6,
            1,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-01 00:54:15',
            '2025-07-01 00:54:15',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            7,
            3,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-01 01:45:32',
            '2025-07-01 01:45:32',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            8,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-01 02:12:12',
            '2025-07-01 02:12:12',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            9,
            2,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-07-05 22:08:42',
            '2025-07-05 22:08:42',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            10,
            32,
            '57620eda-417d-45f4-878d-dfa9ed7b7e3c',
            '2025-07-06 02:35:40',
            '2025-07-06 02:35:40',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            11,
            32,
            '87a89127-bea0-4122-b37a-0b785948532b',
            '2025-07-20 21:55:05',
            '2025-07-20 21:55:05',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            12,
            34,
            '87a89127-bea0-4122-b37a-0b785948532b',
            '2025-08-12 01:07:26',
            '2025-08-12 01:07:26',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            13,
            39,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 18:58:27',
            '2025-10-05 18:58:27',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            17,
            28,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 19:14:13',
            '2025-10-05 19:14:13',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            18,
            29,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 19:25:37',
            '2025-10-05 19:25:37',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            20,
            8,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 19:31:09',
            '2025-10-05 19:31:09',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            21,
            14,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 19:42:45',
            '2025-10-05 19:42:45',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            22,
            26,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-10-05 19:44:51',
            '2025-10-05 19:44:51',
            NULL
        );
INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            24,
            5,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            '2025-11-01 23:49:04',
            '2025-11-01 23:49:04',
            NULL
        );

-- Location Logs Data
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            1,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            29,
            '2025-06-29 04:54:42',
            37.785834000,
            -122.406417000,
            false,
            '2025-06-29 04:54:42'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            2,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            29,
            '2025-06-29 05:06:59',
            37.785834000,
            -122.406417000,
            false,
            '2025-06-29 05:06:59'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            3,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            29,
            '2025-06-29 05:11:41',
            37.785834000,
            -122.406417000,
            false,
            '2025-06-29 05:11:41'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            4,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            29,
            '2025-06-30 19:07:20',
            37.785834000,
            -122.406417000,
            false,
            '2025-06-30 19:07:20'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            5,
            '45b86f55-eca3-466d-9561-082616921ca3',
            6,
            '2025-07-12 08:23:47',
            35.689500000,
            139.691700000,
            true,
            '2025-07-12 08:23:47'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            6,
            '87a89127-bea0-4122-b37a-0b785948532b',
            6,
            '2025-07-12 08:23:47',
            35.689500000,
            139.691700000,
            true,
            '2025-07-12 08:23:47'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            7,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            6,
            '2025-07-12 08:23:47',
            35.689500000,
            139.691700000,
            true,
            '2025-07-12 08:23:47'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            8,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            5,
            '2025-07-12 08:26:12',
            35.681200000,
            139.767100000,
            true,
            '2025-07-12 08:26:12'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            9,
            '45b86f55-eca3-466d-9561-082616921ca3',
            5,
            '2025-07-12 08:26:12',
            35.681200000,
            139.767100000,
            true,
            '2025-07-12 08:26:12'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            10,
            '87a89127-bea0-4122-b37a-0b785948532b',
            5,
            '2025-07-12 08:26:12',
            35.681200000,
            139.767100000,
            true,
            '2025-07-12 08:26:12'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            11,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            5,
            '2025-07-12 08:26:12',
            35.681200000,
            139.767100000,
            true,
            '2025-07-12 08:26:12'
        );
INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            12,
            '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',
            5,
            '2025-07-12 08:26:12',
            35.681200000,
            139.767100000,
            true,
            '2025-07-12 08:26:12'
        );
