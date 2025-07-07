package com.kidshealth.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kidshealth.app.data.model.Medication
import java.util.UUID

@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val reportId: String,
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    val instructions: String,
    val createdAt: Long = System.currentTimeMillis()
)

fun MedicationEntity.toMedication(): Medication {
    return Medication(
        name = name,
        dosage = dosage,
        frequency = frequency,
        duration = duration,
        instructions = instructions
    )
}

fun Medication.toEntity(reportId: String): MedicationEntity {
    return MedicationEntity(
        reportId = reportId,
        name = name,
        dosage = dosage,
        frequency = frequency,
        duration = duration,
        instructions = instructions
    )
}