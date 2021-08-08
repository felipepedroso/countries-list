package br.pedroso.citieslist.domain.entities

data class City(
    val name: String,
    val countryCode: String,
    val coordinates: Coordinates,
    val id: Int,
)
