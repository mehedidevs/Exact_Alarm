package com.mehedi.beeda_alarm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmDao {

    @Insert
    suspend fun addAlarm(alarmData: AlarmData)

    @Update
    suspend fun updateAlarm(alarmData: AlarmData)
    @Query("SELECT * FROM AlarmData")
    suspend fun getAllAlarm(): List<AlarmData>



}