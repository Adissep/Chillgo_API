package com.capstone.chillgoapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.data.splash.SplashUiState
import com.capstone.chillgoapp.data.splash.SplashViewModel
import com.capstone.chillgoapp.ui.theme.PrimaryMain
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    gotoLogin: () -> Unit = {},
    gotoHome: () -> Unit = {},
    viewModel: SplashViewModel = hiltViewModel(),
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(PrimaryMain),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0.0f)
    }

    val hasLoggedIn by viewModel.hasLoggedIn.collectAsState(initial = SplashUiState.Init)

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )
        delay(1500)
        when (hasLoggedIn) {
            SplashUiState.Success -> gotoHome()
            SplashUiState.Failed -> gotoLogin()
            else -> Unit
        }
    }

    Image(
        painter = painterResource(id = R.drawable.logo_white),
        contentDescription = "",
        modifier = Modifier
            .width(125.dp)
            .height(109.dp),
        contentScale = ContentScale.Fit
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "ChillGo App",
        style = TextStyle(
            fontSize = 30.sp,
            fontFamily = FontFamily(Font(R.font.calistoga_regular))
        ),
        color = Color.White,
    )
}