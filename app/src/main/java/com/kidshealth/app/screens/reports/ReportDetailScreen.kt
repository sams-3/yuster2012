package com.kidshealth.app.screens.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.Medication
import com.kidshealth.app.ui.theme.KidsHealthBackground
import com.kidshealth.app.ui.theme.KidsHealthPrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailScreen(
    report: HealthReport?,
    onBackClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KidsHealthBackground)
    ) {
        if (report == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Report not found",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
            return@Box
        }
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            TopAppBar(
                title = {
                    Text(
                        text = "Medical Report",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share report */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Header Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = report.doctorName,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Patient: ${report.patientName}",
                                        fontSize = 16.sp,
                                        color = Color.Gray
                                    )
                                }
                                StatusChip(status = report.status)
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "Report Date: ${dateFormat.format(report.reportDate)}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
                
                // Vital Signs
                if (hasVitalSigns(report)) {
                    item {
                        SectionCard(
                            title = "Vital Signs",
                            icon = Icons.Default.MonitorHeart
                        ) {
                            VitalSignsGrid(report = report)
                        }
                    }
                }
                
                // Symptoms
                if (report.symptoms.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "Symptoms",
                            icon = Icons.Default.Sick
                        ) {
                            Text(
                                text = report.symptoms.joinToString(", "),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
                
                // Diagnosis
                if (report.diagnosis.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "Diagnosis",
                            icon = Icons.Default.Assignment
                        ) {
                            Text(
                                text = report.diagnosis,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
                
                // Treatment
                if (report.treatment.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "Treatment Plan",
                            icon = Icons.Default.MedicalServices
                        ) {
                            Text(
                                text = report.treatment,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
                
                // Medications
                if (report.medications.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "Medications",
                            icon = Icons.Default.Medication
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                report.medications.forEach { medication ->
                                    MedicationDetailCard(medication = medication)
                                }
                            }
                        }
                    }
                }
                
                // Recommendations
                if (report.recommendations.isNotEmpty()) {
                    item {
                        SectionCard(
                            title = "Recommendations",
                            icon = Icons.Default.Lightbulb
                        ) {
                            Text(
                                text = report.recommendations,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
                
                // Follow-up
                report.followUpDate?.let { followUpDate ->
                    item {
                        SectionCard(
                            title = "Follow-up",
                            icon = Icons.Default.CalendarToday
                        ) {
                            Text(
                                text = "Next appointment: ${dateFormat.format(followUpDate)}",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = KidsHealthPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            content()
        }
    }
}

@Composable
fun VitalSignsGrid(report: HealthReport) {
    val vitals = report.vitals
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (vitals.temperature.isNotEmpty()) {
                VitalSignItem("Temperature", "${vitals.temperature}°C")
            }
            if (vitals.heartRate.isNotEmpty()) {
                VitalSignItem("Heart Rate", "${vitals.heartRate} bpm")
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (vitals.bloodPressure.isNotEmpty()) {
                VitalSignItem("Blood Pressure", vitals.bloodPressure)
            }
            if (vitals.oxygenSaturation.isNotEmpty()) {
                VitalSignItem("O2 Saturation", "${vitals.oxygenSaturation}%")
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (vitals.weight.isNotEmpty()) {
                VitalSignItem("Weight", "${vitals.weight} kg")
            }
            if (vitals.height.isNotEmpty()) {
                VitalSignItem("Height", "${vitals.height} cm")
            }
        }
    }
}

@Composable
fun VitalSignItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun MedicationDetailCard(medication: Medication) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KidsHealthBackground)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = medication.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "${medication.dosage} - ${medication.frequency}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            if (medication.duration.isNotEmpty()) {
                Text(
                    text = "Duration: ${medication.duration}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            if (medication.instructions.isNotEmpty()) {
                Text(
                    text = "Instructions: ${medication.instructions}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

fun hasVitalSigns(report: HealthReport): Boolean {
    val vitals = report.vitals
    return vitals.temperature.isNotEmpty() ||
           vitals.bloodPressure.isNotEmpty() ||
           vitals.heartRate.isNotEmpty() ||
           vitals.weight.isNotEmpty() ||
           vitals.height.isNotEmpty() ||
           vitals.oxygenSaturation.isNotEmpty()
}