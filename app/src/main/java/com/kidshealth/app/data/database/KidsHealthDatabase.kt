package com.kidshealth.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.kidshealth.app.data.database.dao.*
import com.kidshealth.app.data.database.entities.*

@Database(
    entities = [
        UserEntity::class,
        AppointmentEntity::class,
        HealthReportEntity::class,
        MedicationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KidsHealthDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun healthReportDao(): HealthReportDao
    abstract fun medicationDao(): MedicationDao
    
    companion object {
        @Volatile
        private var INSTANCE: KidsHealthDatabase? = null
        
        fun getDatabase(context: Context): KidsHealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KidsHealthDatabase::class.java,
                    "kids_health_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}