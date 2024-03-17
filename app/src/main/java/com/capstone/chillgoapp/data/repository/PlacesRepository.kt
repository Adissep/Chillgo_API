package com.capstone.chillgoapp.data.repository

import com.capstone.chillgoapp.data.response.BaseResponseWithPaging
import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.data.response.UmkmResponse
import com.capstone.chillgoapp.model.FormUmkmModel
import com.capstone.chillgoapp.model.PlacesDTO
import com.capstone.chillgoapp.model.RecommendedPlaceModel
import com.capstone.chillgoapp.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {
    suspend fun getPlaceByIdLocal(id: Int): Flow<PlacesDTO?>
    suspend fun getPlaceBasedOnCity(
        city: String
    ): Flow<UiState<List<PlaceResponse>>>

    suspend fun getPlaceBasedOnCityAndSaveToLocal(
        city: String
    ): Flow<UiState<List<PlacesDTO>>>

    suspend fun getAllPlace(page: Int): Flow<UiState<BaseResponseWithPaging<List<PlaceResponse>>>>

    suspend fun getAllUmkm(page: Int): Flow<UiState<BaseResponseWithPaging<List<UmkmResponse>>>>

    suspend fun getTopRatingPlace(): Flow<UiState<List<TopRatingPlaceResponse>>>

    suspend fun getRecommendedPlace(limit: Int): Flow<UiState<List<RecommendedPlaceModel>>>

    suspend fun getDetailPlaceById(id: String): Flow<UiState<DetailPlace>>

    suspend fun getUmkmById(id: Int): Flow<UiState<DetailPlace>>

    suspend fun saveUmkmByPlaceId(id: String, data: FormUmkmModel): Flow<UiState<Any>>

    suspend fun saveCategoryPlaces(category: String): UiState<String>
}