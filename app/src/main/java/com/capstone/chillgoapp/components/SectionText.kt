package com.capstone.chillgoapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Preview
@Composable
fun SectionText(
    modifier: Modifier = Modifier,
    title: String = "Title",
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = PrimaryMain
        ),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}