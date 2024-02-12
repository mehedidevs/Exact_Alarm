package com.mehedi.beeda_alarm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedi.beeda_alarm.db.AlarmData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val repository: AlarmRepository) : ViewModel() {

    private val _responseAlarm = MutableLiveData<List<AlarmData>>()
    val responseAlarm: LiveData<List<AlarmData>>
        get() = _responseAlarm


    fun addAlarm(alarmData: AlarmData) {
        viewModelScope.launch {
            repository.addAlarm(alarmData)
        }
        getAllAlarm()

    }


    fun updateAlarm(alarmData: AlarmData) {
        viewModelScope.launch {
            repository.updateAlarm(alarmData)
        }
    }


    fun getAllAlarm() {
        viewModelScope.launch {
            _responseAlarm.postValue(repository.getAllAlarm())
        }
    }


}