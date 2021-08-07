package br.pedroso.countrieslist.domain.entities

data class City(
    val name: String,
    val countryCode: String,
    val coordinates: Coordinates,
)
