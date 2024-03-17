package com.capstone.chillgoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.capstone.chillgoapp.model.PlacesDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM tb_place ORDER BY id ASC")
    fun getAllPlaces(): Flow<List<PlacesDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaces(placesDTO: PlacesDTO)

    @Update
    fun updatePlaces(placesDTO: PlacesDTO)

    @Transaction
    suspend fun saveBatch(data: List<PlacesDTO>) {
        data.forEach {
            if (checkExist(it.placeId) == 0) {
                insertPlaces(it)
            } else {
                val placesExist = getByPlaceId(it.placeId)
                updatePlaces(
                    placesExist.copy(
                        city = it.city,
                        rating = it.rating,
                        longitude = it.longitude,
                        latitude = it.latitude,
                        image_url = it.image_url,
                        review = it.review,
                        place_name = it.place_name,
                        placeId = it.placeId,
                    )
                )
            }
        }
    }

    @Query("SELECT COUNT(*) FROM tb_place WHERE placeId = :placeId")
    fun checkExist(placeId: Long?): Int

    @Query("SELECT * FROM tb_place WHERE placeId = :placeId")
    fun getByPlaceId(placeId: Long?): PlacesDTO

    @Query("SELECT * FROM tb_place WHERE id = :id")
    fun  getById(id:Int?) : PlacesDTO?

    @Query("DELETE FROM tb_place")
    fun delete()

}