package com.capstone.chillgoapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.ui.theme.ChillGoAppTheme
import com.capstone.chillgoapp.ui.theme.PrimaryMain
import com.gowtham.ratingbar.RatingBar

@Composable
fun MoreItem(
    id: Long,
    imageUrl: String,
    title: String,
    description: String,
    requiredPoint: Int,
    orderCount: Int,
    rating:Float,
    modifier: Modifier = Modifier,
    onFavClick: (Long) -> Unit,
    onReviewClick: () -> Unit,
) {
    Box(modifier = Modifier.padding(bottom = 28.dp)) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    rememberAsyncImagePainter(imageUrl),
                    contentDescription = description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(113.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                Spacer(modifier = Modifier.height(14.dp))
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 18.dp, bottom = 11.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = PrimaryMain
                        )
                        Text(
                            text = stringResource(R.string.required_point, requiredPoint),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = PrimaryMain
                        )
                    }
                    Spacer(modifier = Modifier.height(27.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RatingBar(
                            value = rating,
                            painterEmpty = painterResource(id = R.drawable.baseline_star_border_24),
                            painterFilled = painterResource(id = R.drawable.baseline_star_24),
                            spaceBetween = 2.dp,
                            onValueChange = {},
                            onRatingChanged = {})
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier.clickable {
                                onFavClick(id)
                            },
                            imageVector = if(orderCount == 0) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                            contentDescription = "",
                            tint = PrimaryMain
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = "7",
                            color = PrimaryMain,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Icon(
                            modifier = Modifier.clickable {
                                onReviewClick()
                            },
                            imageVector = Icons.Default.Chat,
                            contentDescription = "",
                            tint = PrimaryMain
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MoreItemPreview() {
    ChillGoAppTheme {
        MoreItem(
            0, "", "The Great Asia Afrika", "", 40000,
            modifier = Modifier.padding(8.dp), onFavClick = {}, orderCount = 0, onReviewClick = {}, rating = 4F
        )
    }
}