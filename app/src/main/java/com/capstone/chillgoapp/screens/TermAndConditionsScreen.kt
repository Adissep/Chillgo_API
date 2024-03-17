package com.capstone.chillgoapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.HeadingTextComponent
import com.capstone.chillgoapp.ui.theme.PrimaryBorder

@Composable
fun TermAndConditionsScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryBorder)
            .padding(16.dp)
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_header))
    }
//    SystemBackButtonHandler {
//        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
//    }
}

@Preview
@Composable
fun TermAndConditionsScreenPreview() {
    TermAndConditionsScreen()
}