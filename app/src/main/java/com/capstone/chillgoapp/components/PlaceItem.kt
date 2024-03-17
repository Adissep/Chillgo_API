package com.capstone.chillgoapp.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.model.Travel
import com.capstone.chillgoapp.ui.theme.ChillGoAppTheme
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Composable
fun PlaceItem(
    imageUrl:String,
    placeId:Long,
    placeName:String,
    placePrice: Long,
    orderCount: Int,
    modifier: Modifier = Modifier,
    onFavClick: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .width(120.dp)
            .height(120.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )
                Icon(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .clickable {
                            Log.d("TAG", "Add to fav")
                            onFavClick(placeId)
                        },
                    imageVector = if(orderCount == 0) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = "",
                    tint = PrimaryMain)
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = placeName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp, color = PrimaryMain
                )
                Text(
                    text = stringResource(R.string.required_point, placePrice),
                    fontSize = 10.sp, color = PrimaryMain,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TravelItemPreview() {
    ChillGoAppTheme {

    }
}