package com.kidshealth.app.data.supabase.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.ReportStatus
import com.kidshealth.app.data.model.VitalSigns
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class HealthReportDto(
    val id: String,
    @SerialName("patient_id")
    val patientId: String,
    @SerialName("patient_name")
    val patientName: String,
    @SerialName("doctor_id")
    val doctorId: String,
    @SerialName("doctor_name")
    val doctorName: String,
    @SerialName("appointment_date")
    val appointmentDate: String,
    @SerialName("report_date")
    val reportDate: String,
    val diagnosis: String = "",
    val symptoms: String = "", // JSON string of symptoms list
    val treatment: String = "",
    val recommendations: String = "",
    @SerialName("follow_up_date")
    val followUpDate: String? = null,
    val status: String,
    // Vital Signs
    val temperature: String = "",
    @SerialName("blood_pressure")
    val bloodPressure: String = "",
    @SerialName("heart_rate")
    val heartRate: String = "",
    val weight: String = "",
    val height: String = "",
    @SerialName("oxygen_saturation")
    val oxygenSaturation: String = "",
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

fun HealthReportDto.toHealthReport(medications: List<MedicationDto> = emptyList()): HealthReport {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    val parsedAppointmentDate = try {
        if (appointmentDate.contains("-")) {
            dateFormat.parse(appointmentDate) ?: Date()
        } else {
            Date(appointmentDate.toLong())
        }
    } catch (e: Exception) {
        Date()
    }
    
    val parsedReportDate = try {
        if (reportDate.contains("-")) {
            dateFormat.parse(reportDate) ?: Date()
        } else {
            Date(reportDate.toLong())
        }
    } catch (e: Exception) {
        Date()
    }
    
    val parsedFollowUpDate = followUpDate?.let { 
        try {
            if (it.contains("-")) {
                dateFormat.parse(it)
            } else {
                Date(it.toLong())
            }
        } catch (e: Exception) {
            null
        }
    }
    
    return HealthReport(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentDate = parsedAppointmentDate,
        reportDate = parsedReportDate,
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
        followUpDate = parsedFollowUpDate,
        status = ReportStatus.valueOf(status)
    )
}

fun HealthReport.toDto(): HealthReportDto {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return HealthReportDto(
        id = id,
        patientId = patientId,
        patientName = patientName,
        doctorId = doctorId,
        doctorName = doctorName,
        appointmentDate = dateFormat.format(appointmentDate),
        reportDate = dateFormat.format(reportDate),
        diagnosis = diagnosis,
        symptoms = symptoms.joinToString(","),
        treatment = treatment,
        recommendations = recommendations,
        followUpDate = followUpDate?.let { dateFormat.format(it) },
        status = status.name,
        temperature = vitals.temperature,
        bloodPressure = vitals.bloodPressure,
        heartRate = vitals.heartRate,
        weight = vitals.weight,
        height = vitals.height,
        oxygenSaturation = vitals.oxygenSaturation
    )
}