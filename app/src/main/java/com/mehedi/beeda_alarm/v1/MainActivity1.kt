package com.mehedi.beeda_alarm.v1

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mehedi.beeda_alarm.R
import com.mehedi.beeda_alarm.v1.AlarmReceiver_v1
import com.mehedi.beeda_alarm.v2.RemindersManager
import java.util.Calendar

class MainActivity1 : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var setAlarmButton: Button
    private lateinit var textView: TextView

    lateinit var alarmManager: AlarmManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationsChannels()



//        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
        timePicker = findViewById(R.id.timePicker)
        setAlarmButton = findViewById(R.id.setAlarmButton)
        textView = findViewById(R.id.textView)

        setAlarmButton.setOnClickListener {
            setAlarm()
        }
    }

    private fun createNotificationsChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.reminders_notification_channel_id),
                getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }

    }


    private fun setAlarm() {
        //    showPermissionDialog()

        val hour =
            timePicker.hour

        val minute =
            timePicker.minute

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // check and set alarms
            val alarmManager: AlarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager
            when {
                alarmManager.canScheduleExactAlarms() -> {
                    // Use to showcase the UX improvements in Android12
                    Toast.makeText(this, getString(R.string.alarm_toast_message), Toast.LENGTH_LONG)
                        .show()
                    val alarmIntent =
                        Intent(applicationContext, AlarmReceiver_v1::class.java).let {
                            it.apply { action = getString(R.string.alarm_intent_action) }
                            PendingIntent.getBroadcast(
                                applicationContext,
                                0,
                                it,
                                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                            )
                        }
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        alarmIntent
                    )
                }

                else -> {
                    // go to exact alarm settings
                    Intent().apply {
                        action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    }.also {
                        startActivity(it)
                    }
                }
            }
        }


//        val intent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//
//        textView.text = "Alarm set for $hour:$minute"
    }


    private fun showPermissionDialog() {
        // You can customize the dialog to explain the need for the permission
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("Please enable the SCHEDULE_EXACT_ALARM permission in app settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Handle the case where the user cancels
            }
            .show()
    }

    private fun openAppSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (!alarmManager.canScheduleExactAlarms()) {
                Intent().also { intent ->
                    intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    startActivity(intent)
                }
            }

        } else {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }


    }


}

