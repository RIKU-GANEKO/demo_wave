#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
MySQL to PostgreSQL Data Conversion Script
- Converts user_id (INT) to UUID (new 5 test users)
- Generates PostgreSQL INSERT statements
"""

import csv
import random
from collections import defaultdict

# 新5人のテストユーザーUUID
NEW_USER_UUIDS = [
    '37eaf714-043a-4f2e-a0e4-5aa76092e0d0',  # 山田太郎
    '57620eda-417d-45f4-878d-dfa9ed7b7e3c',  # 佐藤花子
    '87a89127-bea0-4122-b37a-0b785948532b',  # 鈴木一郎
    '45b86f55-eca3-466d-9561-082616921ca3',  # 田中美咲
    '785d91e2-b770-4176-8d9d-b6a818f94ead',  # 高橋健太
]

# 旧user_id → 新UUIDのマッピング（同じユーザーは同じUUIDに）
user_mapping = {}

def get_or_create_uuid_for_user(old_user_id):
    """旧user_idに対して一貫したUUIDを割り当て"""
    if old_user_id not in user_mapping:
        # ランダムに5人から選択
        user_mapping[old_user_id] = random.choice(NEW_USER_UUIDS)
    return user_mapping[old_user_id]

def escape_sql_value(value):
    """SQL値をエスケープ"""
    if value is None or value == 'NULL' or value == '' or value == '\\N':
        return 'NULL'
    # 数値の場合
    try:
        if '.' in str(value):
            return str(float(value))
        else:
            return str(int(value))
    except (ValueError, AttributeError):
        pass
    # 文字列の場合、シングルクォートをエスケープ
    value = str(value).replace("'", "''").replace("\\", "\\\\")
    return f"'{value}'"

def read_tsv(filepath):
    """TSVファイルを読み込み"""
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
        # MySQL警告行をスキップ
        data_lines = [line for line in lines if not line.startswith('mysql:')]
        # 文字列リストからDictReaderを作成
        from io import StringIO
        data_str = ''.join(data_lines)
        reader = csv.DictReader(StringIO(data_str), delimiter='\t')
        return list(reader)

def convert_master_data(table_name, filepath):
    """マスタデータ変換（user_id変換不要）"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for row in rows:
        columns = list(row.keys())
        values = [escape_sql_value(row[col]) for col in columns]

        sql = f"INSERT INTO public.{table_name} ({', '.join(columns)}) VALUES ({', '.join(values)});"
        sql_statements.append(sql)

    return sql_statements

def convert_demo_data(filepath):
    """demoテーブル変換（organizer_user_idをUUIDに）"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for i, row in enumerate(rows):
        # 不完全なデータをスキップ
        if not row.get('organizer_user_id') or row['organizer_user_id'] == 'NULL':
            print(f"⚠️  Skipping incomplete demo row {i} (id={row.get('id')})")
            continue

        # organizer_user_idを変換
        try:
            old_user_id = int(row['organizer_user_id'])
        except (ValueError, TypeError) as e:
            print(f"Error at row {i}: organizer_user_id = {row.get('organizer_user_id')}")
            continue
        new_uuid = get_or_create_uuid_for_user(old_user_id)

        sql = f"""INSERT INTO public.demo (
            id, title, content, category_id, demo_start_date, demo_end_date,
            demo_place, prefecture_id, demo_address_latitude, demo_address_longitude,
            organizer_user_id, created_at, updated_at, deleted_at
        ) VALUES (
            {row['id']},
            {escape_sql_value(row['title'])},
            {escape_sql_value(row['content'])},
            {row['category_id']},
            {escape_sql_value(row['demo_start_date'])},
            {escape_sql_value(row['demo_end_date'])},
            {escape_sql_value(row['demo_place'])},
            {row['prefecture_id']},
            {escape_sql_value(row['demo_address_latitude'])},
            {escape_sql_value(row['demo_address_longitude'])},
            '{new_uuid}',
            {escape_sql_value(row['created_at'])},
            {escape_sql_value(row['updated_at'])},
            {escape_sql_value(row.get('deleted_at', 'NULL'))}
        );"""
        sql_statements.append(sql)

    return sql_statements

def convert_comment_data(filepath, valid_demo_ids=None):
    """commentテーブル変換（user_idをUUIDに）"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for i, row in enumerate(rows):
        if not row.get('user_id') or row['user_id'] == 'NULL':
            print(f"⚠️  Skipping incomplete comment row {i}")
            continue
        # 参照整合性チェック
        if valid_demo_ids and int(row.get('demo_id', 0)) not in valid_demo_ids:
            print(f"⚠️  Skipping comment row {i} - demo_id={row.get('demo_id')} not found")
            continue
        old_user_id = int(row['user_id'])
        new_uuid = get_or_create_uuid_for_user(old_user_id)

        sql = f"""INSERT INTO public.comment (
            id, demo_id, content, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            {row['id']},
            {row['demo_id']},
            {escape_sql_value(row['content'])},
            '{new_uuid}',
            {escape_sql_value(row['created_at'])},
            {escape_sql_value(row['updated_at'])},
            {escape_sql_value(row.get('deleted_at', 'NULL'))}
        );"""
        sql_statements.append(sql)

    return sql_statements

