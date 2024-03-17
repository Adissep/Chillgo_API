package com.capstone.chillgoapp.data.profile

import com.capstone.chillgoapp.data.response.PlaceResponse

data class ProfileStateUi(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val userName:String = "",
    val email: String = "",
)