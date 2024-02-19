package br.pedroso.citieslist.entities

import androidx.compose.runtime.Stable

@Stable
data class City(
    val name: String,
    val countryCode: String,
    val coordinates: Coordinates,
    val id: Int,
    val isStarred: Boolean,
)
