package com.kidshealth.app.data.database.dao

import androidx.room.*
import com.kidshealth.app.data.database.entities.MedicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    
    @Query("SELECT * FROM medications WHERE reportId = :reportId")
    suspend fun getMedicationsByReportId(reportId: String): List<MedicationEntity>
    
    @Query("SELECT * FROM medications WHERE reportId = :reportId")
    fun getMedicationsByReportIdFlow(reportId: String): Flow<List<MedicationEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedications(medications: List<MedicationEntity>)
    
    @Update
    suspend fun updateMedication(medication: MedicationEntity)
    
    @Delete
    suspend fun deleteMedication(medication: MedicationEntity)
    
    @Query("DELETE FROM medications WHERE reportId = :reportId")
    suspend fun deleteMedicationsByReportId(reportId: String)
}