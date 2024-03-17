package com.capstone.chillgoapp.data.local

import androidx.room.TypeConverter
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataConverter {
    @TypeConverter
    @JvmStatic
    fun fromHospitalResponse(data: PlaceResponse?): String {
        if (data == null) return ""
        val gson = Gson()
        return gson.toJson(data)

    }

    @TypeConverter
    @JvmStatic
    fun toHospitalResponse(data: String?): PlaceResponse? {
        if (data == null) return null
        if (data == "") return null
        val gson = Gson()
        return gson.fromJson(data, PlaceResponse::class.java)
    }

}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)