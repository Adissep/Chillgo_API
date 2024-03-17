package com.capstone.chillgoapp.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.CardLoading
import com.capstone.chillgoapp.components.ExpandableText
import com.capstone.chillgoapp.data.detail.DetailTravelUiState
import com.capstone.chillgoapp.data.detail.DetailTravelViewModel
import com.capstone.chillgoapp.data.response.UmkmResponse
import com.capstone.chillgoapp.ui.theme.ChillGoAppTheme
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryBorder
import com.capstone.chillgoapp.ui.theme.PrimaryMain
import com.gowtham.ratingbar.RatingBar
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    isScroll: Boolean,
    viewModel: DetailTravelViewModel = hiltViewModel(),
    navigateToReviews: () -> Unit,
    navigateToUmkmForm: (String) -> Unit,
    navigateToUmkmDetail: (placeId: Long, city: String, umkmId: Long) -> Unit
) {
    val detailPlace by viewModel.uiState.collectAsState(initial = DetailTravelUiState())
    val umkmState = viewModel.umkmState.collectAsLazyPagingItems()

    val data = detailPlace.data

    DetailContent(
        data.id,
        data.image_url,
        data.place_name,
        data.description,
        data.price.toInt(),
        city = data.city,
        rating = data.rating,
        loading = detailPlace.loading,
        navigateToReviews = navigateToReviews,
        navigateToUmkmDetail = navigateToUmkmDetail,
        isScroll = isScroll,
        modifier = modifier,
        umkm = umkmState,
        navigateToUmkmForm = {
            navigateToUmkmForm(data.id.toString())
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailContent(
    placeId: Long,
    imageUrl: String,
    title: String,
    description: String,
    basePoint: Int,
    loading: Boolean,
    isScroll: Boolean,
    rating: Double,
    city: String,
    umkm: LazyPagingItems<UmkmResponse>,
    modifier: Modifier = Modifier,
    navigateToReviews: () -> Unit = {},
    navigateToUmkmForm: () -> Unit,
    navigateToUmkmDetail: (placeId: Long, city: String, umkmId: Long) -> Unit
) {
    val pageState = rememberPagerState { 2 }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalPager(state = pageState) {
            if (it == 0) Image(
                rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()

            ) else {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.blur),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                    )
                    OutlinedButton(
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0XFFC7CEBE)),
                        onClick = {}
                    ) {
                        Image(
                            modifier = Modifier.size(15.dp),
                            painter = painterResource(id = R.drawable.vr),
                            contentDescription = "VR"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Launch", color = Color(0XFFC7CEBE))
                    }
                }
            }
        }
        TopSection(index = pageState.currentPage)
        DetailBody(
            title = title,
            basePoint = basePoint,
            description = description,
            navigateToReviews = navigateToReviews,
            navigateToUmkmDetail = { _: Long, c: String, u: Long ->
                navigateToUmkmDetail(placeId, c, u)
            },
            isScroll = isScroll,
            loading = loading,
            umkm = umkm,
            rating = rating,
            city = city,
            navigateToUmkmForm = navigateToUmkmForm
        )
    }
}

@Composable
fun TopSection(index: Int, onButtonClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        DetailIndicators(index)
    }
}

@Composable
fun BoxScope.DetailIndicators(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = (configuration.screenWidthDp / 2) - 8

        repeat(2) {
            DetailIndicator(isSelected = it == index, screenWidth)
        }
    }
}

@Composable
fun DetailIndicator(isSelected: Boolean, screenWidth: Int = 80) {
    val width = animateDpAsState(
        targetValue = screenWidth.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    Box(
        modifier = Modifier
            .height(4.dp)
            .width(width.value)
            .background(
                color = if (isSelected) PrimaryMain else Color(0XFFC7CEBE)
            )
    ) {}
}

@Composable
fun DetailBody(
    title: String,
    basePoint: Int,
    description: String,
    isScroll: Boolean,
    loading: Boolean,
    rating: Double,
    city: String,
    umkm: LazyPagingItems<UmkmResponse>,
    navigateToUmkmForm: () -> Unit,
    navigateToReviews: () -> Unit = {},
    navigateToUmkmDetail: (placeId: Long, city: String, umkmId: Long) -> Unit
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .padding(top = 229.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .background(PrimaryBody)
                .verticalScroll(scrollState)
                .padding(start = 16.dp, top = 28.dp, end = 16.dp)
        ) {
            DetailBodyHeader(
                title = title,
                basePoint = basePoint,
                description = description,
                loading = loading,
                rating = rating,
                city = city
            )
            Divider(
                modifier = Modifier.padding(
                    top = 29.dp, bottom = 9.dp
                ),
                thickness = 1.dp,
                color = PrimaryBorder
            )
            DetailBodyContent(
                navigateToReviews = navigateToReviews,
                navigateToUmkmDetail = navigateToUmkmDetail,
                scrollState = scrollState,
                isScroll = isScroll,
                loading = loading,
                umkm = umkm,
                navigateToUmkmForm = navigateToUmkmForm,
            )
        }
    }
}

