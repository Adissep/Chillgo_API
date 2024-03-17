package com.capstone.chillgoapp.data.home

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.ui.common.LocationClient
import com.capstone.chillgoapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    private val locationClient: LocationClient,
) : ViewModel() {

    private val _topPlaceUiState: MutableStateFlow<TopPlaceUiState> =
        MutableStateFlow(
            TopPlaceUiState()
        )
    val topPlaceUiState: StateFlow<TopPlaceUiState>
        get() = _topPlaceUiState.asStateFlow()

    private val _recommendedPlaceUiState: MutableStateFlow<RecommendedPlaceUiState> =
        MutableStateFlow(
            RecommendedPlaceUiState()
        )
    val recommendedPlaceUiState: StateFlow<RecommendedPlaceUiState>
        get() = _recommendedPlaceUiState.asStateFlow()

    init {
        getTopRatingPlaces()
        getRecommendedPlaces()
    }

    fun turnOnGps(resultLauncher: ActivityResultLauncher<IntentSenderRequest>) {
        locationClient.turnOnGps(resultLauncher)
    }

    private fun getTopRatingPlaces() = viewModelScope.launch {
        placesRepository.getTopRatingPlace().collect {
            _topPlaceUiState.emit(
                when (it) {
                    is UiState.Loading -> TopPlaceUiState(loading = true)
                    is UiState.Success -> {
                        TopPlaceUiState(
                            data = if (it.data.size >= 10) it.data.take(10) else it.data
                        )
                    }

                    is UiState.Error -> TopPlaceUiState(
                        error = true,
                        message = "ERROR: ${it.errorMessage}"
                    )

                    else -> TopPlaceUiState()
                }
            )
        }
    }

    private fun getRecommendedPlaces() = viewModelScope.launch {
        placesRepository.getRecommendedPlace(10).collect {
            _recommendedPlaceUiState.emit(
                when (it) {
                    is UiState.Loading -> RecommendedPlaceUiState(loading = true)
                    is UiState.Success -> {
                        RecommendedPlaceUiState(
                            data = if (it.data.size >= 10) it.data.take(
                                10
                            ) else it.data
                        )
                    }

                    is UiState.Error -> RecommendedPlaceUiState(
                        error = true,
                        message = "ERROR: ${it.errorMessage}"
                    )

                    else -> RecommendedPlaceUiState()
                }
            )
        }
    }

}