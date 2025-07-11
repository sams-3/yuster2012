package com.kidshealth.app.data.repository

import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class AppointmentRepository {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: Flow<List<Appointment>> = _appointments.asStateFlow()

    suspend fun saveAppointment(appointment: Appointment) {
        val currentAppointments = _appointments.value.toMutableList()
        val existingIndex = currentAppointments.indexOfFirst { it.id == appointment.id }
        
        if (existingIndex != -1) {
            currentAppointments[existingIndex] = appointment
        } else {
            currentAppointments.add(appointment)
        }
        
        _appointments.value = currentAppointments
    }

    suspend fun getAppointmentsByPatient(patientId: String): List<Appointment> {
        return _appointments.value.filter { it.patientId == patientId }
    }

    suspend fun getUpcomingAppointments(patientId: String): List<Appointment> {
        return _appointments.value.filter { 
            it.patientId == patientId && 
            (it.status == AppointmentStatus.SCHEDULED || it.status == AppointmentStatus.CONFIRMED)
        }
    }

    suspend fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        val currentAppointments = _appointments.value.toMutableList()
        val index = currentAppointments.indexOfFirst { it.id == appointmentId }
        
        if (index != -1) {
            currentAppointments[index] = currentAppointments[index].copy(status = status)
            _appointments.value = currentAppointments
        }
    }
    
    suspend fun getAppointmentById(appointmentId: String): Appointment? {
        return _appointments.value.find { it.id == appointmentId }
    }
    
    suspend fun updateReminderSent(appointmentId: String, reminderSent: Boolean) {
        val currentAppointments = _appointments.value.toMutableList()
        val index = currentAppointments.indexOfFirst { it.id == appointmentId }
        
        if (index != -1) {
            currentAppointments[index] = currentAppointments[index].copy(reminderSent = reminderSent)
            _appointments.value = currentAppointments
        }
    }
    
    suspend fun getAppointmentsForReminders(): List<Appointment> {
        val now = Date()
        val twentyFourHoursFromNow = Date(now.time + 24 * 60 * 60 * 1000)
        
        return _appointments.value.filter { appointment ->
            !appointment.reminderSent &&
            appointment.status == AppointmentStatus.SCHEDULED &&
            appointment.date.after(now) &&
            appointment.date.before(twentyFourHoursFromNow)
        }
    }
}