package com.capstone.chillgoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.chillgoapp.data.response.Umkm


@Entity("tb_place")
data class PlacesDTO(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val placeId: Long,
    val place_name: String,
    val city: String,
    val rating: Double,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
    val review: String,
)