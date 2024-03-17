package com.capstone.chillgoapp.data.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.chillgoapp.data.paging.UmkmPagingSource
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.response.UmkmResponse
import com.capstone.chillgoapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTravelViewModel @Inject constructor(
    private val repository: PlacesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val placeId: String = savedStateHandle.get<String>("placeId").orEmpty()
    private val cityParams: String = savedStateHandle.get<String>("city").orEmpty()

    private val _uiState: MutableStateFlow<DetailTravelUiState> =
        MutableStateFlow(DetailTravelUiState())
    val uiState: StateFlow<DetailTravelUiState>
        get() = _uiState.asStateFlow()

    val umkmState =
        Pager(
            PagingConfig(pageSize = Int.MAX_VALUE, enablePlaceholders = false),
            pagingSourceFactory = {
                UmkmPagingSource(
                    repository = repository,
                    filter = cityParams
                )
            }).flow.cachedIn(viewModelScope)

    init {
        Log.e("PLACEID",placeId)
        getPlaceById(placeId)
    }

    private fun getPlaceById(placeId: String) = viewModelScope.launch {
        repository.getDetailPlaceById(placeId).onEach {
            _uiState.emit(
                when (it) {
                    is UiState.Loading -> DetailTravelUiState(loading = true)
                    is UiState.Success -> {
                        DetailTravelUiState(
                            data = it.data
                        )
                    }

                    is UiState.Error -> DetailTravelUiState(
                        error = true,
                        message = "ERROR: ${it.errorMessage}"
                    )

                    else -> DetailTravelUiState()
                }
            )
        }.collect()
    }

}