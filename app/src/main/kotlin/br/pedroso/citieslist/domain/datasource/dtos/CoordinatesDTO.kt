package br.pedroso.citieslist.domain.datasource.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesDTO(
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double,
)
