package br.pedroso.citieslist.domain.repository

import br.pedroso.citieslist.domain.entities.City

interface CitiesRepository {
    suspend fun getCities(searchQuery: String): List<City>
    suspend fun getCityById(cityId: Int): City
}
