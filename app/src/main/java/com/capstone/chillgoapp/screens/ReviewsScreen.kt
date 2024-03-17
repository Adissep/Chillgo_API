package com.capstone.chillgoapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryBorder
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ReviewsScreen(
    onBackPressed: () -> Unit = {}
) {
    var selectedChip by remember {
        mutableStateOf("Semua")
    }

    Surface(modifier = Modifier.background(PrimaryBody)) {
        Column(
            modifier = Modifier
                .background(PrimaryBody)
                .padding(top = 6.dp, start = 19.dp, end = 19.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(PrimaryBody),
                        text = "Lembang Park An..",
                        color = PrimaryMain,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PrimaryBody),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .background(PrimaryBody)
                            .clickable { onBackPressed() },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryMain
                    )
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            Chips(selectedChip = selectedChip, onSelectedChip = {
                selectedChip = it
            })
            Spacer(modifier = Modifier.height(25.dp))
            Divider(
                color = PrimaryBorder
            )
            Row(
                modifier = Modifier
                    .background(PrimaryBody)
                    .padding(start = 10.dp, top = 4.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Semua",
                    color = PrimaryMain,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Terbaru",
                    color = PrimaryMain,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    modifier = Modifier.size(30.dp),
                    contentPadding = PaddingValues(8.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, PrimaryMain),
                    onClick = {}
                ) {
                    Image(
                        modifier = Modifier
                            .size(15.dp),
                        painter = painterResource(id = R.drawable.adjust),
                        contentDescription = "Tune"
                    )
                }
            }
            Spacer(modifier = Modifier.height(11.dp))
            Divider(
                color = PrimaryBorder
            )
            Spacer(modifier = Modifier.height(29.dp))
            LazyColumn(content = {
                items(10) {
                    ReviewItem()
                }
            })
        }
    }
}

@Composable
fun Chips(
    selectedChip: String,
    onSelectedChip: (String) -> Unit
) {
    val chips = listOf("Semua", "5", "4", "3", "2", "1")

    Row {
        chips.forEach {
            Surface(
                modifier = Modifier
                    .background(PrimaryBody)
                    .padding(end = 8.dp)
                    .clickable { onSelectedChip(it) },
                shape = RoundedCornerShape(16.dp),
                color = if (selectedChip == it) Color(0X80C7CEBE) else Color.Transparent,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedChip == it) Color(0X80C7CEBE) else PrimaryMain
                )
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (it != "Semua") {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = PrimaryMain
                        )
                    }

                    Text(
                        text = it,
                        color = PrimaryMain,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}