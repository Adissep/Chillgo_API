package com.capstone.chillgoapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.capstone.chillgoapp.components.CardLoading
import com.capstone.chillgoapp.components.MoreItem
import com.capstone.chillgoapp.components.MoreSearch
import com.capstone.chillgoapp.components.NormalTextComponent
import com.capstone.chillgoapp.components.SectionErrorText
import com.capstone.chillgoapp.data.home.RecommendedPlaceUiState
import com.capstone.chillgoapp.data.home.TopPlaceUiState
import com.capstone.chillgoapp.data.more.MoreViewModel
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.model.FakeTravelDataSource
import com.capstone.chillgoapp.model.OrderTravel
import com.capstone.chillgoapp.ui.common.TypePlace
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    moreViewModel: MoreViewModel = hiltViewModel(),
    navigateToDetail: (placeId: String, city: String) -> Unit = { _, _ -> },
    navigateToDetailReview: (placeId: String, city: String) -> Unit = { _, _ -> },
) {

    val city = moreViewModel.cityParams.uppercase()
    var type by moreViewModel.type
    val placesUiState = moreViewModel.moreState.collectAsLazyPagingItems()
    val topPlace by moreViewModel.topPlaceUiState.collectAsState(TopPlaceUiState())
    val recommendedPlaceUiState by moreViewModel.recommendedPlaceUiState.collectAsState(
        RecommendedPlaceUiState()
    )

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier.background(PrimaryBody)
    ) {
        MoreSearch(
            onFilterClick = {
            },
            onSearch = {
                searchQuery = it
            },
            query = searchQuery
        )
        MoreChips(

            city = if (city == TypePlace.all.name) "Bandung" else city,
            type = type,
        ) {
            type = it
            moreViewModel.getPlacesByCity(it)
        }
        Spacer(modifier = Modifier.height(15.dp))
        MoreContent(
            searchQuery = searchQuery,
            navigateToDetail = navigateToDetail,
            navigateToDetailReview = navigateToDetailReview,
            places = placesUiState,
            top = topPlace,
            recommend = recommendedPlaceUiState,
            type = type,
        )
    }
}

@Composable
fun MoreChips(
    city: String,
    type: String = TypePlace.city.name,
    onTypeClicked: (String) -> Unit
) {

    var selectedType by remember { mutableStateOf(type) }

    LazyRow(content = {
        item {
            MoreChip(
                modifier = Modifier.padding(start = 16.dp),
                label = city,
                selectedChip = selectedType == TypePlace.city.name,
                onChoiceChip = {
                    selectedType = it
                    onTypeClicked(it)
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.height(18.dp),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = if (TypePlace.city.name == selectedType) Color.White else PrimaryMain
                    )
                },
                type = TypePlace.city
            )
        }
        item {
            MoreChip(
                modifier = Modifier.padding(start = 16.dp),
                label = "Top High Rating",
                selectedChip = selectedType == TypePlace.top.name,
                onChoiceChip = {
                    selectedType = it
                    onTypeClicked(it)
                },
                type = TypePlace.top
            )
        }
        item {
            MoreChip(
                modifier = Modifier.padding(start = 16.dp),
                label = "Top Place Recommendation",
                selectedChip = selectedType == TypePlace.recommended.name,
                onChoiceChip = {
                    selectedType = it
                    onTypeClicked(it)
                },
                type = TypePlace.recommended
            )
        }
    })
}

@Composable
fun MoreChip(
    modifier: Modifier = Modifier,
    label: String = "Label",
    type: TypePlace,
    selectedChip: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = {},
    onChoiceChip: (String) -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(1.dp, PrimaryMain),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor =
            if (selectedChip) PrimaryMain
            else Color.Transparent
        ),
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 14.dp),
        onClick = { onChoiceChip(type.name) }) {
        Row {
            if (leadingIcon != null) leadingIcon()
            if (leadingIcon != null) Spacer(modifier = Modifier.width(5.dp))
            NormalTextComponent(
                value = label,
                fontSize = 15.sp,
                color = if (selectedChip) Color.White
                else PrimaryMain
            )
        }
    }
}

