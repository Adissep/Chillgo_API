package com.capstone.chillgoapp.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.capstone.chillgoapp.data.onboard.OnBoardingViewModel
import com.capstone.chillgoapp.ui.common.PermissionUtils
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToSecondScreen: () -> Unit,
) {
    /*val scope = rememberCoroutineScope()
    val pageState = rememberPagerState { 2 }*/

    var selectedChip by remember { mutableStateOf("") }
    val context = LocalContext.current
    var checkPermission by remember { mutableStateOf(false) }

    var types by remember { mutableStateOf("") }

    val permissionUtils = PermissionUtils(context)

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
            val check = it.values.contains(false)
            checkPermission = !check
        }

    fun navigateTo(type: String) {
        if (type == "Next") {
            viewModel.saveCategory(context, selectedChip) {
                onNavigateToSecondScreen()
            }
        } else {
            onNavigateToLogin()
        }
    }

    LaunchedEffect(key1 = checkPermission, block = {
        if (checkPermission) {
            navigateTo(types)
        } else {
            Toast.makeText(
                context,
                "You must provide permissions to maximize application performance",
                Toast.LENGTH_LONG
            ).show()
        }
    })



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBody),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Where do you prefer to vacation?",
            fontSize = 16.sp,
            color = PrimaryMain,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.height(67.dp))

        OnBoardingChips(
            selectedChip = selectedChip,
            onSelect = {
                selectedChip = it
            }
        )

        Spacer(modifier = Modifier.height(142.dp))

        Spacer(modifier = Modifier.height(47.dp))
        OnBoardingButton(label = "Next") {
            types = "Next"
            permissionLauncher.launch(permissionUtils.listPermission())
        }
        Spacer(modifier = Modifier.height(32.dp))
        OnBoardingButton(label = "Skip", outlined = true) {
            types = "Skip"
            permissionLauncher.launch(permissionUtils.listPermission())
        }
    }
}

@Composable
fun OnBoardingChips(
    selectedChip: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OnBoardingChip(label = "Budaya", selectedChip = selectedChip) {
                onSelect(it)
            }
            Spacer(modifier = Modifier.width(12.dp))
            OnBoardingChip(label = "Taman Hiburan", selectedChip = selectedChip) {
                onSelect(it)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Spacer(modifier = Modifier.width(12.dp))
            OnBoardingChip(label = "Tempat Ibadah", selectedChip = selectedChip) {
                onSelect(it)
            }
            Spacer(modifier = Modifier.width(12.dp))
            OnBoardingChip(label = "Cagar Alam", selectedChip = selectedChip) {
                onSelect(it)
            }
        }
    }
}

@Composable
fun OnBoardingChip(
    label: String,
    selectedChip: String,
    onSelect: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onSelect(label) },
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(width = 1.dp, color = PrimaryMain),
        color = if (label == selectedChip) PrimaryMain else Color.Transparent
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = label,
            color = if (label != selectedChip) PrimaryMain else Color.White,
            fontSize = 18.sp
        )
    }
}

@Composable
fun OnBoardingButton(
    label: String,
    outlined: Boolean = false,
    onButtonClick: () -> Unit
) {
    if (outlined) OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        border = BorderStroke(width = 1.dp, color = PrimaryMain),
        onClick = { onButtonClick() }) {
        Text(
            text = label,
            fontWeight = FontWeight.W600,
            color = PrimaryMain
        )
    } else Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryMain,
        ),
        onClick = { onButtonClick() }) {
        Text(
            text = label,
            fontWeight = FontWeight.W600
        )
    }
}


@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingScreen(onNavigateToLogin = { }) {

    }
}