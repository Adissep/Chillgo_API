package com.capstone.chillgoapp.data

import android.util.Log
import com.capstone.chillgoapp.model.FakeTravelDataSource
import com.capstone.chillgoapp.model.OrderTravel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class TravelRepository private constructor() {

    private val orderTravels = mutableListOf<OrderTravel>()

    init {
        Log.d("TAG", "INIT")
        if (orderTravels.isEmpty()) {
            FakeTravelDataSource.dummyTravels.forEach {
                orderTravels.add(OrderTravel(it, 0))
            }
            FakeTravelDataSource.dummyBestTravel.forEach {
                orderTravels.add(OrderTravel(it, 0))
            }
        }
    }

    fun getAllTravels(): Flow<List<OrderTravel>> {
        return flowOf(orderTravels)
    }

    fun getOrderTravelById(travelId: Long): OrderTravel {
        return orderTravels.first {
            it.travel.id == travelId
        }
    }

    fun updateOrderTravel(travelId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderTravels.indexOfFirst { it.travel.id == travelId }

        val result = if (index >= 0) {
            val orderTravel = orderTravels[index]
            orderTravels[index] =
                orderTravel.copy(travel = orderTravel.travel, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderTravels(): Flow<List<OrderTravel>> {
        return getAllTravels()
            .map { orderTravels ->
                orderTravels.filter { order ->
                    order.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: TravelRepository? = null

        fun getInstance(): TravelRepository =
            instance ?: synchronized(this) {
                TravelRepository().apply {
                    instance = this
                }
            }
    }
}