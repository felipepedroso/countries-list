package br.pedroso.citieslist.repository

import androidx.paging.PagingData
import br.pedroso.citieslist.entities.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(searchQuery: String): Flow<PagingData<City>>

    fun getStarredCities(): Flow<PagingData<City>>

    fun getCityById(cityId: Int): Flow<City>

    suspend fun updateCityStarredState(
        city: City,
        newStarredState: Boolean,
    )
}
