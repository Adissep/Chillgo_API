package com.capstone.chillgoapp.data.repository

import com.capstone.chillgoapp.data.response.ReverseGeocodeResponse
import com.capstone.chillgoapp.ui.common.UiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface GeocodeRepository {
    suspend fun reverseGeocode(
        latLng: LatLng,
    ): Flow<UiState<ReverseGeocodeResponse>>
}