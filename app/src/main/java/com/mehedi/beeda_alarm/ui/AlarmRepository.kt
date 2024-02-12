package com.mehedi.beeda_alarm.ui

import com.mehedi.beeda_alarm.db.AlarmDao
import com.mehedi.beeda_alarm.db.AlarmData
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val dao: AlarmDao) {

    suspend fun addAlarm(alarmData: AlarmData) {
        dao.addAlarm(alarmData)
    }

    suspend fun updateAlarm(alarmData: AlarmData) {
        dao.updateAlarm(alarmData)
    }


    suspend fun getAllAlarm(): List<AlarmData> {
        return dao.getAllAlarm()
    }


}