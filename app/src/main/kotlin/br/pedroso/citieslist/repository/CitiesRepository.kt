package br.pedroso.citieslist.repository

import br.pedroso.citieslist.entities.City

interface CitiesRepository {
    suspend fun getCities(searchQuery: String): List<City>
    suspend fun getCityById(cityId: Int): City
}