@Composable
fun DetailBodyHeader(
    loading: Boolean,
    title: String,
    basePoint: Int,
    description: String,
    rating: Double,
    city: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBody)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                textAlign = TextAlign.Start,
                fontSize = 26.sp,
                fontWeight = FontWeight.W600,
                color = PrimaryMain,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.required_point, basePoint),
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = PrimaryMain,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = city.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = PrimaryMain,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = rating.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = PrimaryMain,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "4 rb+ rating",
                fontSize = 16.sp,
                color = PrimaryMain,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "About",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = PrimaryMain,
        )
        if (!loading)
            ExpandableText(
                text = description,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = PrimaryMain
                ),
            )
        else CardLoading()
    }
}

@Composable
fun DetailBodyContent(
    navigateToReviews: () -> Unit,
    navigateToUmkmDetail: (placeId: Long, city: String, umkmId: Long) -> Unit,
    umkm: LazyPagingItems<UmkmResponse>,
    isScroll: Boolean,
    scrollState: ScrollState,
    loading: Boolean,
    navigateToUmkmForm: () -> Unit,
) {
    var scrollToPosition by remember {
        mutableFloatStateOf(0f)
    }

    var review by remember {
        mutableStateOf("")
    }
    var rating by remember {
        mutableFloatStateOf(0f)
    }

    if (isScroll) {
        LaunchedEffect(key1 = loading) {
            delay(100)
            if (!loading) scrollState.animateScrollTo(scrollToPosition.roundToInt())
        }
    }

    val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

    Column {
        Text(
            text = "Opening Hours",
            color = PrimaryMain,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700

        )

        days.forEach {
            Row(
                modifier = Modifier.padding(vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "",
                    tint = PrimaryMain
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = it,
                    color = PrimaryMain,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "08.00 - 16.00 WIB",
                    color = PrimaryMain,
                    fontSize = 16.sp
                )
            }
        }

        Divider(
            modifier = Modifier.padding(
                top = 29.dp, bottom = 14.dp
            ),
            thickness = 1.dp,
            color = PrimaryBorder
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Storefront,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "UMKM AROUND HERE!",
                color = PrimaryMain,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(umkm.itemCount) { index ->
                val data = umkm[index]
                Card(
                    modifier = Modifier
                        .height(120.dp)
                        .width(150.dp)
                        .padding(end = if (index < 9) 38.dp else 0.dp)
                        .clickable {
                            navigateToUmkmDetail(
                                data?.id ?: 0L,
                                data?.city ?: "",
                                data?.id ?: 0L
                            )
                        },
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            painter = rememberAsyncImagePainter(model = data?.image_url),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.height(13.dp))
                        Text(
                            text = data?.umkm_name ?: "",
                            color = PrimaryMain,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.padding(horizontal = 13.dp),
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0X80C7CEBE)
            ),
            onClick = navigateToUmkmForm
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "",
                tint = PrimaryMain
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.msme_submission),
                color = PrimaryMain,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = 9.dp),
            thickness = 1.dp,
            color = PrimaryBorder
        )
        Text(
            text = "Write your review here..",
            fontWeight = FontWeight.W600,
            color = PrimaryMain
        )
        Spacer(modifier = Modifier.height(36.dp))
        RatingBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = rating,
            painterEmpty = painterResource(id = R.drawable.baseline_star_border_24),
            painterFilled = painterResource(id = R.drawable.baseline_star_24),
            spaceBetween = 2.dp,
            size = 32.dp,
            onValueChange = { rating = it },
            onRatingChanged = {})
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            value = review,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.EditNote,
                    contentDescription = null,
                    tint = PrimaryMain
                )
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Review",
                    color = PrimaryMain,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0X80C7CEBE),
                unfocusedContainerColor = Color(0X80C7CEBE),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            onValueChange = {
                review = it
            })
        Spacer(modifier = Modifier.height(9.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryMain
            ),
            onClick = { }) {
            Text(text = "Posting")
        }
        Spacer(modifier = Modifier
            .height(4.dp)
            .onGloballyPositioned {
                scrollToPosition = it.positionInRoot().y
            })
        repeat(2) { ReviewItem() }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { navigateToReviews() },
            text = "Lihat semua ulasan",
            color = PrimaryMain
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun ReviewItem() {
    Column(
        modifier = Modifier.padding(bottom = 14.dp)
    ) {
        Row {
            Icon(
                modifier = Modifier.size(21.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Name",
                fontSize = 12.sp,
                color = PrimaryMain
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        RatingBar(
            value = 4f,
            painterEmpty = painterResource(id = R.drawable.baseline_star_border_24),
            painterFilled = painterResource(id = R.drawable.baseline_star_24),
            spaceBetween = 2.dp,
            size = 14.dp,
            onValueChange = { },
            onRatingChanged = {})
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Deskripsi Ulasan",
            fontSize = 12.sp,
            color = PrimaryMain
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    ChillGoAppTheme {
    }
}