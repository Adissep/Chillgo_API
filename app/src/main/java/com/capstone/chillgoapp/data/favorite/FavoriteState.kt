package com.capstone.chillgoapp.data.favorite

import com.capstone.chillgoapp.model.OrderTravel

data class FavoriteState(
    val orderTravel: List<OrderTravel>,
    val totalRequiredPoint: Int
)