package com.kidshealth.app.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidshealth.app.data.model.HealthReport
import com.kidshealth.app.ui.theme.KidsHealthBackground
import com.kidshealth.app.ui.theme.KidsHealthPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    healthReports: List<HealthReport> = emptyList(),
    onScheduleAppointmentClick: () -> Unit,
    onTrackGrowthClick: () -> Unit,
    onViewReportsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KidsHealthBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header with Notifications
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Welcome back!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "How is your child today?",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Notifications Icon
                        IconButton(
                            onClick = onNotificationsClick,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        ) {
                            Badge {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = KidsHealthPrimary
                                )
                            }
                        }
                        
                        // Profile Icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(KidsHealthPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                // Quick Actions
                Text(
                    text = "Quick Actions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Schedule\nAppointment",
                        icon = Icons.Default.CalendarToday,
                        onClick = onScheduleAppointmentClick,
                        modifier = Modifier.weight(1f)
                    )
                    
                    QuickActionCard(
                        title = "Track\nGrowth",
                        icon = Icons.Default.TrendingUp,
                        onClick = onTrackGrowthClick,
                        modifier = Modifier.weight(1f)
                    )
                    
                    QuickActionCard(
                        title = "Health\nReports",
                        icon = Icons.Default.Assignment,
                        onClick = onViewReportsClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                // Recent Reports
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Reports",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    if (healthReports.isNotEmpty()) {
                        TextButton(onClick = onViewReportsClick) {
                            Text(
                                text = "View All",
                                color = KidsHealthPrimary
                            )
                        }
                    }
                }
            }
            
            item {
                if (healthReports.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Assignment,
                                contentDescription = "No Reports",
                                modifier = Modifier.size(48.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No medical reports yet",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                            Text(
                                text = "Reports from doctor visits will appear here",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    // Show latest 2 reports
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        healthReports.take(2).forEach { report ->
                            RecentReportCard(report = report)
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                // Health Tips
                Text(
                    text = "Health Tips",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "💡 Daily Tip",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ensure your child gets adequate sleep. Children aged 3-5 need 10-13 hours of sleep per day for optimal growth and development.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = KidsHealthPrimary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun RecentReportCard(report: HealthReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Assignment,
                contentDescription = "Report",
                tint = KidsHealthPrimary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = report.doctorName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = if (report.diagnosis.isNotEmpty()) report.diagnosis else "Medical Report",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}