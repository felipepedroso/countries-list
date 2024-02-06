package br.pedroso.citieslist.utils

import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.entities.Coordinates

fun createPreviewCities(count: Int = 10) = (1..count).map {
    City(
        name = "City $it",
        countryCode = "BR",
        coordinates = Coordinates(0.0, 0.0),
        id = it,
        isStarred = true
    )
}