package com.capstone.chillgoapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.ButtonComponent
import com.capstone.chillgoapp.components.ClickableLoginTextComponent
import com.capstone.chillgoapp.components.DividerTextComponent
import com.capstone.chillgoapp.components.HeadingTextComponent
import com.capstone.chillgoapp.components.LogoTextComponent
import com.capstone.chillgoapp.components.MyTextFieldComponent
import com.capstone.chillgoapp.components.NormalTextComponent
import com.capstone.chillgoapp.components.PasswordTextFieldComponent
import com.capstone.chillgoapp.components.SocialMediaButton
import com.capstone.chillgoapp.components.UnderlineNormalTextComponent
import com.capstone.chillgoapp.data.login.LoginUIEvent
import com.capstone.chillgoapp.data.login.LoginViewModel
import com.capstone.chillgoapp.ui.theme.PrimaryBody

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
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
                LogoTextComponent(value = stringResource(id = R.string.chill_go_app))
                Spacer(modifier = Modifier.height(20.dp))
                HeadingTextComponent(value = stringResource(id = R.string.welcome_back))
                NormalTextComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = stringResource(id = R.string.log_in_to_your_setup_account)
                )

                Spacer(modifier = Modifier.height(40.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    leadingIcon = null,
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )
                Spacer(modifier = Modifier.height(24.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    leadingIcon = null,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    NormalTextComponent(value = stringResource(id = R.string.forgot_password))
                    Spacer(modifier = Modifier.width(4.dp))
                    UnderlineNormalTextComponent(value = stringResource(id = R.string.reset_password))
                }

                Spacer(modifier = Modifier.height(24.dp))

                val context = LocalContext.current
                val toastMessage by loginViewModel.toastMessage
                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        showToast(context, toastMessage)
                        loginViewModel.clearToastMessage()
                    }
                }

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(
                            LoginUIEvent.LoginButtonClicked,
                            onNavigateToHome = onNavigateToHome
                        )
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
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
                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
//                    PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                    onNavigateToSignUp()
                })
            }
        }

        if (loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

fun showToast(context: Context, toastMessage: String?) {
    if (!toastMessage.isNullOrEmpty()) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}