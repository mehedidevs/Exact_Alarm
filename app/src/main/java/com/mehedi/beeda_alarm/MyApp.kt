package com.mehedi.beeda_alarm

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationsChannels()

    }


    private fun createNotificationsChannels() {

        val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.oh_boy)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val att = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            val channel = NotificationChannel(
                getString(R.string.reminders_notification_channel_id),
                getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                setSound(soundUri, att)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                enableLights(true)
                lightColor = Color.GRAY
                enableVibration(true)


            }
            // Register the channel with the system
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

    }


}