@Composable
fun MoreContent(
    searchQuery: String,
    places: LazyPagingItems<PlaceResponse>,
    top: TopPlaceUiState,
    recommend: RecommendedPlaceUiState,
    type: String,
    modifier: Modifier = Modifier,
    navigateToDetail: (placeId: String, city: String) -> Unit,
    navigateToDetailReview: (placeId: String, city: String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.testTag("TravelList")
    ) {
        when (type) {

            TypePlace.top.name -> {
                if (top.loading) {
                    item {
                        CardLoading()
                    }
                } else {
                    val topFilter = top.data.filter {
                        it.place_name.lowercase().contains(searchQuery.lowercase())
                    }

                    items(topFilter.size) { index ->

                        var orderCount by remember {
                            mutableIntStateOf(top.data.size)
                        }

                        val data = topFilter[index]
                        val placeData = topFilter[index].Place

                        MoreItem(
                            id = placeData.id,
                            imageUrl = placeData.image_url,
                            title = placeData.place_name,
                            orderCount = orderCount,
                            description = placeData.description,
                            rating = placeData.rating.toFloat(),
                            requiredPoint = placeData.price.toInt(),
                            modifier = Modifier.clickable {
                                navigateToDetail(data.place_id.toString(), data.city)
                            },
                            onFavClick = {
                                if (orderCount == 0) {
                                    orderCount += 1
                                } else {
                                    orderCount = 0
                                }

                                //TODO: UPDATE LIKE COUNT / FAV COUNT
                            },
                            onReviewClick = {
                                navigateToDetailReview(
                                    data.id.toString(),
                                    data.city
                                )
                            }
                        )

                    }
                }
            }

            TypePlace.recommended.name -> {
                if (recommend.loading) {
                    item {
                        CardLoading()
                    }
                } else {
                    val filterData =
                        recommend.data.filter {
                            it.placeName.lowercase().contains(searchQuery.lowercase())
                        }
                    items(filterData.size) { index ->
                        val data = filterData[index]
                        var orderCount by remember {
                            mutableIntStateOf(recommend.data.size)
                        }

                        MoreItem(
                            id = data.id,
                            imageUrl = data.placeImageUrl,
                            title = data.placeName,
                            orderCount = orderCount,
                            description = data.placeDescription,
                            rating = data.placeRating.toFloat(),
                            requiredPoint = data.placePrice.toInt(),
                            modifier = Modifier.clickable {
                                navigateToDetail(data.id.toString(), data.placeCity)
                            },
                            onFavClick = {
                                if (orderCount == 0) {
                                    orderCount += 1
                                } else {
                                    orderCount = 0
                                }

                                //TODO: UPDATE LIKE COUNT / FAV COUNT
                            },
                            onReviewClick = {
                                navigateToDetailReview(
                                    data.id.toString(),
                                    data.placeCity
                                )
                            }
                        )
                    }
                }

            }

            TypePlace.city.name, TypePlace.all.name, "" -> {
                val filterData =
                    places.itemSnapshotList.items.filter {
                        it.place_name.lowercase().contains(searchQuery.lowercase())
                    }

                when (val state = places.loadState.refresh) {
                    is LoadState.NotLoading -> Unit
                    LoadState.Loading -> {
                        item {
                            CardLoading()
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            SectionErrorText(description = state.error.localizedMessage ?: "")
                        }
                    }

                    else -> Unit
                }
                items(
                    items = filterData,
                ) { data ->
                    var orderCount by remember {
                        mutableIntStateOf(filterData.size)
                    }

                    MoreItem(
                        id = data.id,
                        imageUrl = data.image_url,
                        title = data.place_name,
                        orderCount = orderCount,
                        description = data.description,
                        rating = data.rating.toFloat(),
                        requiredPoint = data.price.toInt(),
                        modifier = Modifier.clickable {
                            navigateToDetail(data.id.toString(), data.city)
                        },
                        onFavClick = {
                            if (orderCount == 0) {
                                orderCount += 1
                            } else {
                                orderCount = 0
                            }

                            //TODO: UPDATE LIKE COUNT / FAV COUNT
                        },
                        onReviewClick = {
                            navigateToDetailReview(data.id.toString(), data.city)
                        }
                    )
                }
                when (val state = places.loadState.append) {
                    is LoadState.NotLoading -> Unit
                    LoadState.Loading -> {
                        item {
                            CardLoading()
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            SectionErrorText(description = state.error.localizedMessage ?: "")
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MoreScreenPreview() {
    val orders = arrayListOf<OrderTravel>()
    FakeTravelDataSource.dummyTravels.forEach {
        orders.add(OrderTravel(it, 0))
    }

    Column(
        modifier = Modifier.background(PrimaryBody)
    ) {
        MoreSearch(
            onSearch = {},
            onFilterClick = {},
            query = ""
        )
        MoreChips(TypePlace.city.name) {}
        Spacer(modifier = Modifier.height(15.dp))
    }
}