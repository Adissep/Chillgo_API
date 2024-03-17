package com.capstone.chillgoapp.data.splash

import com.capstone.chillgoapp.data.login.LoginUIEvent

sealed class SplashUiState {
    object Init : SplashUiState()
    object Success : SplashUiState()
    object Failed : SplashUiState()
}