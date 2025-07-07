package com.kidshealth.app.di

import android.content.Context
import com.kidshealth.app.data.database.KidsHealthDatabase
import com.kidshealth.app.data.repository.DatabaseAppointmentRepository
import com.kidshealth.app.data.repository.DatabaseHealthReportRepository
import com.kidshealth.app.data.repository.DatabaseUserRepository

object DatabaseModule {
    
    fun provideDatabase(context: Context): KidsHealthDatabase {
        return KidsHealthDatabase.getDatabase(context)
    }
    
    fun provideHealthReportRepository(context: Context): DatabaseHealthReportRepository {
        val database = provideDatabase(context)
        return DatabaseHealthReportRepository(
            database.healthReportDao(),
            database.medicationDao()
        )
    }
    
    fun provideAppointmentRepository(context: Context): DatabaseAppointmentRepository {
        val database = provideDatabase(context)
        return DatabaseAppointmentRepository(database.appointmentDao())
    }
    
    fun provideUserRepository(context: Context): DatabaseUserRepository {
        val database = provideDatabase(context)
        return DatabaseUserRepository(database.userDao())
    }
}