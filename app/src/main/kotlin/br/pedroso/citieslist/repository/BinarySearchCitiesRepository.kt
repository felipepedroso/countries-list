package br.pedroso.citieslist.repository

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.repository.CitiesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class BinarySearchCitiesRepository(
    private val citiesDataSource: CitiesDataSource,
    scope: CoroutineScope = GlobalScope,
) : CitiesRepository {

    override suspend fun getCities(searchQuery: String): List<City> {
        TODO("Not yet implemented")
    }
}
