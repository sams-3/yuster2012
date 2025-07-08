package com.kidshealth.app.di

import com.kidshealth.app.data.repository.SupabaseAppointmentRepository
import com.kidshealth.app.data.repository.SupabaseHealthReportRepository
import com.kidshealth.app.data.repository.SupabaseUserRepository

object SupabaseModule {
    
    fun provideHealthReportRepository(): SupabaseHealthReportRepository {
        return SupabaseHealthReportRepository()
    }
    
    fun provideAppointmentRepository(): SupabaseAppointmentRepository {
        return SupabaseAppointmentRepository()
    }
    
    fun provideUserRepository(): SupabaseUserRepository {
        return SupabaseUserRepository()
    }
}