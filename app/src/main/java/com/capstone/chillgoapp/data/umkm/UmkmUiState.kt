package com.capstone.chillgoapp.data.umkm

import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.Umkm

data class UmkmUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val places:PlaceResponse = PlaceResponse(
        city = "",
        category = "",
        coordinate = "",
        description = "",
        image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
        latitude = .0,
        price = 0L,
        longitude = .0,
        rating = .0,
        schedule_operational = "",
        id = 0L   ,
        umkm = Umkm(
            umkm_name = "",
            city = "",
            category = "",
            coordinate = "",
            description = "",
            image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
            latitude = .0,
            price = 0L,
            longitude = .0,
            rating = .0,
            schedule_operational = "",
            no_telepon = "",
            id = 0L
        ),
        place_name = "",
        review = "",
        call_number = "",
        createdAt = "",
        updatedAt = "",
    ),
    val detailData: Umkm = Umkm(
        umkm_name = "",
        city = "",
        category = "",
        coordinate = "",
        description = "",
        image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
        latitude = .0,
        price = 0L,
        longitude = .0,
        rating = .0,
        schedule_operational = "",
        no_telepon = "",
        id = 0L
    ),
    val listUmkm: List<Umkm> = listOf()
)

data class UmkmFormState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
)