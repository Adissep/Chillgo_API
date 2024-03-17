package com.capstone.chillgoapp.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.DividerTextComponent
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryBorder
import com.capstone.chillgoapp.ui.theme.PrimaryMain
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OnBoardingSecondScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToSignup: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBody)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .width(157.dp)
                .height(125.dp)
                .padding(16.dp),
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(17.dp))
        Text(
            text = "Please do Sign In or Sign Up!",
            fontSize = 16.sp,
            color = PrimaryMain,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.height(146.dp))
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxWidth()
        ) { _ ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.width(105.dp),
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Spacer(modifier = Modifier.height(108.dp))
        BottomSection(index = pageState.currentPage) {
            if (pageState.currentPage + 1 < 2) scope.launch {
                pageState.scrollToPage(pageState.currentPage + 1)
            }
        }
        Spacer(modifier = Modifier.height(47.dp))
        Row(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                border = BorderStroke(width = 1.dp, color = PrimaryMain),
                onClick = { onNavigateToLogin() }) {
                Text(
                    text = "Sign In",
                    fontWeight = FontWeight.W600,
                    color = PrimaryMain
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Button(
                modifier = Modifier.weight(1f),
                border = BorderStroke(width = 1.dp, color = PrimaryMain),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryMain
                ),
                onClick = { onNavigateToSignup() }) {
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.W600
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        DividerTextComponent()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = PrimaryBorder),
                onClick = onNavigateToLogin
            ) {

                    Image(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.fb_logo),
                        contentDescription = ""
                    )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Surface(
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = PrimaryBorder),
                onClick = onNavigateToSignup
            ) {
                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.height(33.dp))
    }
}

@Composable
fun BottomSection(index: Int, onButtonClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        Indicators(index)
    }
}

@Composable
fun BoxScope.Indicators(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(2) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) PrimaryMain else PrimaryBorder
            )
    ) {}
}
