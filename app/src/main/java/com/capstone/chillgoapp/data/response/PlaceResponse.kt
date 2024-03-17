package com.capstone.chillgoapp.data.response

import com.capstone.chillgoapp.model.PlacesDTO
import com.capstone.chillgoapp.model.RecommendedPlaceModel

data class PlaceResponse(
    val id: Long,
    val place_name: String,
    val description: String,
    val category: String,
    val city: String,
    val price: Long,
    val rating: Double,
    val call_number: String,
    val coordinate: String,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
    val review: String,
    val schedule_operational: String,
    val createdAt: Any? = null,
    val updatedAt: Any? = null,
    val umkm: Umkm
) {
    fun toDTO(): PlacesDTO = PlacesDTO(
        placeId = id,
        place_name = place_name,
        review = review,
        image_url = image_url,
        longitude = longitude,
        latitude = latitude,
        rating = rating,
        city = city,
    )
}

data class DetailPlace(
    val id: Long,
    val place_name: String,
    val description: String,
    val category: String,
    val city: String,
    val price: Long,
    val rating: Double,
    val call_number: String,
    val coordinate: String,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
    val review: String,
    val schedule_operational: String,
    val createdAt: Any? = null,
    val updatedAt: Any? = null,
    val umkMs: Umkm
) {
    fun toRecommendedPlaceModel(): RecommendedPlaceModel = RecommendedPlaceModel(
        id = id,
        placePrice = price,
        placeName = place_name,
        placeImageUrl = image_url,
        placeCity = city,
        placeDescription = description,
        placeRating = rating,
    )
}

data class Umkm(
    val id: Long,
    val umkm_name: String,
    val description: String,
    val category: String,
    val city: String,
    val price: Long,
    val rating: Double,
    val no_telepon: String,
    val coordinate: String,
    val latitude: Double,
    val longitude: Double,
    val schedule_operational: String,
    val image_url: String
)