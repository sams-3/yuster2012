package com.kidshealth.app.data.supabase.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class AppointmentDto(
    val id: String,
    @SerialName("patient_id")
    val patientId: String,
    @SerialName("patient_name")
    val patientName: String,
    @SerialName("doctor_id")
    val doctorId: String,
    @SerialName("doctor_name")
    val doctorName: String,
    @SerialName("appointment_type")
    val appointmentType: String,
    val date: String, // ISO string format
    val time: String,
    val status: String,
    val notes: String = "",
    @SerialName("reminder_sent")
    val reminderSent: Boolean = false,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

fun AppointmentDto.toAppointment(): Appointment {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsedDate = try {
        if (date.contains("-")) {
            // If it's a date string format (yyyy-MM-dd)
            dateFormat.parse(date) ?: Date()
        } else {
            // If it's a timestamp
            Date(date.toLong())
        }
    } catch (e: Exception) {
        Date() // Fallback to current date
    }
    
    return Appointment(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentType = appointmentType,
        date = parsedDate,
        time = time,
        status = AppointmentStatus.valueOf(status),
        notes = notes,
        reminderSent = reminderSent
    )
}

fun Appointment.toDto(): AppointmentDto {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return AppointmentDto(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentType = appointmentType,
        date = dateFormat.format(date), // Convert to date string format
        time = time,
        status = status.name,
        notes = notes,
        reminderSent = reminderSent
    )
}