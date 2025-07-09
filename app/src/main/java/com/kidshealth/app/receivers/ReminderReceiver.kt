package com.kidshealth.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kidshealth.app.utils.NotificationHelper
import java.text.SimpleDateFormat
import java.util.*

class ReminderReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        
        when (intent.getStringExtra("type")) {
            "medication" -> {
                val medicationName = intent.getStringExtra("medication_name") ?: ""
                notificationHelper.sendMedicationReminder(medicationName)
            }
            else -> {
                // Appointment reminder
                val doctorName = intent.getStringExtra("doctor_name") ?: ""
                val appointmentTime = intent.getStringExtra("appointment_time") ?: ""
                val appointmentDate = intent.getLongExtra("appointment_date", 0)
                
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val formattedDateTime = "${dateFormat.format(Date(appointmentDate))} at $appointmentTime"
                
                notificationHelper.sendAppointmentReminder(
                    appointmentTime = formattedDateTime,
                    doctorName = doctorName
                )
            }
        }
    }
}