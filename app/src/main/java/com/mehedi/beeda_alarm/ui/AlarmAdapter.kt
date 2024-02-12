package com.mehedi.beeda_alarm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mehedi.beeda_alarm.databinding.ItemAlarmBinding
import com.mehedi.beeda_alarm.db.AlarmData
import com.mehedi.beeda_alarm.v2.RemindersManager

class AlarmAdapter(
    private val alarmDataList: List<AlarmData>,
    val handleAlarm: HandleAlarm
) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {


    interface HandleAlarm {

        fun onActiveAlarm(isActive: Boolean, alarmData: AlarmData)

    }


    inner class AlarmViewHolder(val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alarmData: AlarmData) {

            binding.apply {

                if (alarmData.active) {
                    if (alarmData.alarmTimeMillis > System.currentTimeMillis()) {
                        RemindersManager.startReminder(
                            binding.root.context,
                            alarmData.alarmId,
                            alarmData.alarmTimeMillis
                        )
                    }
                } else {
                    RemindersManager.stopReminder(binding.root.context, alarmData.alarmId)
                    switchAlarm.isChecked = false
                }

                txtAlarmTime.text = alarmData.alarmDateTime
                txtAlarmNote.text = alarmData.note
                switchAlarm.isChecked = alarmData.active

                switchAlarm.setOnCheckedChangeListener { _, isChecked ->
                    handleAlarm.onActiveAlarm(isChecked, alarmData)
                }


            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            ItemAlarmBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return alarmDataList.size
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarmDataList[position])
    }


}