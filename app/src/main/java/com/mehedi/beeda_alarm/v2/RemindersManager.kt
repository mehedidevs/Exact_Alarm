package com.mehedi.beeda_alarm.v2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.mehedi.beeda_alarm.db.AlarmDao
import com.mehedi.beeda_alarm.db.AlarmData
import com.mehedi.beeda_alarm.db.AlarmDatabase
import com.mehedi.beeda_alarm.utils.SharedPrefUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


object RemindersManager {


    fun startAllReminder(alarms: List<AlarmData>, context: Context) {


        alarms.forEach { alarmData ->
            if (alarmData.active) {
                startReminder(
                    context,
                    alarmData.alarmId,
                    alarmData.alarmTimeMillis
                )

            } else {
                stopReminder(context, alarmData.alarmId)
            }
        }


    }


    fun startReminder(
        context: Context,
        reminderId: Int,
        alarmTimeMillis: Long
    ) {
        //  val alarmTimeMillis: Long = SharedPrefUtil(context).getAlarmTime()
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
        //.alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeMillis, intent)

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarmTimeMillis, intent),
            intent
        )
    }

    fun stopReminder(
        context: Context,
        reminderId: Int
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                reminderId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}