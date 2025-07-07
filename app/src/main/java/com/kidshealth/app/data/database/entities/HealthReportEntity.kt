package com.kidshealth.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.ReportStatus
import com.kidshealth.app.data.model.VitalSigns
import java.util.Date

@Entity(tableName = "health_reports")
data class HealthReportEntity(
    @PrimaryKey
    val id: String,
    val patientId: String,
    val patientName: String,
    val doctorId: String,
    val doctorName: String,
    val appointmentDate: Long,
    val reportDate: Long,
    val diagnosis: String,
    val symptoms: String, // JSON string of symptoms list
    val treatment: String,
    val recommendations: String,
    val followUpDate: Long?,
    val status: String,
    // Vital Signs
    val temperature: String,
    val bloodPressure: String,
    val heartRate: String,
    val weight: String,
    val height: String,
    val oxygenSaturation: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

fun HealthReportEntity.toHealthReport(medications: List<MedicationEntity>): HealthReport {
    return HealthReport(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentDate = Date(appointmentDate),
        reportDate = Date(reportDate),
        diagnosis = diagnosis,
        symptoms = if (symptoms.isNotEmpty()) symptoms.split(",").map { it.trim() } else emptyList(),
        treatment = treatment,
        medications = medications.map { it.toMedication() },
        vitals = VitalSigns(
            temperature = temperature,
            bloodPressure = bloodPressure,
            heartRate = heartRate,
            weight = weight,
            height = height,
            oxygenSaturation = oxygenSaturation
        ),
        recommendations = recommendations,
        followUpDate = followUpDate?.let { Date(it) },
        status = ReportStatus.valueOf(status)
    )
}

fun HealthReport.toEntity(): HealthReportEntity {
    return HealthReportEntity(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentDate = appointmentDate.time,
        reportDate = reportDate.time,
        diagnosis = diagnosis,
        symptoms = symptoms.joinToString(","),
        treatment = treatment,
        recommendations = recommendations,
        followUpDate = followUpDate?.time,
        status = status.name,
        temperature = vitals.temperature,
        bloodPressure = vitals.bloodPressure,
        heartRate = vitals.heartRate,
        weight = vitals.weight,
        height = vitals.height,
        oxygenSaturation = vitals.oxygenSaturation
    )
}