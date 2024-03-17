package com.capstone.chillgoapp.screens

//import com.capstone.chillgoapp.navigation.PostOfficeAppRouter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.ButtonComponent
import com.capstone.chillgoapp.components.ClickableLoginTextComponent
import com.capstone.chillgoapp.components.DividerTextComponent
import com.capstone.chillgoapp.components.LogoTextComponent
import com.capstone.chillgoapp.components.MyTextFieldComponent
import com.capstone.chillgoapp.components.NormalTextComponent
import com.capstone.chillgoapp.components.PasswordTextFieldComponent
import com.capstone.chillgoapp.components.SocialMediaButton
import com.capstone.chillgoapp.data.signup.SignupUIEvent
import com.capstone.chillgoapp.data.signup.SignupViewModel
import com.capstone.chillgoapp.ui.theme.PrimaryBody

@Composable
fun SignUpScreen(
    signupViewModel: SignupViewModel = viewModel(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToTerm: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBody)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryBody)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier.padding(3.dp)
                ) {
                    painterResource(id = R.drawable.identity_24)
                    LogoTextComponent(value = stringResource(id = R.string.chill_go_app))
                }
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextComponent(
                    value = stringResource(id = R.string.setup_your_account),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(18.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    leadingIcon = null,
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = signupViewModel.registUIState.value.firstNameError
                )
                Spacer(modifier = Modifier.height(24.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    leadingIcon = null,
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = signupViewModel.registUIState.value.emailError
                )
                Spacer(modifier = Modifier.height(24.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    leadingIcon = null,
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(24.dp))

                val context = LocalContext.current
                val toastMessage by signupViewModel.toastMessage
                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        showToast(context, toastMessage)
                        signupViewModel.clearToastMessage()
                    }
                }

                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        signupViewModel.onEvent(
                            SignupUIEvent.RegisterButtonClicked,
                            onNavigateToHome = onNavigateToHome
                        )
                    },
                    isEnabled = signupViewModel.allValidationsPassed.value
                )

                DividerTextComponent()
                SocialMediaButton(
                    label = stringResource(id = R.string.continue_google),
                    leadingIcon = {
                        Image(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Google"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SocialMediaButton(
                    label = stringResource(id = R.string.continue_facebook),
                    leadingIcon = {
                        Image(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.fb_logo),
                            contentDescription = "FB"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(42.dp))
                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
//                    PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                    onNavigateToLogin()
                })
                Spacer(modifier = Modifier.height(24.dp))
                NormalTextComponent(
                    modifier = Modifier.clickable {
//                        PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                        onNavigateToTerm()
                    },
                    value = stringResource(id = R.string.terms_conditions),
                    fontSize = 12.sp
                )
            }
        }

        if (signupViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}