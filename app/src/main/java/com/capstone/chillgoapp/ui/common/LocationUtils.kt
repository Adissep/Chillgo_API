package com.capstone.chillgoapp.ui.common

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface LocationClient {
    fun getLocationUpdates(): Flow<Location>
    fun turnOnGps(resultLauncher: ActivityResultLauncher<IntentSenderRequest>)
    class LocationException(message: String) : Exception(message)
}

class DefaultLocationClient(
    val context: Context,
    durationChangeLocation: Long = 1000
) : LocationClient {

    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, durationChangeLocation)
            .setIntervalMillis(durationChangeLocation)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

    override fun getLocationUpdates(): Flow<Location> = callbackFlow {

        fun setLocationData(location: Location?) {
            location?.let { loc ->
                launch { send(loc) }
            }
        }


        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                for (location in p0.locations) {
                    setLocationData(location)
                }
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fun startLocationUpdate() {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        fun stopUpdateLocation() {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw LocationClient.LocationException("Missing location permission")
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location.also { loc ->
                setLocationData(loc)
            }
        }
        startLocationUpdate()
        awaitClose { stopUpdateLocation() }
    }

    override fun turnOnGps(
        resultLauncher: ActivityResultLauncher<IntentSenderRequest>,
    ) {

        val locationSetting = LocationSettingsRequest.Builder()
            .setAlwaysShow(true)
            .addLocationRequest(locationRequest)
            .build()

        val client = LocationServices.getSettingsClient(context.applicationContext)
            .checkLocationSettings(locationSetting)
        client.addOnSuccessListener {
            Log.e("GPS", "ON GPS is successes")
        }
        client.addOnFailureListener { exception ->
            when ((exception as ApiException).statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        val resolvable = exception as ResolvableApiException
                        resultLauncher.launch(
                            IntentSenderRequest.Builder(resolvable.resolution).build()
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("GPS", "ON GPS is successes")
                    }
                }

                else -> Unit
            }
        }
    }

}

fun LatLng.toLocation(): Location {
    val location = Location("")
    location.longitude = this.longitude
    location.latitude = this.latitude
    return location
}

fun Location.toLatLng(): LatLng = LatLng(
    this.latitude,
    this.longitude
)