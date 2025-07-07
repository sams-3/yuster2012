package com.kidshealth.app.data.database.dao

import androidx.room.*
import com.kidshealth.app.data.database.entities.HealthReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthReportDao {
    
    @Query("SELECT * FROM health_reports WHERE id = :reportId")
    suspend fun getHealthReportById(reportId: String): HealthReportEntity?
    
    @Query("SELECT * FROM health_reports WHERE patientId = :patientId ORDER BY reportDate DESC")
    fun getHealthReportsByPatient(patientId: String): Flow<List<HealthReportEntity>>
    
    @Query("SELECT * FROM health_reports WHERE doctorId = :doctorId ORDER BY reportDate DESC")
    fun getHealthReportsByDoctor(doctorId: String): Flow<List<HealthReportEntity>>
    
    @Query("SELECT * FROM health_reports ORDER BY reportDate DESC")
    fun getAllHealthReports(): Flow<List<HealthReportEntity>>
    
    @Query("SELECT * FROM health_reports WHERE patientId = :patientId ORDER BY reportDate DESC LIMIT :limit")
    fun getRecentHealthReports(patientId: String, limit: Int): Flow<List<HealthReportEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthReport(report: HealthReportEntity)
    
    @Update
    suspend fun updateHealthReport(report: HealthReportEntity)
    
    @Delete
    suspend fun deleteHealthReport(report: HealthReportEntity)
    
    @Query("DELETE FROM health_reports WHERE id = :reportId")
    suspend fun deleteHealthReportById(reportId: String)
    
    @Query("UPDATE health_reports SET status = :status WHERE id = :reportId")
    suspend fun updateReportStatus(reportId: String, status: String)
}