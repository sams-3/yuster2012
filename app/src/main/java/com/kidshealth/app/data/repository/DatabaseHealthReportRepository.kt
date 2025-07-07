package com.kidshealth.app.data.repository

import com.kidshealth.app.data.database.dao.HealthReportDao
import com.kidshealth.app.data.database.dao.MedicationDao
import com.kidshealth.app.data.database.entities.toEntity
import com.kidshealth.app.data.database.entities.toHealthReport
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.ReportStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseHealthReportRepository(
    private val healthReportDao: HealthReportDao,
    private val medicationDao: MedicationDao
) {
    
    fun getAllHealthReports(): Flow<List<HealthReport>> {
        return healthReportDao.getAllHealthReports().map { reportEntities ->
            reportEntities.map { reportEntity ->
                val medications = medicationDao.getMedicationsByReportId(reportEntity.id)
                reportEntity.toHealthReport(medications)
            }
        }
    }
    
    fun getHealthReportsByPatient(patientId: String): Flow<List<HealthReport>> {
        return healthReportDao.getHealthReportsByPatient(patientId).map { reportEntities ->
            reportEntities.map { reportEntity ->
                val medications = medicationDao.getMedicationsByReportId(reportEntity.id)
                reportEntity.toHealthReport(medications)
            }
        }
    }
    
    fun getRecentHealthReports(patientId: String, limit: Int = 5): Flow<List<HealthReport>> {
        return healthReportDao.getRecentHealthReports(patientId, limit).map { reportEntities ->
            reportEntities.map { reportEntity ->
                val medications = medicationDao.getMedicationsByReportId(reportEntity.id)
                reportEntity.toHealthReport(medications)
            }
        }
    }
    
    suspend fun getHealthReportById(reportId: String): HealthReport? {
        val reportEntity = healthReportDao.getHealthReportById(reportId)
        return reportEntity?.let {
            val medications = medicationDao.getMedicationsByReportId(reportId)
            it.toHealthReport(medications)
        }
    }
    
    suspend fun saveHealthReport(report: HealthReport) {
        // Save the health report
        healthReportDao.insertHealthReport(report.toEntity())
        
        // Delete existing medications for this report
        medicationDao.deleteMedicationsByReportId(report.id)
        
        // Save new medications
        val medicationEntities = report.medications.map { it.toEntity(report.id) }
        medicationDao.insertMedications(medicationEntities)
    }
    
    suspend fun updateReportStatus(reportId: String, status: ReportStatus) {
        healthReportDao.updateReportStatus(reportId, status.name)
    }
    
    suspend fun deleteHealthReport(reportId: String) {
        medicationDao.deleteMedicationsByReportId(reportId)
        healthReportDao.deleteHealthReportById(reportId)
    }
}