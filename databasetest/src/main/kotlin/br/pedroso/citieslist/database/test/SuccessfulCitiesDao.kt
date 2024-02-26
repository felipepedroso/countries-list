package br.pedroso.citieslist.database.test

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.database.DatabaseCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

/**
 * A fake implementation of [CitiesDao] that stores the data in memory.
 *
 * @param initialCities The initial list of cities to be stored in memory.
 */
class SuccessfulCitiesDao(
    initialCities: List<DatabaseCity> = emptyList(),
) : CitiesDao {
    private val _citiesInMemory = mutableListOf<DatabaseCity>().apply { addAll(initialCities) }

    /**
     * Returns a copy of the list of cities in memory.
     */
    val citiesInMemory: List<DatabaseCity>
        get() = _citiesInMemory.toList()

    override fun getAllCities(): PagingSource<Int, DatabaseCity> {
        return SuccessfulPagingSource(_citiesInMemory)
    }

    override fun getCitiesByName(query: String): PagingSource<Int, DatabaseCity> {
        return SuccessfulPagingSource(_citiesInMemory.filter { it.name.contains(query) })
    }

    override fun getStarredCities(): PagingSource<Int, DatabaseCity> {
        return SuccessfulPagingSource(_citiesInMemory.filter { it.isStarred })
    }

    override fun getCityById(id: Int): Flow<DatabaseCity> {
        return _citiesInMemory.filter { it.id == id }.asFlow()
    }

    override suspend fun upsertAll(cities: List<DatabaseCity>) {
        cities.forEach { city ->
            if (_citiesInMemory.none { it.id == city.id }) {
                _citiesInMemory.add(city)
            } else {
                _citiesInMemory.replaceAll { if (it.id == city.id) city else it }
            }
        }
    }

    override suspend fun updateCity(updatedCity: DatabaseCity) {
        _citiesInMemory.replaceAll { city -> if (city.id == updatedCity.id) updatedCity else city }
    }
}

/**
 * A [PagingSource] that uses a list of [DatabaseCity] as its data source.
 *
 * @param cities The list of cities to be used as the data source.
 */
class SuccessfulPagingSource(
    private val cities: List<DatabaseCity>,
) : PagingSource<Int, DatabaseCity>() {
    override fun getRefreshKey(state: PagingState<Int, DatabaseCity>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DatabaseCity> {
        return LoadResult.Page(
            data = cities,
            prevKey = null,
            nextKey = null,
        )
    }
}
