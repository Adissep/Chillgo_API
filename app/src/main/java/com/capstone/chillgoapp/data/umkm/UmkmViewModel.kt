package com.capstone.chillgoapp.data.umkm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.Umkm
import com.capstone.chillgoapp.model.FormUmkmModel
import com.capstone.chillgoapp.ui.common.UiState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UmkmViewModel @Inject constructor(
    private val repository: PlacesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val city: String = savedStateHandle.get<String>("city").orEmpty()
    private val defaultUmkmId: Long = savedStateHandle.get<Long>("umkmId") ?: 0L
    private val defaultPlaceId: Long = savedStateHandle.get<Long>("placeId") ?: 0L

    var umkmName = mutableStateOf("")
    var category = mutableStateOf("")
    var umkmAddress = mutableStateOf("")
    var umkmRating = mutableStateOf("")
    var callNumber = mutableStateOf("")
    var productPrice = mutableStateOf("")
    var scheduleOperational = mutableStateOf("")
    var productDescription = mutableStateOf("")
    var mediaType = mutableStateOf("")
    var file = mutableStateOf<File?>(null)

    private val _umkmState: MutableStateFlow<UmkmUiState> = MutableStateFlow(UmkmUiState())
    val umkmState = _umkmState

    init {
        getDetailUmkm(defaultUmkmId)
    }

    fun toGoogleMaps(context: Context, latLng: LatLng,placeName:String) {
        val gmmIntentUri = Uri.parse("geo:${latLng.latitude},${latLng.longitude}?q=$placeName")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(context, mapIntent, null)

    }


    fun getDetailUmkm(umkmId: Long = defaultUmkmId) = viewModelScope.launch {
        repository.getPlaceBasedOnCity(city).onEach {
            when (it) {
                is UiState.Error -> _umkmState.emit(
                    UmkmUiState(
                        error = true,
                        message = it.errorMessage
                    )
                )

                UiState.Init -> Unit
                UiState.Loading -> _umkmState.emit(UmkmUiState(loading = true))
                is UiState.Success -> {

                    _umkmState.emit(
                        UmkmUiState(
                            detailData = it.data.find { response -> response.id == umkmId }?.umkm
                                ?: Umkm(
                                    umkm_name = "",
                                    city = "",
                                    category = "",
                                    coordinate = "",
                                    description = "",
                                    image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
                                    latitude = .0,
                                    price = 0L,
                                    longitude = .0,
                                    rating = .0,
                                    schedule_operational = "",
                                    no_telepon = "",
                                    id = 0L
                                ),
                            listUmkm = it.data.map { data -> data.umkm }
                                .filter { response -> response.id != umkmId },
                            places = it.data.find {
                                it.id == defaultPlaceId
                            } ?: PlaceResponse(
                                city = "",
                                category = "",
                                coordinate = "",
                                description = "",
                                image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
                                latitude = .0,
                                price = 0L,
                                longitude = .0,
                                rating = .0,
                                schedule_operational = "",
                                id = 0L,
                                umkm = Umkm(
                                    umkm_name = "",
                                    city = "",
                                    category = "",
                                    coordinate = "",
                                    description = "",
                                    image_url = "https://oneshaf.com/wp-content/uploads/2022/12/placeholder-5.png",
                                    latitude = .0,
                                    price = 0L,
                                    longitude = .0,
                                    rating = .0,
                                    schedule_operational = "",
                                    no_telepon = "",
                                    id = 0L
                                ),
                                place_name = "",
                                review = "",
                                call_number = "",
                                createdAt = "",
                                updatedAt = "",
                            )
                        )

                    )
                }
            }
        }.collect()
    }

    fun saveUmkm(context: Context) = viewModelScope.launch {
        if (
            umkmName.value.isNotBlank() ||
            category.value.isNotBlank() ||
            umkmAddress.value.isNotBlank() ||
            umkmRating.value.isNotBlank() ||
            callNumber.value.isNotBlank() ||
            productPrice.value.isNotBlank() ||
            scheduleOperational.value.isNotBlank() ||
            productDescription.value.isNotBlank() ||
            mediaType.value.isNotBlank() ||
            file.value != null
        ) {
            repository.saveUmkmByPlaceId(
                defaultPlaceId.toString(), FormUmkmModel(
                    umkm_name = umkmName.value,
                    umkm_address = umkmAddress.value,
                    category = category.value,
                    umkm_ratings = umkmRating.value,
                    file = file.value!!,
                    call_number = callNumber.value,
                    product_description = productDescription.value,
                    schedule_operational = scheduleOperational.value,
                    mediaType = mediaType.value,
                    product_price = productPrice.value,
                )
            ).onEach {

            }.collect()
        } else {
            Toast.makeText(
                context,
                "Field Cannot Empty, All Filed are required.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}