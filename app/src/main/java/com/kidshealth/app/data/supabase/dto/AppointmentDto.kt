package com.kidshealth.app.data.supabase.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import java.util.Date

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
    return Appointment(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentType = appointmentType,
        date = Date(date), // You might want to use a proper date parser
        time = time,
        status = AppointmentStatus.valueOf(status),
        notes = notes,
        reminderSent = reminderSent
    )
}

fun Appointment.toDto(): AppointmentDto {
    return AppointmentDto(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentType = appointmentType,
        date = date.time.toString(), // Convert to timestamp string
        time = time,
        status = status.name,
        notes = notes,
        reminderSent = reminderSent
    )
}