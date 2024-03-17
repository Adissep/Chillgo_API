package com.capstone.chillgoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.capstone.chillgoapp.model.PlacesDTO

@Database(
    entities = [
       PlacesDTO::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DataConverter::class
)
abstract class AppDB : RoomDatabase() {
    abstract fun placeDao(): PlaceDao


    companion object {
        const val DATABASE_NAME = "chillgo_db"
    }
}