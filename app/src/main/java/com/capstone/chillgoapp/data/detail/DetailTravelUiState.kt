package com.capstone.chillgoapp.data.detail

import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.Umkm

data class DetailTravelUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val data: DetailPlace = DetailPlace(
        updatedAt = null,
        description = "",
        review = "",
        rating = .0,
        price = 0,
        place_name = "",
        id = 0,
        longitude = .0,
        latitude = .0,
        image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
        createdAt = null,
        coordinate = "",
        city = "",
        category = "",
        call_number = "",
        schedule_operational = "",
        umkMs = Umkm(
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
        )
    ),
)