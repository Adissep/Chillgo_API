package com.capstone.chillgoapp.data.response

data class TopRatingPlaceResponse(
    val id: Long,
    val user_id: Long,
    val place_id: Long,
    val place_rating: Long,
    val place_name: String,
    val category: String,
    val city: String,
    val createdAt: Any? = null,
    val updatedAt: Any? = null,
    val Place: PlaceResponse
)