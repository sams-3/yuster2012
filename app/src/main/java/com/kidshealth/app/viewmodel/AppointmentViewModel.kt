package com.kidshealth.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import com.kidshealth.app.data.repository.DatabaseAppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel(
    private val repository: DatabaseAppointmentRepository
) : ViewModel() {
    
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()
    
    private val _upcomingAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val upcomingAppointments: StateFlow<List<Appointment>> = _upcomingAppointments.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun loadAppointmentsByPatient(patientId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getAppointmentsByPatient(patientId).collect { appointments ->
                    _appointments.value = appointments
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadUpcomingAppointments(patientId: String) {
        viewModelScope.launch {
            try {
                repository.getUpcomingAppointments(patientId).collect { appointments ->
                    _upcomingAppointments.value = appointments
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    
    fun saveAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                repository.saveAppointment(appointment)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    
    fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        viewModelScope.launch {
            try {
                repository.updateAppointmentStatus(appointmentId, status)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    suspend fun getAppointmentById(appointmentId: String): Appointment? {
        return try {
            repository.getAppointmentById(appointmentId)
        } catch (e: Exception) {
            _error.value = e.message
            null
        }
    }
    
    fun updateReminderSent(appointmentId: String, reminderSent: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateReminderSent(appointmentId, reminderSent)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

class AppointmentViewModelFactory(
    private val repository: DatabaseAppointmentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppointmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppointmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}