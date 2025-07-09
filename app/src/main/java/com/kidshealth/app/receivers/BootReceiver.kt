package com.kidshealth.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kidshealth.app.services.NotificationService

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            
            // Restart notification service after boot
            val serviceIntent = Intent(context, NotificationService::class.java)
            context.startForegroundService(serviceIntent)
        }
    }
}