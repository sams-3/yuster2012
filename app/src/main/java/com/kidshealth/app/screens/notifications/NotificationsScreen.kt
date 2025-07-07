package com.kidshealth.app.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.kidshealth.app.ui.theme.KidsHealthBackground
import com.kidshealth.app.ui.theme.KidsHealthPrimary
import java.text.SimpleDateFormat
import java.util.*

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val timestamp: Date,
    val isRead: Boolean = false
)

enum class NotificationType {
    APPOINTMENT_REMINDER,
    REPORT_READY,
    MEDICATION_REMINDER,
    GENERAL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit
) {
    // Sample notifications - in real app, this would come from repository
    val notifications = remember {
        listOf(
            NotificationItem(
                id = "1",
                title = "Appointment Reminder",
                message = "You have an appointment with Dr. Sarah Johnson tomorrow at 10:00 AM",
                type = NotificationType.APPOINTMENT_REMINDER,
                timestamp = Date(System.currentTimeMillis() - 3600000) // 1 hour ago
            ),
            NotificationItem(
                id = "2",
                title = "Medical Report Ready",
                message = "Your child's medical report from the recent visit is now available",
                type = NotificationType.REPORT_READY,
                timestamp = Date(System.currentTimeMillis() - 7200000), // 2 hours ago
                isRead = true
            ),
            NotificationItem(
                id = "3",
                title = "Medication Reminder",
                message = "Time to give your child their prescribed medication",
                type = NotificationType.MEDICATION_REMINDER,
                timestamp = Date(System.currentTimeMillis() - 10800000) // 3 hours ago
            ),
            NotificationItem(
                id = "4",
                title = "Health Tip",
                message = "Remember to maintain a balanced diet for your child's optimal growth",
                type = NotificationType.GENERAL,
                timestamp = Date(System.currentTimeMillis() - 86400000), // 1 day ago
                isRead = true
            )
        )
    }

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
                        text = "Notifications",
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
                    IconButton(onClick = { /* Mark all as read */ }) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "Mark all as read"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
            
            if (notifications.isEmpty()) {
                // Empty State
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "No Notifications",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Notifications",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = "You're all caught up!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    items(notifications.sortedByDescending { it.timestamp }) { notification ->
                        NotificationCard(notification = notification)
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) Color.White else KidsHealthPrimary.copy(alpha = 0.05f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Notification Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(getNotificationColor(notification.type).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getNotificationIcon(notification.type),
                    contentDescription = notification.type.name,
                    tint = getNotificationColor(notification.type),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Notification Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(KidsHealthPrimary)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = notification.message,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = dateFormat.format(notification.timestamp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

fun getNotificationIcon(type: NotificationType): ImageVector {
    return when (type) {
        NotificationType.APPOINTMENT_REMINDER -> Icons.Default.CalendarToday
        NotificationType.REPORT_READY -> Icons.Default.Assignment
        NotificationType.MEDICATION_REMINDER -> Icons.Default.Medication
        NotificationType.GENERAL -> Icons.Default.Info
    }
}

fun getNotificationColor(type: NotificationType): Color {
    return when (type) {
        NotificationType.APPOINTMENT_REMINDER -> Color(0xFF2196F3) // Blue
        NotificationType.REPORT_READY -> Color(0xFF4CAF50) // Green
        NotificationType.MEDICATION_REMINDER -> Color(0xFFFF9800) // Orange
        NotificationType.GENERAL -> Color(0xFF9C27B0) // Purple
    }
}