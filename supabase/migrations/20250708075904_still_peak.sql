/*
  # Create appointments table

  1. New Tables
    - `appointments`
      - `id` (uuid, primary key)
      - `patient_id` (uuid, foreign key to users)
      - `patient_name` (text)
      - `doctor_id` (uuid, foreign key to users)
      - `doctor_name` (text)
      - `appointment_type` (text)
      - `date` (text) - timestamp as string
      - `time` (text)
      - `status` (text) - SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
      - `notes` (text)
      - `reminder_sent` (boolean)
      - `created_at` (timestamp)
      - `updated_at` (timestamp)

  2. Security
    - Enable RLS on `appointments` table
    - Add policy for patients to read their own appointments
    - Add policy for doctors to read their appointments
    - Add policy for authenticated users to create appointments
*/

CREATE TABLE IF NOT EXISTS appointments (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  patient_id uuid NOT NULL,
  patient_name text NOT NULL,
  doctor_id uuid NOT NULL,
  doctor_name text NOT NULL,
  appointment_type text NOT NULL DEFAULT '',
  date text NOT NULL,
  time text NOT NULL,
  status text NOT NULL DEFAULT 'SCHEDULED',
  notes text DEFAULT '',
  reminder_sent boolean DEFAULT false,
  created_at timestamptz DEFAULT now(),
  updated_at timestamptz DEFAULT now(),
  FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE appointments ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Patients can read own appointments"
  ON appointments
  FOR SELECT
  TO authenticated
  USING (patient_id = auth.uid());

CREATE POLICY "Doctors can read their appointments"
  ON appointments
  FOR SELECT
  TO authenticated
  USING (doctor_id = auth.uid());

CREATE POLICY "Users can create appointments"
  ON appointments
  FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Patients can update own appointments"
  ON appointments
  FOR UPDATE
  TO authenticated
  USING (patient_id = auth.uid() OR doctor_id = auth.uid());