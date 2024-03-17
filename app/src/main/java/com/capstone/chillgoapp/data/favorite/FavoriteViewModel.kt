package com.capstone.chillgoapp.data.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chillgoapp.data.TravelRepository
import com.capstone.chillgoapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: TravelRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getAddedOrderFavorites() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderTravels()
                .collect { orderTravel ->
                    val totalRequiredPoint =
                        orderTravel.sumOf { it.travel.requiredPoint * it.count }
                    _uiState.value = UiState.Success(FavoriteState(orderTravel, totalRequiredPoint))
                }
        }
    }

    fun updateOrderTravel(travelId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderTravel(travelId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderFavorites()
                    }
                }
        }
    }
}