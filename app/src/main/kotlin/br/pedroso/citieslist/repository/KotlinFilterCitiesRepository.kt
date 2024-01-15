package br.pedroso.citieslist.repository

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.repository.CitiesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * This class was used only for testing the UI before BinarySearchCitiesRepository was created.
 * I'll leave it here for future reference.
 */
class KotlinFilterCitiesRepository(
    private val citiesDataSource: CitiesDataSource,
    scope: CoroutineScope = GlobalScope,
) : CitiesRepository {

    private val sortedCitiesListDeferred: Deferred<List<City>> =
        scope.async(start = CoroutineStart.LAZY) {
            prepareCitiesList()
        }

    private suspend fun prepareCitiesList(): List<City> {
        return citiesDataSource.getCities()
            .asSequence()
            .map { it.mapToEntity() }
            .sortedWith(compareBy<City> { it.name }.thenBy { it.countryCode })
            .toList()
    }

    override suspend fun getCities(searchQuery: String): List<City> {
        val citiesList = sortedCitiesListDeferred.await()

        return citiesList.filter { city -> city.name.startsWith(searchQuery, ignoreCase = true) }
    }

    override suspend fun getCityById(cityId: Int): City {
        return sortedCitiesListDeferred.await().first { it.id == cityId }
    }
}
