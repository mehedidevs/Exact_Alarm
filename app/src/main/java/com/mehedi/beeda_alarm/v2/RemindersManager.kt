package com.mehedi.beeda_alarm.v2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.mehedi.beeda_alarm.SharedPrefUtil


object RemindersManager {
    private const val REMINDER_NOTIFICATION_REQUEST_CODE = 123

    fun startReminder(
        context: Context,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmTimeMillis: Long = SharedPrefUtil(context).getAlarmTime()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context.applicationContext, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(
                    context.applicationContext,
                    reminderId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarmTimeMillis, intent),
            intent
        )
    }

    fun stopReminder(
        context: Context,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                reminderId,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}