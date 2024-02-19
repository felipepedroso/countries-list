package br.pedroso.citieslist.repository

import androidx.paging.PagingData
import br.pedroso.citieslist.domain.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(searchQuery: String): Flow<PagingData<br.pedroso.citieslist.domain.City>>

    fun getStarredCities(): Flow<PagingData<br.pedroso.citieslist.domain.City>>

    fun getCityById(cityId: Int): Flow<br.pedroso.citieslist.domain.City>

    suspend fun updateCityStarredState(
        city: br.pedroso.citieslist.domain.City,
        newStarredState: Boolean,
    )
}
