/*
  # Create health reports table

  1. New Tables
    - `health_reports`
      - `id` (uuid, primary key)
      - `patient_id` (uuid, foreign key to users)
      - `patient_name` (text)
      - `doctor_id` (uuid, foreign key to users)
      - `doctor_name` (text)
      - `appointment_date` (text) - timestamp as string
      - `report_date` (text) - timestamp as string
      - `diagnosis` (text)
      - `symptoms` (text) - comma-separated symptoms
      - `treatment` (text)
      - `recommendations` (text)
      - `follow_up_date` (text, nullable) - timestamp as string
      - `status` (text) - PENDING, COMPLETED, REVIEWED
      - Vital signs fields
      - `created_at` (timestamp)
      - `updated_at` (timestamp)

  2. Security
    - Enable RLS on `health_reports` table
    - Add policy for patients to read their own reports
    - Add policy for doctors to read their reports
    - Add policy for doctors to create/update reports
*/

CREATE TABLE IF NOT EXISTS health_reports (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  patient_id uuid NOT NULL,
  patient_name text NOT NULL,
  doctor_id uuid NOT NULL,
  doctor_name text NOT NULL,
  appointment_date text NOT NULL,
  report_date text NOT NULL,
  diagnosis text DEFAULT '',
  symptoms text DEFAULT '',
  treatment text DEFAULT '',
  recommendations text DEFAULT '',
  follow_up_date text,
  status text NOT NULL DEFAULT 'PENDING',
  temperature text DEFAULT '',
  blood_pressure text DEFAULT '',
  heart_rate text DEFAULT '',
  weight text DEFAULT '',
  height text DEFAULT '',
  oxygen_saturation text DEFAULT '',
  created_at timestamptz DEFAULT now(),
  updated_at timestamptz DEFAULT now(),
  FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE health_reports ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Patients can read own health reports"
  ON health_reports
  FOR SELECT
  TO authenticated
  USING (patient_id = auth.uid());

CREATE POLICY "Doctors can read their health reports"
  ON health_reports
  FOR SELECT
  TO authenticated
  USING (doctor_id = auth.uid());

CREATE POLICY "Doctors can create health reports"
  ON health_reports
  FOR INSERT
  TO authenticated
  WITH CHECK (doctor_id = auth.uid());

CREATE POLICY "Doctors can update their health reports"
  ON health_reports
  FOR UPDATE
  TO authenticated
  USING (doctor_id = auth.uid());