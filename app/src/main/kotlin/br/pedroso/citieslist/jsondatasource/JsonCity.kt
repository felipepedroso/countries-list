package br.pedroso.citieslist.jsondatasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonCity(
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("coord") val coordinates: JsonCoordinates,
    @SerialName("_id") val id: Int,
)

@Serializable
data class JsonCoordinates(
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double,
)
