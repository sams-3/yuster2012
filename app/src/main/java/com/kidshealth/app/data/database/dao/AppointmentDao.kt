package com.kidshealth.app.data.database.dao

import androidx.room.*
import com.kidshealth.app.data.database.entities.AppointmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    
    @Query("SELECT * FROM appointments WHERE id = :appointmentId")
    suspend fun getAppointmentById(appointmentId: String): AppointmentEntity?
    
    @Query("SELECT * FROM appointments WHERE patientId = :patientId ORDER BY date DESC")
    fun getAppointmentsByPatient(patientId: String): Flow<List<AppointmentEntity>>
    
    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId ORDER BY date DESC")
    fun getAppointmentsByDoctor(doctorId: String): Flow<List<AppointmentEntity>>
    
    @Query("SELECT * FROM appointments WHERE patientId = :patientId AND status IN ('SCHEDULED', 'CONFIRMED') ORDER BY date ASC")
    fun getUpcomingAppointments(patientId: String): Flow<List<AppointmentEntity>>
    
    @Query("SELECT * FROM appointments ORDER BY date DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity)
    
    @Update
    suspend fun updateAppointment(appointment: AppointmentEntity)
    
    @Delete
    suspend fun deleteAppointment(appointment: AppointmentEntity)
    
    @Query("UPDATE appointments SET status = :status WHERE id = :appointmentId")
    suspend fun updateAppointmentStatus(appointmentId: String, status: String)
    
    @Query("UPDATE appointments SET reminderSent = :reminderSent WHERE id = :appointmentId")
    suspend fun updateReminderSent(appointmentId: String, reminderSent: Boolean)
}