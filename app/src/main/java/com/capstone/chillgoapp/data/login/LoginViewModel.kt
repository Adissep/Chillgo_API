package com.capstone.chillgoapp.data.login

//import com.capstone.chillgoapp.navigation.PostOfficeAppRouter
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.capstone.chillgoapp.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    var toastMessage = mutableStateOf(String())

    fun onEvent(
        event: LoginUIEvent,
        onNavigateToHome: () -> Unit = {}
    ) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login(onNavigateToHome = onNavigateToHome)
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validEmail(
            email = loginUIState.value.email
        )
        val pwdResult = Validator.validPassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = pwdResult.status
        )

        allValidationsPassed.value = emailResult.status && pwdResult.status
    }

    private fun login(
        onNavigateToHome: () -> Unit
    ) {
        loginInProgress.value = true

        val email = loginUIState.value.email
        val pwd = loginUIState.value.password

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, pwd)
            .addOnCompleteListener {
                Log.d(TAG, "InsideLoginSucces")
                Log.d(TAG, "${it.isSuccessful}")

                if (it.isSuccessful) {
                    toastMessage.value = "Login successful!"
                    loginInProgress.value = false
//                    PostOfficeAppRouter.navigateTo(Screen.Dashboard)
                    onNavigateToHome()
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "InsideOnFailureListener")
                Log.d(TAG, "Exception = ${it.localizedMessage}")

                toastMessage.value = "Login failed: ${it.localizedMessage}"
                loginInProgress.value = false
            }
    }

    fun clearToastMessage() {
        toastMessage.value = ""
    }
}