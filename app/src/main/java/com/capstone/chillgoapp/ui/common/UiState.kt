package com.capstone.chillgoapp.ui.common

const val SOCKET_TIME_OUT_EXCEPTION = "Request timed out while trying to connect. Please ensure you have a strong signal and try again."
const val UNKNOWN_NETWORK_EXCEPTION = "An unexpected error has occurred. Please check your network connection and try again."
const val CONNECT_EXCEPTION = "Could not connect to the server. Please check your internet connection and try again."
const val UNKNOWN_HOST_EXCEPTION = "Couldn't connect to the server at the moment. Please try again in a few minutes."
const val UNAUTHORIZED = "You don't have permission to use this resource"
const val INTERNAL_SERVER_ERROR = "You don't have permission to use this resource"

sealed class UiState<out T> {

    object Init: UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()

    data class Error(val errorMessage: String) : UiState<Nothing>()
}