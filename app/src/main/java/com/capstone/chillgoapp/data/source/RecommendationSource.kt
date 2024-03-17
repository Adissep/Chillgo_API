package com.capstone.chillgoapp.data.source

import com.capstone.chillgoapp.data.response.OnBoardingResponse
import com.capstone.chillgoapp.data.retrofit.RecommendationServices
import com.capstone.chillgoapp.data.safeApiCall
import com.capstone.chillgoapp.model.RecommendationRequestModel
import com.capstone.chillgoapp.ui.common.UiState
import com.google.android.gms.maps.model.LatLng

interface RecommendationSource {
    suspend fun getRecommendationByCategory(category: String): UiState<OnBoardingResponse>
}

class RecommendationSourceImpl(
    private val apiServices: RecommendationServices
) : RecommendationSource {
    override suspend fun getRecommendationByCategory(category: String) = safeApiCall {
        apiServices.getRecommendationByCategory(
            RecommendationRequestModel(
                category
            )
        )
    }

}