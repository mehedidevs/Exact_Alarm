package com.mehedi.beeda_alarm.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.mehedi.beeda_alarm.R

sealed class AppPermission(
    val permissionName: String,
    val requestCode: Int,
    val deniedMessageId: Int,
    val explanationMessageId: Int
) {
    companion object {
        val permissions: List<AppPermission> by lazy {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                listOf(
                    POST_NOTIFICATIONS,
                    SCHEDULE_EXACT_ALARM
                )
            } else {
                listOf(
                    USE_EXACT_ALARM
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object POST_NOTIFICATIONS : AppPermission(
        permissionName = android.Manifest.permission.POST_NOTIFICATIONS,
        requestCode = 42,
        deniedMessageId = R.string.app_name,
        explanationMessageId = R.string.app_name
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object SCHEDULE_EXACT_ALARM : AppPermission(
        permissionName = android.Manifest.permission.SCHEDULE_EXACT_ALARM,
        requestCode = 43,
        deniedMessageId = R.string.app_name,
        explanationMessageId = R.string.app_name
    )


    data object USE_EXACT_ALARM : AppPermission(
        permissionName = android.Manifest.permission.USE_EXACT_ALARM,
        requestCode = 30,
        deniedMessageId = R.string.app_name,
        explanationMessageId = R.string.app_name
    )
}
