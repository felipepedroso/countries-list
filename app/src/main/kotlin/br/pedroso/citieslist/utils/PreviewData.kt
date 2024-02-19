package br.pedroso.citieslist.utils

import br.pedroso.citieslist.domain.City
import br.pedroso.citieslist.domain.Coordinates

fun createPreviewCities(count: Int = 10) =
    (1..count).map {
        br.pedroso.citieslist.domain.City(
            name = "City $it",
            countryCode = "BR",
            coordinates = br.pedroso.citieslist.domain.Coordinates(0.0, 0.0),
            id = it,
            isStarred = true,
        )
    }
