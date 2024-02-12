package com.mehedi.beeda_alarm.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mehedi.beeda_alarm.utils.SharedPrefUtil
import com.mehedi.beeda_alarm.databinding.FragmentAddAlarmBinding
import com.mehedi.beeda_alarm.db.AlarmData
import com.mehedi.beeda_alarm.v2.RemindersManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddAlarmFragment : Fragment() {

    val viewModel: AlarmViewModel by viewModels()


    private lateinit var binding: FragmentAddAlarmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddAlarmBinding.inflate(inflater, container, false)

        binding.setAlarmButton.setOnClickListener {


            setAlarm()
        }

        binding.cancelAlarmButton.setOnClickListener {
            cancelAlarm()
        }

        binding.btnNotificationSetting.setOnClickListener {
            showPermissionDialog()
        }



        return binding.root
    }

    private fun cancelAlarm() {
        RemindersManager.stopReminder(requireContext(), 0)
        Toast.makeText(requireContext(), "Notification Cancel ", Toast.LENGTH_LONG).show()
    }

    private fun setAlarm() {
        val hour =
            binding.timePicker.hour
        val minute =
            binding.timePicker.minute

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        //  SharedPrefUtil(requireContext()).setAlarmTime(calendar.timeInMillis)
        // RemindersManager.startReminder(requireContext(), 0)
        Toast.makeText(context, "Notification Set ", Toast.LENGTH_LONG).show()

        val alarmData = AlarmData(
            0,
            "${calendar.get(Calendar.HOUR)} : ${calendar.get(Calendar.MINUTE)}",
            calendar.timeInMillis,
            "it is time to assar pray!",
            true
        )
        viewModel.addAlarm(alarmData)

    }

    private fun showPermissionDialog() {
        // You can customize the dialog to explain the need for the permission
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Required")
            .setMessage("Please enable the SCHEDULE_EXACT_ALARM permission in app settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Handle the case where the user cancels
            }
            .show()
    }

    private fun openAppSettings() {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)

//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//
//            if (!alarmManager.canScheduleExactAlarms()) {
//                Intent().also { intent ->
//                    intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
//                    startActivity(intent)
//                }
//            }
//
//        } else {
//            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//            val uri = Uri.fromParts("package", packageName, null)
//            intent.data = uri
//            startActivity(intent)
//        }


    }


}