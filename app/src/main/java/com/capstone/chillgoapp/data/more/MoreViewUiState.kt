package com.capstone.chillgoapp.data.more

import com.capstone.chillgoapp.data.response.PlaceResponse

data class MoreViewUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val data: List<PlaceResponse> = listOf(),
)