package com.kidshealth.app.screens.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidshealth.app.ui.theme.KidsHealthBackground
import com.kidshealth.app.ui.theme.KidsHealthPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    onBackClick: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var selectedDoctor by remember { mutableStateOf("") }
    var appointmentType by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

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
                        text = "Schedule Appointment",
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
                    // Appointment Type
                    Text(
                        text = "Appointment Type",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            onClick = { appointmentType = "Check-up" },
                            label = { Text("Check-up") },
                            selected = appointmentType == "Check-up"
                        )
                        FilterChip(
                            onClick = { appointmentType = "Vaccination" },
                            label = { Text("Vaccination") },
                            selected = appointmentType == "Vaccination"
                        )
                        FilterChip(
                            onClick = { appointmentType = "Consultation" },
                            label = { Text("Consultation") },
                            selected = appointmentType == "Consultation"
                        )
                    }
                }
                
                item {
                    // Doctor Selection
                    Text(
                        text = "Select Doctor",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DoctorCard(
                            name = "Dr. Sarah Johnson",
                            specialty = "Pediatrician",
                            isSelected = selectedDoctor == "Dr. Sarah Johnson",
                            onClick = { selectedDoctor = "Dr. Sarah Johnson" }
                        )
                        DoctorCard(
                            name = "Dr. Michael Chen",
                            specialty = "Child Specialist",
                            isSelected = selectedDoctor == "Dr. Michael Chen",
                            onClick = { selectedDoctor = "Dr. Michael Chen" }
                        )
                        DoctorCard(
                            name = "Dr. Emily Davis",
                            specialty = "Family Medicine",
                            isSelected = selectedDoctor == "Dr. Emily Davis",
                            onClick = { selectedDoctor = "Dr. Emily Davis" }
                        )
                    }
                }
                
                item {
                    // Date Selection
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = { selectedDate = it },
                        label = { Text("Select Date") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Date"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("MM/DD/YYYY") }
                    )
                }
                
                item {
                    // Time Selection
                    OutlinedTextField(
                        value = selectedTime,
                        onValueChange = { selectedTime = it },
                        label = { Text("Select Time") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Time"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("HH:MM AM/PM") }
                    )
                }
                
                item {
                    // Notes
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Additional Notes (Optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Schedule Button
                    Button(
                        onClick = {
                            // Handle appointment scheduling
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsHealthPrimary),
                        enabled = appointmentType.isNotEmpty() && 
                                 selectedDoctor.isNotEmpty() && 
                                 selectedDate.isNotEmpty() && 
                                 selectedTime.isNotEmpty()
                    ) {
                        Text(
                            text = "Schedule Appointment",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    name: String,
    specialty: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) KidsHealthPrimary.copy(alpha = 0.1f) else Color.White
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(KidsHealthPrimary)
        ) else null,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Doctor",
                tint = if (isSelected) KidsHealthPrimary else Color.Gray,
                modifier = Modifier.size(40.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = specialty,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}