package com.kidshealth.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.data.model.AppointmentStatus
import java.util.Date

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey
    val id: String,
    val patientId: String,
    val patientName: String,
    val doctorId: String,
    val doctorName: String,
    val appointmentType: String,
    val date: Long,
    val time: String,
    val status: String,
    val notes: String,
    val reminderSent: Boolean,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

fun AppointmentEntity.toAppointment(): Appointment {
    return Appointment(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentType = appointmentType,
        date = Date(date),
        time = time,
        status = AppointmentStatus.valueOf(status),
        notes = notes,
        reminderSent = reminderSent
    )
}

fun Appointment.toEntity(): AppointmentEntity {
    return AppointmentEntity(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentType = appointmentType,
        date = date.time,
        time = time,
        status = status.name,
        notes = notes,
        reminderSent = reminderSent
    )
}