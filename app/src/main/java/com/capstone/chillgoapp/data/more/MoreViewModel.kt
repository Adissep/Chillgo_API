package com.capstone.chillgoapp.data.more

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.capstone.chillgoapp.data.home.RecommendedPlaceUiState
import com.capstone.chillgoapp.data.home.TopPlaceUiState
import com.capstone.chillgoapp.data.paging.PlacesPagingSource
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.ui.common.TypePlace
import com.capstone.chillgoapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val repository: PlacesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val cityParams = when (val result = savedStateHandle.get<String>("city").orEmpty()) {
        TypePlace.top.name, TypePlace.recommended.name -> "Bandung"
        else -> result
    }

    var type = mutableStateOf(
        when (cityParams) {
            TypePlace.top.name, TypePlace.recommended.name -> cityParams
            TypePlace.all.name -> ""
            else -> TypePlace.city.name
        }
    )

    var moreState = Pager(
        PagingConfig(pageSize = Int.MAX_VALUE, enablePlaceholders = false), pagingSourceFactory = {
            PlacesPagingSource(
                repository = repository,
                filter = cityParams
            )
        }).flow.map { pagingData ->
        pagingData.filter { d ->
            d.city.lowercase().contains(
                if (cityParams == TypePlace.all.name || cityParams == TypePlace.city.name) "" else cityParams
            )
        }
    }.cachedIn(viewModelScope)


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
        getPlacesByCity(type.value)
    }

    fun getPlacesByCity(typePlace: String) {
        if (typePlace == TypePlace.top.name) {
            getTopPlace()
            return
        }
        if (typePlace == TypePlace.recommended.name) {
            getRecommendedPlace()
            return
        }
        return
    }

    private fun getTopPlace() = viewModelScope.launch {
        repository.getTopRatingPlace().collect {
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

                    else -> TopPlaceUiState(loading = false)
                }
            )
        }
    }

    private fun getRecommendedPlace() = viewModelScope.launch {
        repository.getRecommendedPlace(Int.MAX_VALUE).collect {
            _recommendedPlaceUiState.emit(
                when (it) {
                    is UiState.Loading -> RecommendedPlaceUiState(loading = true)
                    is UiState.Success -> {
                        RecommendedPlaceUiState(
                            data = it.data
                        )
                    }

                    is UiState.Error -> RecommendedPlaceUiState(
                        error = true,
                        message = "ERROR: ${it.errorMessage}"
                    )

                    else -> RecommendedPlaceUiState(loading = false)
                }
            )
        }
    }
}