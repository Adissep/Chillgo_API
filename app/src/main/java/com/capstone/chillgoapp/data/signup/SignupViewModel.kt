package com.capstone.chillgoapp.data.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.capstone.chillgoapp.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignupViewModel : ViewModel() {

    private val TAG = SignupViewModel::class.simpleName

    var registUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    var toastMessage = mutableStateOf(String())

    fun onEvent(
        event: SignupUIEvent,
        onNavigateToHome: () -> Unit = {}
    ) {
        when (event) {
            is SignupUIEvent.FirstNameChanged -> {
                registUIState.value = registUIState.value.copy(
                    firstName = event.firstName
                )
                printState()
            }
//            is SignupUIEvent.LastNameChanged -> {
//                registUIState.value = registUIState.value.copy(
//                    lastName = event.lastName
//                )
//                printState()
//            }
            is SignupUIEvent.EmailChanged -> {
                registUIState.value = registUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is SignupUIEvent.PasswordChanged -> {
                registUIState.value = registUIState.value.copy(
                    password = event.password
                )
                printState()
            }
//            is SignupUIEvent.PrivacyPolicyCheckBoxClicked -> {
//                registUIState.value = registUIState.value.copy(
//                    privacyPolicyAccepted = event.status
//                )
//            }
            is SignupUIEvent.RegisterButtonClicked -> {
                signUp(
                    onNavigateToHome = onNavigateToHome
                )
            }
        }
        validateDataWithRules()
    }

    private fun signUp(
        onNavigateToHome: () -> Unit
    ) {
        Log.d(TAG, "InsideSignUp")
        printState()

        createUserFirebase(
            email = registUIState.value.email,
            password = registUIState.value.password,
            onNavigateToHome = onNavigateToHome
        )
    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validFirstName(
            fName = registUIState.value.firstName
        )
//        val lNameResult = Validator.validLastName(
//            lName = registUIState.value.lastName
//        )
        val emailResult = Validator.validEmail(
            email = registUIState.value.email
        )
        val pwdResult = Validator.validPassword(
            password = registUIState.value.password
        )
//        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
//            statusValue = registUIState.value.privacyPolicyAccepted
//        )

        Log.d(TAG, "InsideValidateDataWithRules")
        Log.d(TAG, "fNameResult = $fNameResult")
//        Log.d(TAG, "lNameResult = $lNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "pwdResult = $pwdResult")
//        Log.d(TAG, "privacyPolicyResult = $privacyPolicyResult")

        registUIState.value = registUIState.value.copy(
            firstNameError = fNameResult.status,
//            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = pwdResult.status,
//            privacyPolicyAccepted = privacyPolicyResult.status
        )

        allValidationsPassed.value =
            fNameResult.status &&
//                    lNameResult.status &&
                    emailResult.status &&
                    pwdResult.status
//                    privacyPolicyResult.status
    }

    private fun printState() {
        Log.d(TAG, "InsidePrintState")
        Log.d(TAG, registUIState.value.toString())
    }

    private fun createUserFirebase(
        email: String,
        password: String,
        onNavigateToHome: () -> Unit
    ) {
        signUpInProgress.value = true

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "InsideOnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")

                if (it.isSuccessful) {
                    toastMessage.value = "Login successful!"
                    signUpInProgress.value = false
//                    PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                    onNavigateToHome()
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "InsideOnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")

                toastMessage.value = "Login failed: ${it.localizedMessage}"
                signUpInProgress.value = false
            }
    }

    fun logout(
        onNavigateToLogin: () -> Unit
    ) {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside Sign out success")
//                PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                onNavigateToLogin()
            } else {
                Log.d(TAG, "Inside Sign out is not success")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun clearToastMessage() {
        toastMessage.value = ""
    }
}