def convert_participant_data(filepath, valid_demo_ids=None):
    """participantテーブル変換"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for i, row in enumerate(rows):
        if not row.get('user_id') or row['user_id'] == 'NULL':
            print(f"⚠️  Skipping incomplete participant row {i}")
            continue
        # 参照整合性チェック
        if valid_demo_ids and int(row.get('demo_id', 0)) not in valid_demo_ids:
            print(f"⚠️  Skipping participant row {i} - demo_id={row.get('demo_id')} not found")
            continue
        old_user_id = int(row['user_id'])
        new_uuid = get_or_create_uuid_for_user(old_user_id)

        sql = f"""INSERT INTO public.participant (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            {row['id']},
            {row['demo_id']},
            '{new_uuid}',
            {escape_sql_value(row['created_at'])},
            {escape_sql_value(row['updated_at'])},
            {escape_sql_value(row.get('deleted_at', 'NULL'))}
        );"""
        sql_statements.append(sql)

    return sql_statements

def convert_payment_data(filepath, valid_demo_ids=None):
    """paymentテーブル変換（donate_user_idをUUIDに）"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for i, row in enumerate(rows):
        if not row.get('donate_user_id') or row['donate_user_id'] == 'NULL':
            print(f"⚠️  Skipping incomplete payment row {i}")
            continue
        # 参照整合性チェック
        if valid_demo_ids and int(row.get('demo_id', 0)) not in valid_demo_ids:
            print(f"⚠️  Skipping payment row {i} - demo_id={row.get('demo_id')} not found")
            continue
        old_user_id = int(row['donate_user_id'])
        new_uuid = get_or_create_uuid_for_user(old_user_id)

        sql = f"""INSERT INTO public.payment (
            id, demo_id, donate_user_id, donate_amount, created_at, updated_at, deleted_at
        ) VALUES (
            {row['id']},
            {row['demo_id']},
            '{new_uuid}',
            {row['donate_amount']},
            {escape_sql_value(row['created_at'])},
            {escape_sql_value(row['updated_at'])},
            {escape_sql_value(row.get('deleted_at', 'NULL'))}
        );"""
        sql_statements.append(sql)

    return sql_statements

