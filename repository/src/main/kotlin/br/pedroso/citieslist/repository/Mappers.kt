package br.pedroso.citieslist.repository

import br.pedroso.citieslist.database.DatabaseCity
import br.pedroso.citieslist.domain.City
import br.pedroso.citieslist.domain.Coordinates

internal fun City.toDatabaseCity() = DatabaseCity(
    id = id,
    name = name,
    countryCode = countryCode,
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    isStarred = isStarred,
)

internal fun DatabaseCity.toEntity(): City = City(
    name = name,
    countryCode = countryCode,
    coordinates = Coordinates(latitude, longitude),
    id = id,
    isStarred = isStarred,
)
