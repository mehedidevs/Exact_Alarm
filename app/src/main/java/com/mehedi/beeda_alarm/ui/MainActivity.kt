package com.mehedi.beeda_alarm.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mehedi.beeda_alarm.utils.PermissionUtils
import com.mehedi.beeda_alarm.R
import com.mehedi.beeda_alarm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                it.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false) -> {}
                it.getOrDefault(Manifest.permission.SCHEDULE_EXACT_ALARM, false) -> {}
                else -> PermissionUtils.showPermissionSettings(
                    binding.root,
                    this,
                    getString(R.string.rationale_notification)
                )
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//            setShowWhenLocked(true)
//            setTurnScreenOn(true)
//            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
//            keyguardManager.requestDismissKeyguard(this, null)
//        } else {
//            this.window.addFlags(
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
//                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
//                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//            )
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestPermissions()


    }


    private fun requestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                )
            )
        }
    }


}

