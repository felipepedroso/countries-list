package br.pedroso.citieslist.repository

import br.pedroso.citieslist.entities.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    suspend fun getCities(searchQuery: String): List<City>
    fun getCityById(cityId: Int): Flow<City>
}
