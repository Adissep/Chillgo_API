package com.capstone.chillgoapp.data.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chillgoapp.ui.common.StringUtility.capital
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val TAG = this::class.simpleName

    private val _userState = MutableStateFlow(ProfileStateUi())
    val userState = _userState.asStateFlow()


    init {
        getUser()
    }


    private fun getUser() = viewModelScope.launch {
        _userState.emit(ProfileStateUi(loading = true))
        val pattern = "^(.+)@".toRegex()
        try {
            firebaseAuth.currentUser?.let {
                val matchResult = pattern.find(it.email ?: "default@gmail.com")
                val userName = matchResult?.groups?.get(1)?.value
                val capitalize =
                    userName?.capital() ?: ""
                _userState.emit(
                    ProfileStateUi(
                        userName = if (it.displayName.isNullOrBlank()) capitalize else it.displayName!!,
                        email = it.email ?: ""
                    )
                )
            }
        } catch (e: Exception) {
            _userState.emit(
                ProfileStateUi(
                    error = true,
                    message = e.message ?: ""
                )
            )
        }
    }

    fun logout(
        onNavigateToLogin: () -> Unit
    ) {
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside Sign out success")
                onNavigateToLogin()
            } else {
                Log.d(TAG, "Inside Sign out is not success")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }
}