package com.kidshealth.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kidshealth.app.MainActivity
import com.kidshealth.app.R

class NotificationHelper(private val context: Context) {
    
    companion object {
        const val CHANNEL_ID = "kidshealth_notifications"
        const val REMINDER_CHANNEL_ID = "kidshealth_reminders"
        const val APPOINTMENT_REMINDER_ID = 1
        const val REPORT_READY_ID = 2
        const val MEDICATION_REMINDER_ID = 3
    }
    
    init {
        createNotificationChannel()
        createReminderChannel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "KidsHealth Notifications"
            val descriptionText = "Notifications for appointments, reports, and reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createReminderChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "KidsHealth Reminders"
            val descriptionText = "Important reminders for appointments and medications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(REMINDER_CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                setShowBadge(true)
            }
            
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    fun sendAppointmentReminder(
        appointmentTime: String,
        doctorName: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Appointment Reminder")
            .setContentText("You have an appointment with $doctorName tomorrow at $appointmentTime")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Don't forget! You have an appointment with $doctorName tomorrow at $appointmentTime. Please arrive 15 minutes early."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        
        try {
            with(NotificationManagerCompat.from(context)) {
                notify(APPOINTMENT_REMINDER_ID, builder.build())
            }
        } catch (e: SecurityException) {
            // Handle permission not granted
        }
    }
    
    fun sendReportReadyNotification(doctorName: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Medical Report Ready")
            .setContentText("Your medical report from Dr. $doctorName is now available")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        
        try {
            with(NotificationManagerCompat.from(context)) {
                notify(REPORT_READY_ID, builder.build())
            }
        } catch (e: SecurityException) {
            // Handle permission not granted
        }
    }
    
    fun sendMedicationReminder(medicationName: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Medication Reminder")
            .setContentText("Time to give $medicationName to your child")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("It's time to give $medicationName to your child. Please follow the prescribed dosage and instructions."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        
        try {
            with(NotificationManagerCompat.from(context)) {
                notify(MEDICATION_REMINDER_ID, builder.build())
            }
        } catch (e: SecurityException) {
            // Handle permission not granted
        }
    }
    
    fun sendGeneralNotification(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        
        try {
            with(NotificationManagerCompat.from(context)) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        } catch (e: SecurityException) {
            // Handle permission not granted
        }
    }
}