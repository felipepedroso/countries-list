package br.pedroso.citieslist.repository

import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import br.pedroso.citieslist.domain.datasource.dtos.CoordinatesDTO
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.entities.Coordinates

fun CityDTO.mapToEntity() = City(name, country, coordinates.mapToEntity(), id)

private fun CoordinatesDTO.mapToEntity() = Coordinates(latitude, longitude)
