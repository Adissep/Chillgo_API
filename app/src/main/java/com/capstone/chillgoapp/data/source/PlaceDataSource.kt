package com.capstone.chillgoapp.data.source

import com.capstone.chillgoapp.data.response.BaseResponse
import com.capstone.chillgoapp.data.response.BaseResponseWithPaging
import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.RecommendedPlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.data.response.UmkmResponse
import com.capstone.chillgoapp.data.retrofit.PlacesServices
import com.capstone.chillgoapp.data.safeApiCall
import com.capstone.chillgoapp.model.FormUmkmModel
import com.capstone.chillgoapp.ui.common.UiState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

interface PlaceDataSource {
    suspend fun getPlaceBasedOnCity(city: String): UiState<BaseResponse<List<PlaceResponse>>>
    suspend fun getAllPlace(page: Int): UiState<BaseResponseWithPaging<List<PlaceResponse>>>
    suspend fun getTopRating(): UiState<BaseResponse<List<TopRatingPlaceResponse>>>
    suspend fun getRecommendedPlace(): UiState<BaseResponse<RecommendedPlaceResponse>>
    suspend fun getDetailPlaceById(id: String): UiState<BaseResponse<DetailPlace>>
    suspend fun getAllUmkm(page: Int): UiState<BaseResponseWithPaging<List<UmkmResponse>>>
    suspend fun getUmkmById(id: Int): UiState<BaseResponse<DetailPlace>>
    suspend fun saveUmkmByPlaceId(id: String, data: FormUmkmModel): UiState<BaseResponse<Any>>
}

class PlaceDataSourceImpl(
    private val apiServices: PlacesServices
) : PlaceDataSource {
    override suspend fun getPlaceBasedOnCity(city: String) = safeApiCall {
        apiServices.getPlaceBasedOnCity(city)
    }

    override suspend fun getAllPlace(page: Int) = safeApiCall {
        apiServices.getAllPlace(page)
    }

    override suspend fun getTopRating() = safeApiCall {
        apiServices.getTopRating()
    }

    override suspend fun getRecommendedPlace() = safeApiCall {
        apiServices.getRecommendedPlace()
    }

    override suspend fun getDetailPlaceById(id: String) = safeApiCall {
        apiServices.getDetailPlaceById(id)
    }

    override suspend fun getAllUmkm(page: Int) = safeApiCall {
        apiServices.getAllUmkm(page)
    }

    override suspend fun getUmkmById(id: Int) = safeApiCall {
        apiServices.getUmkmById(id)
    }

    override suspend fun saveUmkmByPlaceId(id: String, data: FormUmkmModel) = safeApiCall {
        apiServices.saveUmkmByPlaceId(
            id,
            umkm_name = data.umkm_name.toRequestBody("plain/text".toMediaTypeOrNull()),
            schedule_operational = data.schedule_operational.toRequestBody("plain/text".toMediaTypeOrNull()),
            category = data.category.toRequestBody("plain/text".toMediaTypeOrNull()),
            call_number = data.call_number.toRequestBody("plain/text".toMediaTypeOrNull()),
            product_description = data.product_description.toRequestBody("plain/text".toMediaTypeOrNull()),
            umkm_address = data.umkm_address.toRequestBody("plain/text".toMediaTypeOrNull()),
            umkm_ratings = data.umkm_ratings.toRequestBody("plain/text".toMediaTypeOrNull()),
            product_price = data.product_price.toRequestBody("plain/text".toMediaTypeOrNull()),
            file = MultipartBody.Part.createFormData(
                "file",
                data.file.name,
                data.file.asRequestBody(data.mediaType.toMediaTypeOrNull())
            ),
        )
    }


}