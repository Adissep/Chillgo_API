package com.capstone.chillgoapp.ui.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat

class PermissionUtils(private val context: Context) {
    companion object {
        val listPermission = arrayOf(
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        const val PERMISSION_CODE_REQUEST = 321

    }

    fun listPermission(): Array<String> {
        val list = listPermission.toMutableList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            list.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        return list.toTypedArray()
    }

    /**
     * check if app already have permission
     * @return Boolean
     * **/
    fun hasPermission(): Boolean {
        val hasPermission = arrayListOf<Boolean>()
        listPermission.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (it == Manifest.permission.ACCESS_FINE_LOCATION ||
                    it == Manifest.permission.ACCESS_COARSE_LOCATION
                ) {
                    return@forEach
                }
            }
            hasPermission.add(
                ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            )
            Log.e(
                "permission",
                "$it -> ${
                    ActivityCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                }"
            )
        }
        return !hasPermission.contains(false)
    }
}