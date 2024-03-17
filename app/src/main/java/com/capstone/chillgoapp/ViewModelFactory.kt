package com.capstone.chillgoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.chillgoapp.data.TravelRepository
import com.capstone.chillgoapp.data.detail.DetailTravelViewModel
import com.capstone.chillgoapp.data.favorite.FavoriteViewModel
import com.capstone.chillgoapp.data.home.HomeViewModel
import com.capstone.chillgoapp.data.more.MoreViewModel

class ViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = TravelRepository.getInstance()

       if (modelClass.isAssignableFrom(DetailTravelViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MoreViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}