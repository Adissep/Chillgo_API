package com.capstone.chillgoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.ExpandableText
import com.capstone.chillgoapp.data.umkm.UmkmUiState
import com.capstone.chillgoapp.data.umkm.UmkmViewModel
import com.capstone.chillgoapp.ui.theme.PrimaryBorder
import com.capstone.chillgoapp.ui.theme.PrimaryMain
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, MapsComposeExperimentalApi::class)
@Preview
@Composable
fun UmkmMapScreen(
    viewModel: UmkmViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var marker by remember { mutableStateOf<Marker?>(null) }

    val umkmState by viewModel.umkmState.collectAsStateWithLifecycle(initialValue = UmkmUiState())
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                umkmState.detailData.latitude,
                umkmState.detailData.longitude
            ), 10f
        )
    }

    fun animatedCamera() = scope.launch {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition(
                    LatLng(umkmState.detailData.latitude, umkmState.detailData.longitude),
                    15f,
                    0f,
                    0F
                ),
            ),
            durationMs = 1000
        )
    }

    LaunchedEffect(key1 = umkmState.detailData.latitude, block = {
        animatedCamera()
        marker?.showInfoWindow()
    })

    fun expandBottomSheet() = scope.launch {
        scaffoldState.bottomSheetState.expand()
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetDragHandle = {
            Image(
                modifier = Modifier
                    .padding(vertical = 18.dp)
                    .width(54.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.drag),
                contentDescription = ""
            )
        },
        sheetShape = RoundedCornerShape(0.dp),
        sheetContent = {
            UmkmDetail(
                phoneNumber = umkmState.detailData.no_telepon,
                rating = umkmState.detailData.rating.toString(),
                description = umkmState.detailData.description,
                title = umkmState.detailData.umkm_name,
                address = "Unknown",
                imageUrl = umkmState.detailData.image_url,
                navigateToMaps = {
                    viewModel.toGoogleMaps(
                        context = context,
                        latLng = LatLng(
                            umkmState.detailData.latitude,
                            umkmState.detailData.longitude
                        ),
                        placeName = umkmState.detailData.umkm_name
                    )
                }
            )
        }) {
        Surface(
            modifier = Modifier.padding(bottom = 56.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties()
            ) {
                Circle(
                    center = LatLng(
                        umkmState.detailData.latitude,
                        umkmState.detailData.longitude
                    ),
                    radius = 100.0,
                    fillColor = Color.Red.copy(alpha = .6F),
                    strokeColor = Color.Red
                )
                Circle(
                    center = LatLng(
                        umkmState.places.latitude,
                        umkmState.places.longitude
                    ),
                    radius = 100.0,
                    fillColor = Color.Red.copy(alpha = .6F),
                    strokeColor = Color.Red
                )
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            umkmState.places.latitude,
                            umkmState.places.longitude
                        )
                    ),
                    title = umkmState.places.place_name,
                    onClick = {
                        marker = it
                        expandBottomSheet()
                        true
                    }
                )
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            umkmState.detailData.latitude,
                            umkmState.detailData.longitude
                        )
                    ),
                    title = umkmState.detailData.umkm_name,
                    onClick = {
                        marker = it
                        viewModel.getDetailUmkm(umkmState.detailData.id)
                        expandBottomSheet()
                        true
                    }
                )
                umkmState.listUmkm.forEach { umkm ->
                    Circle(
                        center = LatLng(
                            umkm.latitude,
                            umkm.longitude
                        ),
                        radius = 100.0,
                        fillColor = Color.Red.copy(alpha = .6F),
                        strokeColor = Color.Red
                    )
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                umkm.latitude,
                                umkm.longitude
                            ),
                        ),
                        title = umkm.umkm_name,
                        onClick = {
                            marker = it
                            viewModel.getDetailUmkm(umkm.id)
                            expandBottomSheet()
                            true
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun UmkmDetail(
    imageUrl: String,
    phoneNumber: String,
    description: String,
    title: String,
    rating: String,
    address: String,
    navigateToMaps: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .heightIn(max = (screenHeight * .8).dp)
            .verticalScroll(rememberScrollState())
    ) {
        UmkmHeader(
            imageUrl, phoneNumber, description, title, rating, address, navigateToMaps
        )
    }
}

@Composable
fun UmkmHeader(
    imageUrl: String,
    phoneNumber: String,
    description: String,
    title: String,
    rating: String,
    address: String,
    navigateToMaps: () -> Unit,
) {
    Column {
        Surface(
            modifier = Modifier.size(114.dp),
            color = Color(0XCCC7CEBE),
            shape = CircleShape,
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = 32.sp,
            fontWeight = FontWeight.W600,
            color = PrimaryMain,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = address,
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = PrimaryMain,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = rating,
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
        Row {
            Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Identity verified",
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = PrimaryMain,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0X80C7CEBE)
            ),
            onClick = navigateToMaps
        ) {
            Icon(
                imageVector = Icons.Outlined.Map,
                contentDescription = "",
                tint = PrimaryMain
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Open Google Maps",
                color = PrimaryMain,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )
        }
        Divider(
            modifier = Modifier
                .padding(vertical = 9.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = PrimaryBorder
        )
        UmkmBody(
            phoneNumber, description
        )
    }
}

@Composable
fun UmkmBody(
    phoneNumber: String,
    description: String,
) {
    var showAll by remember {
        mutableStateOf(false)
    }
    val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

    Column {
        Text(
            text = "About",
            color = PrimaryMain,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            ExpandableText(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                text = description,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = PrimaryMain
                ),
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        Row(
            modifier = Modifier.padding(top = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Souvenirs",
                color = PrimaryMain,
                fontSize = 16.sp
            )
        }
        Row(
            modifier = Modifier.padding(top = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Message,
                contentDescription = "",
                tint = PrimaryMain
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = phoneNumber,
                color = PrimaryMain,
                fontSize = 16.sp
            )
        }
        Divider(
            modifier = Modifier
                .padding(vertical = 9.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = PrimaryBorder
        )
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
    }
}