package com.capstone.chillgoapp.data.retrofit

import com.capstone.chillgoapp.data.response.ReverseGeocodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingServices {

    @GET("reverse-geocode-client")
    suspend fun reverseGeocoding(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("localityLanguage") localityLanguage: String = "en",
    ): Response<ReverseGeocodeResponse>

}