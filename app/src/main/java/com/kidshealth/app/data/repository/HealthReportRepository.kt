package com.kidshealth.app.data.repository

import com.kidshealth.app.data.model.HealthReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HealthReportRepository {
    private val _healthReports = MutableStateFlow<List<HealthReport>>(emptyList())
    val healthReports: Flow<List<HealthReport>> = _healthReports.asStateFlow()

    suspend fun saveHealthReport(report: HealthReport) {
        val currentReports = _healthReports.value.toMutableList()
        val existingIndex = currentReports.indexOfFirst { it.id == report.id }
        
        if (existingIndex != -1) {
            currentReports[existingIndex] = report
        } else {
            currentReports.add(report)
        }
        
        _healthReports.value = currentReports
    }

    suspend fun getHealthReportsByPatient(patientId: String): List<HealthReport> {
        return _healthReports.value.filter { it.patientId == patientId }
    }

    suspend fun getHealthReportById(reportId: String): HealthReport? {
        return _healthReports.value.find { it.id == reportId }
    }

    suspend fun deleteHealthReport(reportId: String) {
        val currentReports = _healthReports.value.toMutableList()
        currentReports.removeAll { it.id == reportId }
        _healthReports.value = currentReports
    }
}