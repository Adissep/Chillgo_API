package com.capstone.chillgoapp.data.response

data class ReverseGeocodeResponse (
    val latitude: Double,
    val lookupSource: String,
    val longitude: Double,
    val localityLanguageRequested: String,
    val continent: String,
    val continentCode: String,
    val countryName: String,
    val countryCode: String,
    val principalSubdivision: String,
    val principalSubdivisionCode: String,
    val city: String,
    val locality: String,
    val postcode: String,
    val plusCode: String,
    val localityInfo: LocalityInfo
)

data class LocalityInfo (
    val administrative: List<Ative>,
    val informative: List<Ative>
)

data class Ative (
    val name: String,
    val description: String,
    val isoName: String? = null,
    val order: Long,
    val adminLevel: Long? = null,
    val isoCode: String? = null,
    val wikidataId: String? = null,
    val geonameId: Long? = null
)
