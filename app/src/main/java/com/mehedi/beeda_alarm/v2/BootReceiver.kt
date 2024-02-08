package com.mehedi.beeda_alarm.v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mehedi.beeda_alarm.SharedPrefUtil

class BootReceiver : BroadcastReceiver() {
    /*
    * restart reminders alarms when user's device reboots
    * */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            //val timeMls = SharedPrefUtil(context).getAlarmTime()
            RemindersManager.startReminder(context)
        }
    }
}