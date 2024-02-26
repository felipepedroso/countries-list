package br.pedroso.citieslist.database.test

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.database.DatabaseCity
import kotlinx.coroutines.flow.Flow

/**
 * A fake implementation of [CitiesDao] that always fails when any method is called.
 *
 * @param exception The exception to be thrown when any method is called.
 */
class FailingCitiesDao(
    private val exception: Throwable,
) : CitiesDao {
    override fun getAllCities(): PagingSource<Int, DatabaseCity> = FailingPagingSource(exception)

    override fun getCitiesByName(query: String): PagingSource<Int, DatabaseCity> = FailingPagingSource(exception)

    override fun getStarredCities(): PagingSource<Int, DatabaseCity> = FailingPagingSource(exception)

    override fun getCityById(id: Int): Flow<DatabaseCity> = throw exception

    override suspend fun upsertAll(cities: List<DatabaseCity>) = throw exception

    override suspend fun updateCity(updatedCity: DatabaseCity) = throw exception
}

/**
 * A [PagingSource] that always returns an error as its load result.
 *
 * @param exception The exception to be returned as the load result.
 */
class FailingPagingSource(
    private val exception: Throwable,
) : PagingSource<Int, DatabaseCity>() {
    override fun getRefreshKey(state: PagingState<Int, DatabaseCity>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DatabaseCity> {
        return LoadResult.Error(exception)
    }
}
