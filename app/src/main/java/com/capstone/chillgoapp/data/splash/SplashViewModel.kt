package com.capstone.chillgoapp.data.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _hasLoggedIn: MutableStateFlow<SplashUiState> = MutableStateFlow(SplashUiState.Init)
    val hasLoggedIn = _hasLoggedIn.asStateFlow()

    init {
        checkHasLoggedIn()
    }

    private fun checkHasLoggedIn() = viewModelScope.launch {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) _hasLoggedIn.emit(SplashUiState.Success) else _hasLoggedIn.emit(
            SplashUiState.Failed
        )
    }
}