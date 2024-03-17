package com.capstone.chillgoapp.data.source

import com.capstone.chillgoapp.data.response.ReverseGeocodeResponse
import com.capstone.chillgoapp.data.retrofit.GeocodingServices
import com.capstone.chillgoapp.data.safeApiCall
import com.capstone.chillgoapp.ui.common.UiState
import com.google.android.gms.maps.model.LatLng

interface GeocodeDataSource {
    suspend fun reverseGeocode(latLng: LatLng): UiState<ReverseGeocodeResponse>
}

class GeocodeDataSourceImpl(
    private val apiServices: GeocodingServices
) : GeocodeDataSource {
    override suspend fun reverseGeocode(latLng: LatLng) = safeApiCall {
        apiServices.reverseGeocoding(latLng.latitude, latLng.longitude)
    }

}