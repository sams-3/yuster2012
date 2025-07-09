package com.kidshealth.app.services

import android.app.Service
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.IBinder
import android.os.Build
import androidx.core.app.NotificationCompat
import com.kidshealth.app.data.repository.AppointmentRepository
import com.kidshealth.app.utils.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotificationService : Service() {
    
    companion object {
        const val FOREGROUND_SERVICE_ID = 1001
        const val FOREGROUND_CHANNEL_ID = "kidshealth_foreground"
    }
    
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var appointmentRepository: AppointmentRepository
    private var isRunning = false
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    
    override fun onCreate() {
        super.onCreate()
        notificationHelper = NotificationHelper(this)
        appointmentRepository = AppointmentRepository() // In real app, inject this
        createForegroundNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            isRunning = true
            startForeground(FOREGROUND_SERVICE_ID, createForegroundNotification())
            startReminderChecking()
        }
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun createForegroundNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FOREGROUND_CHANNEL_ID,
                "KidsHealth Background Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the app running to send timely reminders"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createForegroundNotification(): Notification {
        return NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setContentTitle("KidsHealth")
            .setContentText("Monitoring for appointment and medication reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }
    
    private fun startReminderChecking() {
        serviceScope.launch {
            while (isRunning) {
                checkAndSendReminders()
                delay(60 * 60 * 1000) // Check every hour
            }
        }
    }
    
    private suspend fun checkAndSendReminders() {
        try {
            val appointmentsForReminders = appointmentRepository.getAppointmentsForReminders()
            
            appointmentsForReminders.forEach { appointment ->
                sendAppointmentReminder(appointment)
                appointmentRepository.updateReminderSent(appointment.id, true)
            }
        } catch (e: Exception) {
            // Log error
        }
    }
    
    private fun sendAppointmentReminder(appointment: com.kidshealth.app.data.model.Appointment) {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
        val appointmentDateTime = "${dateFormat.format(appointment.date)} ${appointment.time}"
        
        notificationHelper.sendAppointmentReminder(
            appointmentTime = appointmentDateTime,
            doctorName = appointment.doctorName
        )
    }
    
    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}