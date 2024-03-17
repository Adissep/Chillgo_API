package com.capstone.chillgoapp.data

import androidx.annotation.Keep
import com.capstone.chillgoapp.ui.common.UNKNOWN_NETWORK_EXCEPTION
import com.capstone.chillgoapp.ui.common.UiState
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): UiState<T> {

    try {
        val response = call.invoke()
        if (response.code() in 200..209) {
            return UiState.Success((response.body()) as T)
        }
        if (response.code() in 400..500) {
            val gson = Gson()
            val json = response.errorBody()?.string()
            val contentType = response.raw().body?.contentType()
            if (contentType != null && contentType.subtype == "html") {
                return UiState.Error("Internal server error")
            }
            if (json.isNullOrBlank()) {
                return UiState.Error("Failed to authenticate")
            }
            val error = gson.fromJson(json, ErrorBody::class.java)
            return UiState.Error(errorMessage = error.message.toString())
        }
        return UiState.Error(response.message())
    } catch (e: Exception) {
        return when (e) {
            is IOException -> {
                UiState.Error(e.message ?: "")
            }

            is HttpException -> {
                UiState.Error(UNKNOWN_NETWORK_EXCEPTION)
            }

            else -> {
                UiState.Error(e.message ?: "")
            }
        }
    }
}

@Keep
data class ErrorBody(
    var message: String? = ""
)