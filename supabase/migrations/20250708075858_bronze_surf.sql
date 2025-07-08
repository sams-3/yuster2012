/*
  # Create users table

  1. New Tables
    - `users`
      - `id` (uuid, primary key)
      - `full_name` (text)
      - `email` (text, unique)
      - `user_type` (text) - PATIENT, DOCTOR, ADMIN
      - `profile_image_url` (text)
      - `phone_number` (text)
      - `date_of_birth` (text)
      - `address` (text)
      - `created_at` (timestamp)
      - `updated_at` (timestamp)

  2. Security
    - Enable RLS on `users` table
    - Add policy for authenticated users to read their own data
    - Add policy for authenticated users to update their own data
*/

CREATE TABLE IF NOT EXISTS users (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  full_name text NOT NULL DEFAULT '',
  email text UNIQUE NOT NULL,
  user_type text NOT NULL DEFAULT 'PATIENT',
  profile_image_url text DEFAULT '',
  phone_number text DEFAULT '',
  date_of_birth text DEFAULT '',
  address text DEFAULT '',
  created_at timestamptz DEFAULT now(),
  updated_at timestamptz DEFAULT now()
);

ALTER TABLE users ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can read own data"
  ON users
  FOR SELECT
  TO authenticated
  USING (auth.uid() = id);

CREATE POLICY "Users can update own data"
  ON users
  FOR UPDATE
  TO authenticated
  USING (auth.uid() = id);

CREATE POLICY "Users can insert own data"
  ON users
  FOR INSERT
  TO authenticated
  WITH CHECK (auth.uid() = id);