def convert_favorite_demo_data(filepath, valid_demo_ids=None):
    """favorite_demoテーブル変換"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for i, row in enumerate(rows):
        if not row.get('user_id') or row['user_id'] == 'NULL':
            print(f"⚠️  Skipping incomplete favorite_demo row {i}")
            continue
        # 参照整合性チェック
        if valid_demo_ids and int(row.get('demo_id', 0)) not in valid_demo_ids:
            print(f"⚠️  Skipping favorite_demo row {i} - demo_id={row.get('demo_id')} not found")
            continue
        old_user_id = int(row['user_id'])
        new_uuid = get_or_create_uuid_for_user(old_user_id)

        sql = f"""INSERT INTO public.favorite_demo (
            id, demo_id, user_id, created_at, updated_at, deleted_at
        ) VALUES (
            {row['id']},
            {row['demo_id']},
            '{new_uuid}',
            {escape_sql_value(row['created_at'])},
            {escape_sql_value(row['updated_at'])},
            {escape_sql_value(row.get('deleted_at', 'NULL'))}
        );"""
        sql_statements.append(sql)

    return sql_statements

def convert_location_logs_data(filepath, valid_demo_ids=None):
    """location_logsテーブル変換"""
    rows = read_tsv(filepath)
    if not rows:
        return []

    sql_statements = []
    for i, row in enumerate(rows):
        if not row.get('user_id') or row['user_id'] == 'NULL':
            print(f"⚠️  Skipping incomplete location_logs row {i}")
            continue
        # 参照整合性チェック
        if valid_demo_ids and int(row.get('demo_id', 0)) not in valid_demo_ids:
            print(f"⚠️  Skipping location_logs row {i} - demo_id={row.get('demo_id')} not found")
            continue
        old_user_id = int(row['user_id'])
        new_uuid = get_or_create_uuid_for_user(old_user_id)

        # BooleanをPostgreSQL形式に変換
        is_within = 'true' if row['is_within_radius'] in ('1', 1, 'true', True) else 'false'

        sql = f"""INSERT INTO public.location_logs (
            id, user_id, demo_id, timestamp, latitude, longitude, is_within_radius, created_at
        ) VALUES (
            {row['id']},
            '{new_uuid}',
            {row['demo_id']},
            {escape_sql_value(row['timestamp'])},
            {row['latitude']},
            {row['longitude']},
            {is_within},
            {escape_sql_value(row['created_at'])}
        );"""
        sql_statements.append(sql)

    return sql_statements

def main():
    print("=" * 60)
    print("MySQL to PostgreSQL Data Conversion")
    print("=" * 60)

    all_sql = []

    # マスタデータ
    print("\n[1/8] Converting category...")
    all_sql.append("-- Category Data")
    all_sql.extend(convert_master_data('category', 'category_export.tsv'))

    print("[2/8] Converting prefecture...")
    all_sql.append("\n-- Prefecture Data")
    all_sql.extend(convert_master_data('prefecture', 'prefecture_export.tsv'))

    # トランザクションデータ（user_id変換）
    print("[3/8] Converting demo...")
    all_sql.append("\n-- Demo Data")
    demo_sql = convert_demo_data('demo_export.tsv')
    all_sql.extend(demo_sql)

    # 有効なdemo_idのセットを作成（参照整合性チェック用）
    demo_rows = read_tsv('demo_export.tsv')
    valid_demo_ids = set()
    for row in demo_rows:
        if row.get('id') and row.get('organizer_user_id'):
            try:
                valid_demo_ids.add(int(row['id']))
            except (ValueError, TypeError):
                continue
    print(f"   Valid demo IDs: {len(valid_demo_ids)}")

    print("[4/8] Converting comment...")
    all_sql.append("\n-- Comment Data")
    all_sql.extend(convert_comment_data('comment_export.tsv', valid_demo_ids))

    print("[5/8] Converting participant...")
    all_sql.append("\n-- Participant Data")
    all_sql.extend(convert_participant_data('participant_export.tsv', valid_demo_ids))

    print("[6/8] Converting payment...")
    all_sql.append("\n-- Payment Data")
    all_sql.extend(convert_payment_data('payment_export.tsv', valid_demo_ids))

    print("[7/8] Converting favorite_demo...")
    all_sql.append("\n-- Favorite Demo Data")
    all_sql.extend(convert_favorite_demo_data('favorite_demo_export.tsv', valid_demo_ids))

    print("[8/8] Converting location_logs...")
    all_sql.append("\n-- Location Logs Data")
    all_sql.extend(convert_location_logs_data('location_logs_export.tsv', valid_demo_ids))

    # SQLファイルに出力
    output_file = '04_insert_data.sql'
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- ============================================\n")
        f.write("-- Data Migration: MySQL to PostgreSQL\n")
        f.write("-- User ID Mapping (Old INT -> New UUID)\n")
        f.write("-- ============================================\n\n")

        # マッピング情報を記録
        f.write("-- User ID Mapping:\n")
        for old_id, new_uuid in sorted(user_mapping.items()):
            f.write(f"-- {old_id} -> {new_uuid}\n")
        f.write("\n")

        # SQLステートメント
        f.write('\n'.join(all_sql))
        f.write('\n')

    print(f"\n✅ Conversion complete!")
    print(f"📄 Output file: {output_file}")
    print(f"📊 User mapping: {len(user_mapping)} users -> 5 new UUIDs")
    print(f"📝 Total SQL statements: {len(all_sql)}")
    print("\nUser ID Mapping:")
    for old_id, new_uuid in sorted(user_mapping.items()):
        print(f"  {old_id} -> {new_uuid}")

if __name__ == '__main__':
    main()
