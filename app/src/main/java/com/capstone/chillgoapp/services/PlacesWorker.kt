package com.capstone.chillgoapp.services

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.capstone.chillgoapp.data.repository.GeocodeRepository
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.ui.common.LocationClient
import com.capstone.chillgoapp.ui.common.UiState
import com.capstone.chillgoapp.ui.common.toLatLng
import com.google.android.gms.maps.model.LatLng
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


private val CITY_KEY = stringPreferencesKey("city_ref")

@HiltWorker
class PlacesWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val placesRepository: PlacesRepository,
    private val geocodeRepository: GeocodeRepository,
    private val store: DataStore<Preferences>,
    private val locationClient: LocationClient,
) : CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        try {
            Log.e("CITY WORKER", "JALAN NIH")
            val geoManager = GeofenceManager(appContext)
            Log.e("CITY WORKER", "SSSSSSSSSS NIH")

            val locationUpdate = locationClient.getLocationUpdates().first()
            geocodeRepository.reverseGeocode(locationUpdate.toLatLng()).collect()


            val newCity = store.data.first()[CITY_KEY] ?: "bandung"

            Log.e("CITY WORKER", "$newCity NIH")

            placesRepository.getPlaceBasedOnCityAndSaveToLocal(newCity).collect {
                when (it) {
                    is UiState.Error -> {
                        Log.e("WORKER_ERROR", it.errorMessage)
                    }

                    is UiState.Success -> {
                        Log.e("WORKER_SUCCESS", it.data.toString())
                        it.data.forEach { p ->
                            geoManager.addGeofence(
                                p.id.toString(),
                                location = LatLng(
                                    p.latitude,
                                    p.longitude
                                ),
                                radiusInMeters = 500.0F,
                            )
                        }
                        geoManager.registerGeofence()
                    }

                    else -> Unit
                }
            }

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }

    }
}