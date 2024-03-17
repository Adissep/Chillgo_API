package com.capstone.chillgoapp.data.retrofit

import com.capstone.chillgoapp.data.response.BaseResponse
import com.capstone.chillgoapp.data.response.BaseResponseWithPaging
import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.RecommendedPlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.data.response.UmkmResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesServices {
    @GET("places-by-region/{city}")
    suspend fun getPlaceBasedOnCity(
        @Path("city") city: String
    ): Response<BaseResponse<List<PlaceResponse>>>

    @GET("places")
    suspend fun getAllPlace(
        @Query("page") page: Int,
    ): Response<BaseResponseWithPaging<List<PlaceResponse>>>

    @GET("toprating")
    suspend fun getTopRating(): Response<BaseResponse<List<TopRatingPlaceResponse>>>

    @GET("recommended-places")
    suspend fun getRecommendedPlace(): Response<BaseResponse<RecommendedPlaceResponse>>

    @GET("places/{id}")
    suspend fun getDetailPlaceById(
        @Path("id") id: String
    ): Response<BaseResponse<DetailPlace>>

    @GET("umkm")
    suspend fun getAllUmkm(
        @Query("page") page: Int
    ): Response<BaseResponseWithPaging<List<UmkmResponse>>>

    @GET("places/{id}/umkm")
    suspend fun getUmkmById(
        @Path("id") id: Int
    ): Response<BaseResponse<DetailPlace>>


    @Multipart
    @POST("places/{id}/umkm")
    suspend fun saveUmkmByPlaceId(
        @Path("id") placeId: String,
        @Part(value = "umkm_name") umkm_name: RequestBody,
        @Part(value = "umkm_address") umkm_address: RequestBody,
        @Part(value = "category") category: RequestBody,
        @Part(value = "call_number") call_number: RequestBody,
        @Part(value = "product_price") product_price: RequestBody,
        @Part(value = "schedule_operational") schedule_operational: RequestBody,
        @Part(value = "product_description") product_description: RequestBody,
        @Part(value = "umkm_ratings") umkm_ratings: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<BaseResponse<Any>>


}