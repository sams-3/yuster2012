package com.kidshealth.app.data.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "YOUR_SUPABASE_URL", // Replace with your Supabase URL
        supabaseKey = "YOUR_SUPABASE_ANON_KEY" // Replace with your Supabase anon key
    ) {
        install(Postgrest)
        install(GoTrue)
        install(Realtime)
    }
}