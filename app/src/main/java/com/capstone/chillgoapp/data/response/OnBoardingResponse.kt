package com.capstone.chillgoapp.data.response

data class OnBoardingResponse (
    val recommended_places: List<RecommendedPlaces>
)

data class RecommendedPlaces (
    val Category: String,
    val City: String,
    val Place_Id: Long,
    val Place_Name: String
)