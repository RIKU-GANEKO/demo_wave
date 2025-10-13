-- Migration script: Rename firebase_uid to supabase_uid
-- This script renames the firebase_uid column to supabase_uid in the users table
-- as part of the migration from Firebase authentication to Supabase authentication

-- Step 1: Rename the column
ALTER TABLE users
CHANGE COLUMN firebase_uid supabase_uid VARCHAR(255);

-- Step 2: Update index if exists (optional - adjust based on your schema)
-- If you have an index on firebase_uid, you may want to recreate it
-- DROP INDEX IF EXISTS idx_firebase_uid ON users;
-- CREATE INDEX idx_supabase_uid ON users(supabase_uid);

-- Note: This migration should be run carefully in production
-- Make sure to backup your database before executing this script
