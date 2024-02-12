package com.mehedi.beeda_alarm.v1

import android.R
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import com.mehedi.beeda_alarm.ui.MainActivity

class AlarmReceiver_v1 : BroadcastReceiver() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm! Time to wake up!", Toast.LENGTH_LONG).show()
        when (intent?.action) {
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                Toast.makeText(context, "RECEIVED ALARM PERMISSION", Toast.LENGTH_LONG).show()
            }

            "from alarm" -> {

                adjustAudioSettings(context)

                Toast.makeText(context, "ALARM FIRED", Toast.LENGTH_LONG).show()

                // Create an explicit intent for an Activity in your app
                // This is just a placeholder, replace it with your actual intent

                // Create an explicit intent for an Activity in your app
                // This is just a placeholder, replace it with your actual intent
                val resultIntent = Intent(context, MainActivity::class.java)
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                // Create a PendingIntent

                // Create a PendingIntent
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )

                // Create the Notification

                // Create the Notification
                val builder: Notification.Builder =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Notification.Builder(context, "your_channel_id")
                            .setSmallIcon(R.drawable.menu_frame)
                            .setContentTitle("Your Notification Title")
                            .setContentText("Your Notification Text")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                    } else {
                        Notification.Builder(context)
                            .setSmallIcon(R.drawable.menu_frame)
                            .setContentTitle("Your Notification Title")
                            .setContentText("Your Notification Text")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                    } // Remove the notification from the status bar when clicked


                // Show the Notification

                // Show the Notification
                val notificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(1, builder.build())

                playRingtone(context)
            }
        }


    }

    private fun adjustAudioSettings(context: Context?) {
        val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Ensure that the alarm will override silent mode
        audioManager.mode = AudioManager.MODE_RINGTONE

        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val percent = 0.7f
        val seventyVolume = (maxVolume * percent).toInt()


        // Set the alarm stream volume to maximum
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            seventyVolume,
            0
        )

        // Maximize the alarm volume
        // audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_RAISE, 0)
    }

    private fun playRingtone(context: Context?) {
        try {
            // Set the alarm ringtone URI, replace it with your desired ringtone
            val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            // Create a new MediaPlayer instance
            mediaPlayer = MediaPlayer.create(context, ringtoneUri)

            // Ensure that the media player is not null and is playing
            mediaPlayer?.apply {
                // Set looping to true if you want the ringtone to repeat until stopped
                isLooping = true
                // Start playing the ringtone
                // Set audio stream type to alarm
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )

                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exceptions
        }
    }

}