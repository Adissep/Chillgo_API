package com.capstone.chillgoapp.data.onboard

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
) : ViewModel() {

    fun saveCategory(context: Context, category: String, callback: () -> Unit) =
        viewModelScope.launch {
            when (val result = placesRepository.saveCategoryPlaces(category)) {
                is UiState.Success -> callback()
                is UiState.Error -> Toast.makeText(context, result.errorMessage, Toast.LENGTH_LONG)
                    .show()

                UiState.Init -> Unit
                UiState.Loading -> Unit
            }
        }


}