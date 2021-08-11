package br.pedroso.citieslist.repository.fakes

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import br.pedroso.citieslist.domain.datasource.dtos.CoordinatesDTO
import br.pedroso.citieslist.domain.entities.City

class AlwaysSuccessfulCitiesDataSource(
    private val cities: List<City>
) : CitiesDataSource {
    override suspend fun getCities(): List<CityDTO> = cities.map { cityEntity: City ->
        val coordinates = cityEntity.coordinates
        CityDTO(
            name = cityEntity.name,
            country = cityEntity.countryCode,
            coordinates = CoordinatesDTO(coordinates.latitude, coordinates.longitude),
            id = cityEntity.id
        )
    }
}
