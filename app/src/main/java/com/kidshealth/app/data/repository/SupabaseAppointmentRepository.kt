package com.kidshealth.app.data.repository

import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import com.kidshealth.app.data.supabase.SupabaseClient
import com.kidshealth.app.data.supabase.dto.AppointmentDto
import com.kidshealth.app.data.supabase.dto.toAppointment
import com.kidshealth.app.data.supabase.dto.toDto
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SupabaseAppointmentRepository {
    
    private val client = SupabaseClient.client
    
    fun getAllAppointments(): Flow<List<Appointment>> = flow {
        try {
            val response = client.from("appointments")
                .select()
                .order("date", ascending = false)
                .decodeList<AppointmentDto>()
            emit(response.map { it.toAppointment() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getAppointmentsByPatient(patientId: String): Flow<List<Appointment>> = flow {
        try {
            val response = client.from("appointments")
                .select()
                .select(Columns.list("*")) {
                    eq("patient_id", patientId)
                }
                .order("date", ascending = false)
                .decodeList<AppointmentDto>()
            emit(response.map { it.toAppointment() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getUpcomingAppointments(patientId: String): Flow<List<Appointment>> = flow {
        try {
            val response = client.from("appointments")
                .select()
                .select(Columns.list("*")) {
                    eq("patient_id", patientId)
                    `in`("status", listOf("SCHEDULED", "CONFIRMED"))
                }
                .order("date", ascending = true)
                .decodeList<AppointmentDto>()
            emit(response.map { it.toAppointment() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    suspend fun getAppointmentById(appointmentId: String): Appointment? {
        return try {
            val response = client.from("appointments")
                .select()
                .select(Columns.list("*")) {
                    eq("id", appointmentId)
                }
                .decodeSingleOrNull<AppointmentDto>()
            response?.toAppointment()
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun saveAppointment(appointment: Appointment) {
        try {
            client.from("appointments").insert(appointment.toDto())
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun updateAppointment(appointment: Appointment) {
        try {
            client.from("appointments")
                .update(appointment.toDto()) {
                    eq("id", appointment.id)
                }
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun updateAppointmentStatus(appointmentId: String, status: AppointmentStatus) {
        try {
            client.from("appointments")
                .update(mapOf("status" to status.name)) {
                    eq("id", appointmentId)
                }
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun updateReminderSent(appointmentId: String, reminderSent: Boolean) {
        try {
            client.from("appointments")
                .update(mapOf("reminder_sent" to reminderSent)) {
                    eq("id", appointmentId)
                }
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun deleteAppointment(appointmentId: String) {
        try {
            client.from("appointments")
                .delete {
                    eq("id", appointmentId)
                }
        } catch (e: Exception) {
            throw e
        }
    }
}