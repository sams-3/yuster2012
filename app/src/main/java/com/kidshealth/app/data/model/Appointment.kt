package com.kidshealth.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Appointment(
    val id: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val appointmentType: String = "",
    val date: Date = Date(),
    val time: String = "",
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    val notes: String = "",
    val reminderSent: Boolean = false
) : Parcelable

enum class AppointmentStatus {
    SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
}