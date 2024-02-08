package com.mehedi.beeda_alarm

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationsChannels()
       createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Check if the device is running Android 8.0 (API 26) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            val name: CharSequence = "YourChannelName"
            val description = "Your Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("your_channel_id", name, importance)

            channel.apply {
                channel.description = description
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }



        }
    }

    private fun createNotificationsChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.reminders_notification_channel_id),
                getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = description
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            // Register the channel with the system
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

    }


}