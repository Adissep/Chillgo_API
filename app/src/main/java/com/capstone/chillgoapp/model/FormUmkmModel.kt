package com.capstone.chillgoapp.model

import java.io.File

data class FormUmkmModel(
    val umkm_name: String,
    val umkm_address: String,
    val category: String,
    val call_number: String,
    val product_price: String,
    val schedule_operational: String,
    val product_description: String,
    val umkm_ratings: String,
    val file:File,
    val mediaType:String,
)