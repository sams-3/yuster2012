package com.kidshealth.app.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidshealth.app.data.model.*
import com.kidshealth.app.ui.theme.KidsHealthBackground
import com.kidshealth.app.ui.theme.KidsHealthPrimary
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorReportFormScreen(
    patientId: String = "",
    patientName: String = "",
    onBackClick: () -> Unit,
    onReportSaved: (HealthReport) -> Unit
) {
    var diagnosis by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var treatment by remember { mutableStateOf("") }
    var recommendations by remember { mutableStateOf("") }
    var followUpDate by remember { mutableStateOf("") }
    
    // Vital Signs
    var temperature by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var oxygenSaturation by remember { mutableStateOf("") }
    
    // Medications
    var medications by remember { mutableStateOf(listOf<Medication>()) }
    var showAddMedicationDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KidsHealthBackground)
    ) {
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
                    // Patient Info
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Patient Information",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Name: $patientName",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Date: ${Date()}",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
                
                item {
                    // Vital Signs Section
                    Text(
                        text = "Vital Signs",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = temperature,
                            onValueChange = { temperature = it },
                            label = { Text("Temperature (°C)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = heartRate,
                            onValueChange = { heartRate = it },
                            label = { Text("Heart Rate") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
                
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = bloodPressure,
                            onValueChange = { bloodPressure = it },
                            label = { Text("Blood Pressure") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = oxygenSaturation,
                            onValueChange = { oxygenSaturation = it },
                            label = { Text("O2 Sat (%)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
                
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Weight (kg)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Height (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
                
                item {
                    // Symptoms
                    OutlinedTextField(
                        value = symptoms,
                        onValueChange = { symptoms = it },
                        label = { Text("Symptoms") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )
                }
                
                item {
                    // Diagnosis
                    OutlinedTextField(
                        value = diagnosis,
                        onValueChange = { diagnosis = it },
                        label = { Text("Diagnosis") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )
                }
                
                item {
                    // Treatment
                    OutlinedTextField(
                        value = treatment,
                        onValueChange = { treatment = it },
                        label = { Text("Treatment Plan") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )
                }
                
                item {
                    // Medications Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Medications",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        IconButton(
                            onClick = { showAddMedicationDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Medication",
                                tint = KidsHealthPrimary
                            )
                        }
                    }
                }
                
                items(medications) { medication ->
                    MedicationCard(
                        medication = medication,
                        onRemove = {
                            medications = medications.filter { it != medication }
                        }
                    )
                }
                
                item {
                    // Recommendations
                    OutlinedTextField(
                        value = recommendations,
                        onValueChange = { recommendations = it },
                        label = { Text("Recommendations") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )
                }
                
                item {
                    // Follow-up Date
                    OutlinedTextField(
                        value = followUpDate,
                        onValueChange = { followUpDate = it },
                        label = { Text("Follow-up Date (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("MM/DD/YYYY") }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Save Report Button
                    Button(
                        onClick = {
                            val report = HealthReport(
                                id = UUID.randomUUID().toString(),
                                patientId = patientId,
                                patientName = patientName,
                                doctorId = "doctor_1", // This should come from logged in doctor
                                doctorName = "Dr. Sarah Johnson", // This should come from logged in doctor
                                diagnosis = diagnosis,
                                symptoms = symptoms.split(",").map { it.trim() },
                                treatment = treatment,
                                medications = medications,
                                vitals = VitalSigns(
                                    temperature = temperature,
                                    bloodPressure = bloodPressure,
                                    heartRate = heartRate,
                                    weight = weight,
                                    height = height,
                                    oxygenSaturation = oxygenSaturation
                                ),
                                recommendations = recommendations,
                                status = ReportStatus.COMPLETED
                            )
                            onReportSaved(report)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsHealthPrimary),
                        enabled = diagnosis.isNotEmpty() && treatment.isNotEmpty()
                    ) {
                        Text(
                            text = "Save Medical Report",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    
    // Add Medication Dialog
    if (showAddMedicationDialog) {
        AddMedicationDialog(
            onDismiss = { showAddMedicationDialog = false },
            onAdd = { medication ->
                medications = medications + medication
                showAddMedicationDialog = false
            }
        )
    }
}

@Composable
fun MedicationCard(
    medication: Medication,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
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
                if (medication.instructions.isNotEmpty()) {
                    Text(
                        text = medication.instructions,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = Color.Red
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationDialog(
    onDismiss: () -> Unit,
    onAdd: (Medication) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Medication") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Medication Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosage") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = frequency,
                    onValueChange = { frequency = it },
                    label = { Text("Frequency") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotEmpty() && dosage.isNotEmpty()) {
                        onAdd(
                            Medication(
                                name = name,
                                dosage = dosage,
                                frequency = frequency,
                                duration = duration,
                                instructions = instructions
                            )
                        )
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}