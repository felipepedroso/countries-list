package br.pedroso.citieslist.domain

data class City(
    val name: String,
    val countryCode: String,
    val coordinates: Coordinates,
    val id: Int,
    val isStarred: Boolean,
)
