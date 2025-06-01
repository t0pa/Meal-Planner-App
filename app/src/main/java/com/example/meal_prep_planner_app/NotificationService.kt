package com.example.meal_prep_planner_app

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import android.app.PendingIntent
import android.util.Log

class NotificationService : Service() {
    private val channelId = "meal_prep_channel"
    private val notificationId = 1


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("NotificationService", "Service started with action: ${intent?.action}")
        when(intent?.action) {
            "SHOW" -> showNotification()
            "HIDE" -> stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Meal Prep Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for meal prep reminders"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        Log.d("NotificationService", "Attempting to show notification")
        // Create intent that will open your app when notification is tapped
        val openIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Meal Prep Reminder")
            .setContentText("Don't forget to prepare your meals for today!")
            .setSmallIcon(R.drawable.meal_prep_logo_green_removebg_preview) // CHANGED TO PROPER ICON
            .setContentIntent(pendingIntent) // ADDED THIS LINE
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(notificationId, notification)
    }
}