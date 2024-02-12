package com.mehedi.beeda_alarm.di

import android.content.Context
import androidx.room.Room
import com.mehedi.beeda_alarm.db.AlarmDao
import com.mehedi.beeda_alarm.db.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(context, AlarmDatabase::class.java, "alarm_db").build()
    }

    @Provides
    @Singleton
    fun providesDao(alarmDatabase: AlarmDatabase): AlarmDao {
        return alarmDatabase.getAlarmDao()
    }

}