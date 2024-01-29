package br.pedroso.citieslist.repository

import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.database.DatabaseCity
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.entities.Coordinates
import br.pedroso.citieslist.domain.repository.CitiesRepository

class CitiesRepositoryImpl(
    private val citiesDao: CitiesDao,
) : CitiesRepository {
    override suspend fun getCities(searchQuery: String): List<City> {
        return if (searchQuery.isNotEmpty()) {
            citiesDao.getCitiesByName(searchQuery)
        } else {
            citiesDao.getAllCities()
        }.map { it.toEntity() }
    }

    private fun DatabaseCity.toEntity(): City {
        return City(
            name = name,
            countryCode = countryCode,
            coordinates = Coordinates(latitude, longitude),
            id = id
        )
    }

    override suspend fun getCityById(cityId: Int): City {
        return citiesDao.getCityById(cityId).toEntity()
    }
}