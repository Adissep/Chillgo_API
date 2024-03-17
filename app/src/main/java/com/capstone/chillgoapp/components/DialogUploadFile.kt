package com.capstone.chillgoapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Composable
fun DialogUploadDocument(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    openCamera: () -> Unit,
    openGallery: () -> Unit,
    onCancel: () -> Unit,
) {

    if (show) {
        Dialog(onDismissRequest = onCancel) {
            Surface(
                modifier = modifier
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = openCamera,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryMain,
                            contentColor = Color.White,
                        ),
                        modifier = Modifier
                            .padding(
                                vertical = 16.dp,
                                horizontal = 25.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Take photo",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        )
                    }

                    Button(
                        onClick = openGallery,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryMain,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(
                                start = 25.dp,
                                end = 25.dp,
                                bottom = 16.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Choose existing photo",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        )
                    }
                }

            }
        }
    }

}