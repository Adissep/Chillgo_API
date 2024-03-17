package com.capstone.chillgoapp.services

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.data.DispatcherProvider
import com.capstone.chillgoapp.data.local.PlaceDao
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class GeofencingReceiver : BroadcastReceiver() {

    @Inject
    lateinit var placeDao: PlaceDao

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        if (geofencingEvent?.hasError() == true) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e(this::class.simpleName, errorMessage)
            return
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition

        Log.e("RECEIVER", geofenceTransition.toString())

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL
        ) {

            val triggeringGeofence = geofencingEvent.triggeringGeofences
            val id = triggeringGeofence?.get(0)?.requestId

            Log.e("RECEIVER", id.toString())

            CoroutineScope(dispatcherProvider.io()).launch {
                val data = placeDao.getById(id?.toInt())
                Log.e("RECEIVER", data.toString())
                showNotification(
                    context,
                    geofenceTransition,
                    data?.place_name ?: ""
                )
            }

            Log.i(this::class.simpleName, "Success")
        } else {
            Log.e(
                this::class.simpleName, "Invalid"
            )
        }

    }


    private fun showNotification(
        context: Context?,
        geofenceTransition: Int?,
        placeName: String,
    ) {

        val contentText =
            when (geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> "Anda telah memasuki wisata $placeName. Segera cek wisata terdekat di aplikasi."
                Geofence.GEOFENCE_TRANSITION_EXIT -> "Sayang banget, anda telah keluar dari wisata $placeName"
                else -> ""
            }

        val titleText = when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> "Ada $placeName didekat Anda"
            Geofence.GEOFENCE_TRANSITION_EXIT -> "Yah, Anda meninggalkan wisata $placeName,"
            else -> ""
        }

        context?.let {
            val builder = NotificationCompat.Builder(context, "GEOFANCE")
                .setSmallIcon(R.drawable.logo_app)
                .setAutoCancel(true)
                .setContentText(contentText)
                .setContentTitle(titleText)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPublicVersion(
                    NotificationCompat.Builder(context, "GEOFANCE")
                        .setContentTitle(titleText)
                        .setContentText("Unlock to see the message.")
                        .build()
                )
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            with(notificationManager) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.i(this::class.simpleName, "PERMISSION ERROR")
                    }
                }
                notify(System.currentTimeMillis().toInt(), builder)
            }
        }
    }
}