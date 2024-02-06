package br.pedroso.citieslist.features.citiessearch.fakes

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.repository.CitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AlwaysThrowingExceptionFakeCitiesRepository : CitiesRepository {

    override fun getCities(searchQuery: String): Flow<PagingData<City>> =
        MutableStateFlow(
            PagingData.empty(
                LoadStates(
                    LoadState.Error(EXCEPTION),
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                )
            )
        )

    override fun getStarredCities(): Flow<PagingData<City>> =
        error("This method should not be used.")

    override fun getCityById(cityId: Int): Flow<City> = error("This method should not be used.")

    override suspend fun updateCityStarredState(city: City, newStarredState: Boolean) =
        error("This method should not be used.")

    companion object {
        val EXCEPTION = Throwable("This repository is destined to always fail!")
    }
}
