/*
  # Create medications table

  1. New Tables
    - `medications`
      - `id` (uuid, primary key)
      - `report_id` (uuid, foreign key to health_reports)
      - `name` (text)
      - `dosage` (text)
      - `frequency` (text)
      - `duration` (text)
      - `instructions` (text)
      - `created_at` (timestamp)

  2. Security
    - Enable RLS on `medications` table
    - Add policy for users to read medications from their reports
    - Add policy for doctors to create/update medications
*/

CREATE TABLE IF NOT EXISTS medications (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  report_id uuid NOT NULL,
  name text NOT NULL,
  dosage text NOT NULL DEFAULT '',
  frequency text NOT NULL DEFAULT '',
  duration text NOT NULL DEFAULT '',
  instructions text DEFAULT '',
  created_at timestamptz DEFAULT now(),
  FOREIGN KEY (report_id) REFERENCES health_reports(id) ON DELETE CASCADE
);

ALTER TABLE medications ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can read medications from accessible reports"
  ON medications
  FOR SELECT
  TO authenticated
  USING (
    EXISTS (
      SELECT 1 FROM health_reports 
      WHERE health_reports.id = medications.report_id 
      AND (health_reports.patient_id = auth.uid() OR health_reports.doctor_id = auth.uid())
    )
  );

CREATE POLICY "Doctors can create medications"
  ON medications
  FOR INSERT
  TO authenticated
  WITH CHECK (
    EXISTS (
      SELECT 1 FROM health_reports 
      WHERE health_reports.id = medications.report_id 
      AND health_reports.doctor_id = auth.uid()
    )
  );

CREATE POLICY "Doctors can update medications"
  ON medications
  FOR UPDATE
  TO authenticated
  USING (
    EXISTS (
      SELECT 1 FROM health_reports 
      WHERE health_reports.id = medications.report_id 
      AND health_reports.doctor_id = auth.uid()
    )
  );

CREATE POLICY "Doctors can delete medications"
  ON medications
  FOR DELETE
  TO authenticated
  USING (
    EXISTS (
      SELECT 1 FROM health_reports 
      WHERE health_reports.id = medications.report_id 
      AND health_reports.doctor_id = auth.uid()
    )
  );