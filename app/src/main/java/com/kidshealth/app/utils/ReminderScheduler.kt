package com.kidshealth.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.kidshealth.app.data.model.Appointment
import com.kidshealth.app.receivers.ReminderReceiver
import java.util.*

class ReminderScheduler(private val context: Context) {
    
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    fun scheduleAppointmentReminder(appointment: Appointment) {
        val reminderTime = calculateReminderTime(appointment.date)
        
        if (reminderTime > System.currentTimeMillis()) {
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("appointment_id", appointment.id)
                putExtra("patient_name", appointment.patientName)
                putExtra("doctor_name", appointment.doctorName)
                putExtra("appointment_time", appointment.time)
                putExtra("appointment_date", appointment.date.time)
            }
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                appointment.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        }
    }
    
    fun cancelAppointmentReminder(appointmentId: String) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            appointmentId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.cancel(pendingIntent)
    }
    
    private fun calculateReminderTime(appointmentDate: Date): Long {
        // Send reminder 24 hours before appointment
        return appointmentDate.time - (24 * 60 * 60 * 1000)
    }
    
    fun scheduleMedicationReminder(medicationName: String, times: List<String>) {
        times.forEach { time ->
            val calendar = Calendar.getInstance().apply {
                val timeParts = time.split(":")
                set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
                set(Calendar.MINUTE, timeParts[1].toInt())
                set(Calendar.SECOND, 0)
                
                // If time has passed today, schedule for tomorrow
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("type", "medication")
                putExtra("medication_name", medicationName)
            }
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                (medicationName + time).hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }
}