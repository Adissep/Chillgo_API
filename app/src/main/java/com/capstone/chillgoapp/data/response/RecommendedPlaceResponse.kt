package com.capstone.chillgoapp.data.response

data class RecommendedPlaceResponse (
    val recommendedPlaces: List<RecommendedPlace>
)

data class RecommendedPlace (
    val place_id: Long,
    val place_name: String,
    val place_rating: Long,
    val image_url:String,
    val rating: List<Rating>
)

data class Rating (
    val rating: Double
)