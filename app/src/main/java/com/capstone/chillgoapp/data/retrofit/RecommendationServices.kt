package com.capstone.chillgoapp.data.retrofit

import com.capstone.chillgoapp.data.response.OnBoardingResponse
import com.capstone.chillgoapp.model.RecommendationRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationServices {
    @POST("onboarding")
    suspend fun getRecommendationByCategory(
        @Body category: RecommendationRequestModel
    ): Response<OnBoardingResponse>
}