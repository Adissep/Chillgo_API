package com.capstone.chillgoapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Preview
@Composable
fun SocialMediaButton(
    modifier: Modifier = Modifier,
    label: String = "Click",
    leadingIcon: @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, PrimaryMain),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            leadingIcon()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = label,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = PrimaryMain
                )
            )
        }
    }
}