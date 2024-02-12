package com.mehedi.beeda_alarm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mehedi.beeda_alarm.R
import com.mehedi.beeda_alarm.databinding.FragmentHomeBinding
import com.mehedi.beeda_alarm.db.AlarmData
import com.mehedi.beeda_alarm.v2.RemindersManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), AlarmAdapter.HandleAlarm {

    private lateinit var binding: FragmentHomeBinding

    val viewModel: AlarmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {

            btnAddAlarm.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addAlarmFragment)
            }
        }


        alarmObserver()
        viewModel.getAllAlarm()


        return binding.root
    }

    private fun alarmObserver() {
        viewModel.responseAlarm.observe(viewLifecycleOwner) {

            binding.alarmRv.adapter = AlarmAdapter(it, this)


        }


    }

    override fun onActiveAlarm(isActive: Boolean, alarmData: AlarmData) {

        alarmData.active = isActive

        viewModel.updateAlarm(alarmData)

        if (alarmData.active ) {
            if (alarmData.alarmTimeMillis > System.currentTimeMillis()) {
                RemindersManager.startReminder(
                    binding.root.context,
                    alarmData.alarmId,
                    alarmData.alarmTimeMillis
                )
            } else {
                alarmData.active = false

                viewModel.updateAlarm(alarmData)
                Toast.makeText(
                    binding.root.context,
                    "Impossible to set Alarm \n cz,this time is past now :)",
                    Toast.LENGTH_LONG
                ).show()
            }
            Toast.makeText(
                binding.root.context,
                "${alarmData.alarmDateTime} Checked",
                Toast.LENGTH_SHORT
            ).show()
            // RemindersManager.startReminder(binding.root.context, alarmData.alarmId, alarmData.alarmTimeMillis)
        } else {
            RemindersManager.stopReminder(binding.root.context, alarmData.alarmId)

            Toast.makeText(
                binding.root.context,
                "${alarmData.alarmDateTime} Unchecked",
                Toast.LENGTH_SHORT
            ).show()
        }

//        if (isActive) {
//            Toast.makeText(
//                binding.root.context,
//                "${alarmData.alarmDateTime} Checked",
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            Toast.makeText(
//                binding.root.context,
//                "${alarmData.alarmDateTime} Unchecked",
//                Toast.LENGTH_SHORT
//            ).show()
//        }


    }


}