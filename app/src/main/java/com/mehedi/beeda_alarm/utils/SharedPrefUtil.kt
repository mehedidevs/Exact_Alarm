package com.mehedi.beeda_alarm.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences =
            context.getSharedPreferences(context.packageName + "_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setAlarmTime(alarmTimeMls: Long) {
        editor.putLong(KEY_USER_ID, alarmTimeMls)?.commit()
    }

    fun getAlarmTime(): Long {
        return sharedPreferences.getLong(
            KEY_USER_ID, 0
        )
    }


    companion object {
        //pref keys
        private const val KEY_USER_ID = "KEY_USER_ID"
    }
}