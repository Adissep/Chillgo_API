package com.capstone.chillgoapp.data.response

data class BaseResponse<T>(
    val error: Boolean,
    val message: String,
    val data: T
)

data class BaseResponseWithPaging<T>(
    val error: Boolean,
    val message: String,
    val data: T,
    val hashNextPage: Boolean,
    val nextPage: Int? = null,
)