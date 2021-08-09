package br.pedroso.citieslist.domain.datasource.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDTO(
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("coord") val coordinates: CoordinatesDTO,
    @SerialName("_id") val id: Int
)
