package com.kidshealth.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.ReportStatus
import com.kidshealth.app.data.repository.SupabaseHealthReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SupabaseHealthReportViewModel(
    private val repository: SupabaseHealthReportRepository
) : ViewModel() {
    
    private val _healthReports = MutableStateFlow<List<HealthReport>>(emptyList())
    val healthReports: StateFlow<List<HealthReport>> = _healthReports.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadAllHealthReports()
    }
    
    private fun loadAllHealthReports() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getAllHealthReports().collect { reports ->
                    _healthReports.value = reports
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadHealthReportsByPatient(patientId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getHealthReportsByPatient(patientId).collect { reports ->
                    _healthReports.value = reports
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun saveHealthReport(report: HealthReport) {
        viewModelScope.launch {
            try {
                repository.saveHealthReport(report)
                // Refresh the list
                loadAllHealthReports()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    
    fun updateReportStatus(reportId: String, status: ReportStatus) {
        viewModelScope.launch {
            try {
                repository.updateReportStatus(reportId, status)
                // Refresh the list
                loadAllHealthReports()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    
    suspend fun getHealthReportById(reportId: String): HealthReport? {
        return try {
            repository.getHealthReportById(reportId)
        } catch (e: Exception) {
            _error.value = e.message
            null
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}

class SupabaseHealthReportViewModelFactory(
    private val repository: SupabaseHealthReportRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupabaseHealthReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SupabaseHealthReportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}