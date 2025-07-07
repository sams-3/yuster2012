package com.kidshealth.app.screens.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.data.model.ReportStatus
import com.kidshealth.app.ui.theme.KidsHealthBackground
import com.kidshealth.app.ui.theme.KidsHealthPrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthReportsScreen(
    healthReports: List<HealthReport>,
    onBackClick: () -> Unit,
    onReportClick: (String) -> Unit
) {
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
                        text = "Health Reports",
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
            
            if (healthReports.isEmpty()) {
                // Empty State
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = "No Reports",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Health Reports",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = "Your medical reports will appear here after doctor visits",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(healthReports.sortedByDescending { it.reportDate }) { report ->
                        HealthReportCard(
                            report = report,
                            onClick = { onReportClick(report.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HealthReportCard(
    report: HealthReport,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                Text(
                    text = report.doctorName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                StatusChip(status = report.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = dateFormat.format(report.reportDate),
                fontSize = 14.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (report.diagnosis.isNotEmpty()) {
                Text(
                    text = "Diagnosis: ${report.diagnosis}",
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2
                )
            }
            
            if (report.symptoms.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Symptoms: ${report.symptoms.joinToString(", ")}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View Details",
                    tint = KidsHealthPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "View Full Report",
                    fontSize = 14.sp,
                    color = KidsHealthPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: ReportStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        ReportStatus.PENDING -> Triple(Color.Orange.copy(alpha = 0.1f), Color.Orange, "Pending")
        ReportStatus.COMPLETED -> Triple(Color.Green.copy(alpha = 0.1f), Color.Green, "Completed")
        ReportStatus.REVIEWED -> Triple(KidsHealthPrimary.copy(alpha = 0.1f), KidsHealthPrimary, "Reviewed")
    }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}