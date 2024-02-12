package com.mehedi.beeda_alarm.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlarmData::class], version = 1, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao


}