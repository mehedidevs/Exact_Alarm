package com.mehedi.beeda_alarm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmData(
    @PrimaryKey(autoGenerate = true)
    val alarmId: Int = 0,
    val alarmDateTime: String,
    val alarmTimeMillis: Long,
    val note: String,
    var active: Boolean = true
)
