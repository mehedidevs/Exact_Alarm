package com.mehedi.beeda_alarm.v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.room.Room
import com.mehedi.beeda_alarm.db.AlarmDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class BootReceiver : BroadcastReceiver() {
    /*
    * restart reminders alarms when user's device reboots
    * */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            //val timeMls = SharedPrefUtil(context).getAlarmTime()

         runBlocking {
             val alarmDao = Room.databaseBuilder(context, AlarmDatabase::class.java, "alarm_db").build()
                 .getAlarmDao()

             RemindersManager.startAllReminder(alarmDao.getAllAlarm(),context)

             Log.d("TAG", "onReceive:${alarmDao.getAllAlarm()} ")

         }


        }
    }
}