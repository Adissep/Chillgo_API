package com.capstone.chillgoapp.data.response

data class UmkmResponse(
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
    val image_url: String,
    val createdAt: Any? = null,
    val updatedAt: Any? = null
)