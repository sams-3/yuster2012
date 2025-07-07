package com.kidshealth.app.data.repository

import com.kidshealth.app.data.database.dao.AppointmentDao
import com.kidshealth.app.data.database.entities.toAppointment
import com.kidshealth.app.data.database.entities.toEntity
import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseAppointmentRepository(
    private val appointmentDao: AppointmentDao
) {
    
    fun getAllAppointments(): Flow<List<Appointment>> {
        return appointmentDao.getAllAppointments().map { entities ->
            entities.map { it.toAppointment() }
        }
    }
    
    fun getAppointmentsByPatient(patientId: String): Flow<List<Appointment>> {
        return appointmentDao.getAppointmentsByPatient(patientId).map { entities ->
            entities.map { it.toAppointment() }
        }
    }
    
    fun getUpcomingAppointments(patientId: String): Flow<List<Appointment>> {
        return appointmentDao.getUpcomingAppointments(patientId).map { entities ->
            entities.map { it.toAppointment() }
        }
    }
    
    suspend fun getAppointmentById(appointmentId: String): Appointment? {
        return appointmentDao.getAppointmentById(appointmentId)?.toAppointment()
    }
    
    suspend fun saveAppointment(appointment: Appointment) {
        appointmentDao.insertAppointment(appointment.toEntity())
    }
    
    suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.updateAppointment(appointment.toEntity())
    }
    
    suspend fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        appointmentDao.updateAppointmentStatus(appointmentId, status.name)
    }
    
    suspend fun updateReminderSent(appointmentId: String, reminderSent: Boolean) {
        appointmentDao.updateReminderSent(appointmentId, reminderSent)
    }
    
    suspend fun deleteAppointment(appointmentId: String) {
        appointmentDao.getAppointmentById(appointmentId)?.let {
            appointmentDao.deleteAppointment(it)
        }
    }
}