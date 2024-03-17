package com.capstone.chillgoapp.model


data class RecommendedPlaceModel(
    val id: Long,
    val placeName: String,
    val placeImageUrl: String,
    val placePrice: Long,
    val placeCity:String,
    val placeDescription:String,
    val placeRating:Double,
)