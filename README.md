# KidsHealth Android App

A comprehensive health tracking application for children, built with Android Jetpack Compose and Supabase.

## Features

- **User Authentication**: Secure sign-up and sign-in with Supabase Auth
- **Health Reports**: Digital medical reports with vital signs and medications
- **Appointment Scheduling**: Book and manage medical appointments
- **Growth Tracking**: Monitor child's growth over time
- **Notifications**: Reminders for appointments and medications
- **Real-time Sync**: Data synchronized across devices with Supabase

## Setup Instructions

### 1. Supabase Setup

1. Create a new project at [supabase.com](https://supabase.com)
2. Go to Settings > API to get your project URL and anon key
3. Update `app/src/main/java/com/kidshealth/app/data/supabase/SupabaseClient.kt`:
   ```kotlin
   val client = createSupabaseClient(
       supabaseUrl = "YOUR_SUPABASE_URL",
       supabaseKey = "YOUR_SUPABASE_ANON_KEY"
   )
   ```

### 2. Database Migration

Run the SQL migration files in your Supabase SQL editor in this order:
1. `supabase/migrations/create_users_table.sql`
2. `supabase/migrations/create_appointments_table.sql`
3. `supabase/migrations/create_health_reports_table.sql`
4. `supabase/migrations/create_medications_table.sql`

### 3. Authentication Setup

In your Supabase dashboard:
1. Go to Authentication > Settings
2. Enable email authentication
3. Disable email confirmation for development (optional)

### 4. Row Level Security

The migration files automatically set up Row Level Security (RLS) policies to ensure:
- Patients can only access their own data
- Doctors can access their patients' data
- Secure data isolation between users

## Architecture

- **UI Layer**: Jetpack Compose screens and components
- **ViewModel Layer**: State management with StateFlow
- **Repository Layer**: Data access abstraction
- **Data Layer**: Supabase integration with DTOs
- **Database**: PostgreSQL via Supabase with real-time capabilities

## Technologies Used

- **Android**: Jetpack Compose, Navigation, ViewModel, Room (legacy)
- **Backend**: Supabase (PostgreSQL, Auth, Real-time)
- **Networking**: Ktor client
- **Serialization**: Kotlinx Serialization
- **Dependency Injection**: Manual DI with object modules

## Getting Started

1. Clone the repository
2. Set up Supabase as described above
3. Update the Supabase credentials in `SupabaseClient.kt`
4. Run the database migrations
5. Build and run the app

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request