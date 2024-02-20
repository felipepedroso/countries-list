package br.pedroso.citieslist.features.citiessearch.fakes

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.appmattus.kotlinfixture.kotlinFixture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AlwaysSuccessfulFakeCitiesRepository : br.pedroso.citieslist.repository.CitiesRepository {
    override fun getCities(searchQuery: String): Flow<PagingData<br.pedroso.citieslist.domain.City>> =
        MutableStateFlow(
            PagingData.from(
                CITIES_LIST,
                LoadStates(
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                ),
            ),
        )

    override fun getStarredCities(): Flow<PagingData<br.pedroso.citieslist.domain.City>> = error("This method should not be used.")

    override fun getCityById(cityId: Int): Flow<br.pedroso.citieslist.domain.City> = error("This method should not be used.")

    override suspend fun updateCityStarredState(
        city: br.pedroso.citieslist.domain.City,
        newStarredState: Boolean,
    ) = error("This method should not be used.")

    companion object {
        val CITIES_LIST: List<br.pedroso.citieslist.domain.City> = kotlinFixture().invoke()
    }
}
