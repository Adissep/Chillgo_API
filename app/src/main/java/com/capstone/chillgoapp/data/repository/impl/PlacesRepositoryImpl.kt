package com.capstone.chillgoapp.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.chillgoapp.data.DispatcherProvider
import com.capstone.chillgoapp.data.local.PlaceDao
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.response.BaseResponseWithPaging
import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.data.response.UmkmResponse
import com.capstone.chillgoapp.data.source.PlaceDataSource
import com.capstone.chillgoapp.data.source.RecommendationSource
import com.capstone.chillgoapp.model.FormUmkmModel
import com.capstone.chillgoapp.model.PlacesDTO
import com.capstone.chillgoapp.model.RecommendedPlaceModel
import com.capstone.chillgoapp.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlacesRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val placeApi: PlaceDataSource,
    private val recommendationPlaceApi: RecommendationSource,
    private val placeDao: PlaceDao,
    private val store: DataStore<Preferences>
) : PlacesRepository {

    private val CATEGORY_PREF = stringPreferencesKey("category_preferences")

    override suspend fun saveCategoryPlaces(category: String): UiState<String> {
        store.edit {
            it[CATEGORY_PREF] = category
        }

        store.data.first()[CATEGORY_PREF]?.let {
            return UiState.Success(it)
        }

        return UiState.Error("Failed Save Category")
    }

    override suspend fun getUmkmById(id: Int): Flow<UiState<DetailPlace>> =
        flow {
            when (val result = placeApi.getUmkmById(id)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> emit(UiState.Success(result.data.data))
                UiState.Init -> Unit
            }
        }.flowOn(dispatcher.io())

    override suspend fun getPlaceBasedOnCity(city: String): Flow<UiState<List<PlaceResponse>>> =
        flow {
            emit(UiState.Loading)

            when (val result = placeApi.getPlaceBasedOnCity(city)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> emit(UiState.Success(result.data.data))
                UiState.Init -> Unit
            }
        }.flowOn(dispatcher.io())

    override suspend fun getPlaceByIdLocal(id: Int): Flow<PlacesDTO?> = flow<PlacesDTO?> {
        emit(placeDao.getById(id))
    }.flowOn(dispatcher.io())

    override suspend fun getPlaceBasedOnCityAndSaveToLocal(city: String): Flow<UiState<List<PlacesDTO>>> =
        flow {
            emit(UiState.Loading)

            when (val result = placeApi.getPlaceBasedOnCity(city)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> {
                    placeDao.saveBatch(result.data.data.map { it.toDTO() })
                    emit(UiState.Success(placeDao.getAllPlaces().first()))
                }

                UiState.Init -> Unit
            }
        }.flowOn(dispatcher.io())

    override suspend fun getAllPlace(page: Int): Flow<UiState<BaseResponseWithPaging<List<PlaceResponse>>>> =
        flow {
            emit(UiState.Loading)

            when (val result = placeApi.getAllPlace(page)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> emit(UiState.Success(result.data))
                UiState.Init -> Unit
            }
        }

    override suspend fun getAllUmkm(page: Int): Flow<UiState<BaseResponseWithPaging<List<UmkmResponse>>>> =
        flow {
            emit(UiState.Loading)

            when (val result = placeApi.getAllUmkm(page)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> emit(UiState.Success(result.data))
                UiState.Init -> Unit
            }
        }

    override suspend fun getTopRatingPlace(): Flow<UiState<List<TopRatingPlaceResponse>>> = flow {
        emit(UiState.Loading)

        when (val result = placeApi.getTopRating()) {
            is UiState.Error -> emit(UiState.Error(result.errorMessage))
            UiState.Loading -> emit(UiState.Loading)
            is UiState.Success -> emit(UiState.Success(result.data.data))
            UiState.Init -> Unit
        }
    }.flowOn(dispatcher.io())

    override suspend fun getRecommendedPlace(limit: Int): Flow<UiState<List<RecommendedPlaceModel>>> =
        flow {
            emit(UiState.Loading)
            val output = mutableListOf<RecommendedPlaceModel>()
            store.data.first()[CATEGORY_PREF]?.let { category ->
                when (val result = recommendationPlaceApi.getRecommendationByCategory(category)) {
                    is UiState.Error -> emit(UiState.Error(result.errorMessage))
                    UiState.Loading -> emit(UiState.Loading)
                    is UiState.Success -> {
                        val recommendedPlaceData =
                            if (result.data.recommended_places.size >= limit) result.data.recommended_places.take(
                                limit
                            ) else result.data.recommended_places


                        recommendedPlaceData.map { place ->
                            when (val resultPlace =
                                placeApi.getDetailPlaceById(place.Place_Id.toString())) {
                                is UiState.Error -> emit(UiState.Error("Failed get detail: ${resultPlace.errorMessage}"))
                                UiState.Init -> Unit
                                UiState.Loading -> Unit
                                is UiState.Success -> {
                                    output.add(resultPlace.data.data.toRecommendedPlaceModel())
                                }
                            }
                        }

                        emit(UiState.Success(output))
                    }

                    UiState.Init -> Unit
                }
            } ?: run {
                when (val result = placeApi.getRecommendedPlace()) {
                    is UiState.Error -> emit(UiState.Error(result.errorMessage))
                    UiState.Loading -> emit(UiState.Loading)
                    is UiState.Success -> {
                        val recommendedPlaceData =
                            if (result.data.data.recommendedPlaces.size >= limit) result.data.data.recommendedPlaces.take(
                                limit
                            ) else result.data.data.recommendedPlaces


                        recommendedPlaceData.map { place ->
                            when (val resultPlace =
                                placeApi.getDetailPlaceById(place.place_id.toString())) {
                                is UiState.Error -> emit(UiState.Error("Failed get detail: ${resultPlace.errorMessage}"))
                                UiState.Init -> Unit
                                UiState.Loading -> Unit
                                is UiState.Success -> {
                                    output.add(resultPlace.data.data.toRecommendedPlaceModel())
                                }
                            }
                        }

                        emit(UiState.Success(output))
                    }

                    UiState.Init -> Unit
                }
            }


        }.flowOn(dispatcher.io())

    override suspend fun getDetailPlaceById(id: String): Flow<UiState<DetailPlace>> = flow {
        when (val result = placeApi.getDetailPlaceById(id)) {
            is UiState.Error -> emit(UiState.Error(result.errorMessage))
            UiState.Loading -> emit(UiState.Loading)
            is UiState.Success -> emit(UiState.Success(result.data.data))
            UiState.Init -> Unit
        }
    }

    override suspend fun saveUmkmByPlaceId(id: String, data: FormUmkmModel): Flow<UiState<Any>> =
        flow<UiState<Any>> {
            when (val result = placeApi.saveUmkmByPlaceId(id, data)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> emit(UiState.Success(result.data.data))
                UiState.Init -> Unit
            }
        }.flowOn(dispatcher.io())
}