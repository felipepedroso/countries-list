package br.pedroso.citieslist.repository

import br.pedroso.citieslist.entities.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(searchQuery: String): Flow<List<City>>
    fun getCityById(cityId: Int): Flow<City>
}
