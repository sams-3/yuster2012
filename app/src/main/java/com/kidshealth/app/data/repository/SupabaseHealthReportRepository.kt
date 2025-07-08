package com.kidshealth.app.data.repository

import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.ReportStatus
import com.kidshealth.app.data.supabase.SupabaseClient
import com.kidshealth.app.data.supabase.dto.HealthReportDto
import com.kidshealth.app.data.supabase.dto.MedicationDto
import com.kidshealth.app.data.supabase.dto.toDto
import com.kidshealth.app.data.supabase.dto.toHealthReport
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class SupabaseHealthReportRepository {
    
    private val client = SupabaseClient.client
    
    fun getAllHealthReports(): Flow<List<HealthReport>> = flow {
        try {
            val reports = client.from("health_reports")
                .select()
                .order("report_date", Order.DESCENDING)
                .decodeList<HealthReportDto>()
            
            val reportsWithMedications = reports.map { report ->
                val medications = client.from("medications")
                    .select()
                    .eq("report_id", report.id)
                    .decodeList<MedicationDto>()
                report.toHealthReport(medications)
            }
            
            emit(reportsWithMedications)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getHealthReportsByPatient(patientId: String): Flow<List<HealthReport>> = flow {
        try {
            val reports = client.from("health_reports")
                .select()
                .eq("patient_id", patientId)
                .order("report_date", Order.DESCENDING)
                .decodeList<HealthReportDto>()
            
            val reportsWithMedications = reports.map { report ->
                val medications = client.from("medications")
                    .select()
                    .eq("report_id", report.id)
                    .decodeList<MedicationDto>()
                report.toHealthReport(medications)
            }
            
            emit(reportsWithMedications)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getRecentHealthReports(patientId: String, limit: Int = 5): Flow<List<HealthReport>> = flow {
        try {
            val reports = client.from("health_reports")
                .select()
                .eq("patient_id", patientId)
                .order("report_date", Order.DESCENDING)
                .limit(limit.toLong())
                .decodeList<HealthReportDto>()
            
            val reportsWithMedications = reports.map { report ->
                val medications = client.from("medications")
                    .select()
                    .eq("report_id", report.id)
                    .decodeList<MedicationDto>()
                report.toHealthReport(medications)
            }
            
            emit(reportsWithMedications)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    suspend fun getHealthReportById(reportId: String): HealthReport? {
        return try {
            val report = client.from("health_reports")
                .select()
                .eq("id", reportId)
                .decodeSingleOrNull<HealthReportDto>()
            
            report?.let {
                val medications = client.from("medications")
                    .select()
                    .eq("report_id", reportId)
                    .decodeList<MedicationDto>()
                it.toHealthReport(medications)
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun saveHealthReport(report: HealthReport) {
        try {
            // Save the health report
            client.from("health_reports").insert(report.toDto())
            
            // Delete existing medications for this report
            client.from("medications")
                .delete()
                .eq("report_id", report.id)
            
            // Save new medications
            if (report.medications.isNotEmpty()) {
                val medicationDtos = report.medications.map { 
                    it.toDto(report.id, UUID.randomUUID().toString()) 
                }
                client.from("medications").insert(medicationDtos)
            }
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun updateReportStatus(reportId: String, status: ReportStatus) {
        try {
            client.from("health_reports")
                .update(mapOf("status" to status.name))
                .eq("id", reportId)
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun deleteHealthReport(reportId: String) {
        try {
            // Delete medications first
            client.from("medications")
                .delete()
                .eq("report_id", reportId)
            
            // Delete the report
            client.from("health_reports")
                .delete()
                .eq("id", reportId)
        } catch (e: Exception) {
            throw e
        }
    }
}