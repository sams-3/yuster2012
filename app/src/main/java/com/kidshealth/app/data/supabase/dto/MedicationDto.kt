package com.kidshealth.app.data.supabase.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.kidshealth.app.data.model.Medication

@Serializable
data class MedicationDto(
    val id: String,
    @SerialName("report_id")
    val reportId: String,
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    val instructions: String = "",
    @SerialName("created_at")
    val createdAt: String? = null
)

fun MedicationDto.toMedication(): Medication {
    return Medication(
        name = name,
        dosage = dosage,
        frequency = frequency,
        duration = duration,
        instructions = instructions
    )
}

fun Medication.toDto(reportId: String, id: String = ""): MedicationDto {
    return MedicationDto(
        id = id,
        reportId = reportId,
        name = name,
        dosage = dosage,
        frequency = frequency,
        duration = duration,
        instructions = instructions
    )
}