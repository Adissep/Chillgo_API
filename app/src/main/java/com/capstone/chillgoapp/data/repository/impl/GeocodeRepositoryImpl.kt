package com.capstone.chillgoapp.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.chillgoapp.data.DispatcherProvider
import com.capstone.chillgoapp.data.repository.GeocodeRepository
import com.capstone.chillgoapp.data.response.ReverseGeocodeResponse
import com.capstone.chillgoapp.data.source.GeocodeDataSource
import com.capstone.chillgoapp.ui.common.UiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GeocodeRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val geocodeApi: GeocodeDataSource,
    private val store: DataStore<Preferences>
) : GeocodeRepository {
    private val CITY_KEY = stringPreferencesKey("city_ref")

    override suspend fun reverseGeocode(latLng: LatLng): Flow<UiState<ReverseGeocodeResponse>> =
        flow {
            emit(UiState.Loading)
            when (val result = geocodeApi.reverseGeocode(latLng)) {
                is UiState.Success -> {
                    store.edit {
                        it[CITY_KEY] = result.data.city
                    }
                    emit(UiState.Success(result.data))
                }

                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Init -> Unit
                UiState.Loading -> emit(UiState.Loading)
            }
        }.flowOn(dispatcher.io())
}