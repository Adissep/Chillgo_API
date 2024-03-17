package com.capstone.chillgoapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.ViewModelFactory
import com.capstone.chillgoapp.components.FavoriteItem
import com.capstone.chillgoapp.data.favorite.FavoriteState
import com.capstone.chillgoapp.data.favorite.FavoriteViewModel
import com.capstone.chillgoapp.ui.common.UiState
import com.capstone.chillgoapp.ui.theme.PrimaryBody

@Preview
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit = {},
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(),
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderFavorites()
            }

            is UiState.Success -> {
                if(uiState.data.orderTravel.isNotEmpty()) {
                    CartContent(uiState.data,
                        viewModel = viewModel,
                        onNavigateToDetail = onNavigateToDetail)
                } else {
                    Surface(
                        modifier = modifier
                            .fillMaxSize()
                            .background(PrimaryBody)
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.fav_is_empty),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PrimaryBody),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            is UiState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun CartContent(
    state: FavoriteState,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryBody)
    ) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(7.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Adaptive(160.dp)
        ) {
            items(state.orderTravel.size) { index ->
                val item = state.orderTravel[index]

                Column(
                    modifier = Modifier
                        .background(PrimaryBody)
                        .padding(8.dp)
                ) {
                    FavoriteItem(
                        id = item.travel.id,
                        image = item.travel.image,
                        title = item.travel.title,
                        totalPoint = item.travel.requiredPoint * item.count,
                        count = item.count,
                        onFavoriteDelete = {
                            viewModel.updateOrderTravel(item.travel.id, 0)
                            viewModel.getAddedOrderFavorites()
                        },
                        onNavigateToDetail = onNavigateToDetail
                    )
                    Divider()
                }
            }
        }
    }
}