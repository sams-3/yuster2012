package com.kidshealth.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class HealthReport(
    val id: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val appointmentDate: Date = Date(),
    val reportDate: Date = Date(),
    val diagnosis: String = "",
    val symptoms: List<String> = emptyList(),
    val treatment: String = "",
    val medications: List<Medication> = emptyList(),
    val vitals: VitalSigns = VitalSigns(),
    val recommendations: String = "",
    val followUpDate: Date? = null,
    val status: ReportStatus = ReportStatus.PENDING
) : Parcelable

@Parcelize
data class Medication(
    val name: String = "",
    val dosage: String = "",
    val frequency: String = "",
    val duration: String = "",
    val instructions: String = ""
) : Parcelable

@Parcelize
data class VitalSigns(
    val temperature: String = "",
    val bloodPressure: String = "",
    val heartRate: String = "",
    val weight: String = "",
    val height: String = "",
    val oxygenSaturation: String = ""
) : Parcelable

enum class ReportStatus {
    PENDING, COMPLETED, REVIEWED
}