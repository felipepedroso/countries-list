package br.pedroso.citieslist.features.citiessearch.fakes

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.repository.CitiesRepository
import com.appmattus.kotlinfixture.kotlinFixture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AlwaysSuccessfulFakeCitiesRepository : CitiesRepository {
    override fun getCities(searchQuery: String): Flow<PagingData<City>> =
        MutableStateFlow(
            PagingData.from(
                CITIES_LIST,
                LoadStates(
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                )
            )
        )

    override fun getCityById(cityId: Int): Flow<City> = error("This method should not be used.")

    override suspend fun updateCity(city: City, newStarredState: Boolean) =
        error("This method should not be used.")

    companion object {
        val CITIES_LIST: List<City> = kotlinFixture().invoke()
    }
}