package br.pedroso.citieslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.database.DatabaseCity
import br.pedroso.citieslist.domain.City
import br.pedroso.citieslist.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CitiesRepositoryImpl(
    private val citiesDao: br.pedroso.citieslist.database.CitiesDao,
) : CitiesRepository {
    override fun getCities(searchQuery: String): Flow<PagingData<br.pedroso.citieslist.domain.City>> =
        databasePagerFlowFactory {
            if (searchQuery.isNotEmpty()) {
                citiesDao.getCitiesByName(searchQuery)
            } else {
                citiesDao.getAllCities()
            }
        }

    override fun getStarredCities(): Flow<PagingData<br.pedroso.citieslist.domain.City>> =
        databasePagerFlowFactory {
            citiesDao.getStarredCities()
        }

    private fun br.pedroso.citieslist.database.DatabaseCity.toEntity(): br.pedroso.citieslist.domain.City =
        br.pedroso.citieslist.domain.City(
            name = name,
            countryCode = countryCode,
            coordinates = br.pedroso.citieslist.domain.Coordinates(latitude, longitude),
            id = id,
            isStarred = isStarred,
        )

    override fun getCityById(cityId: Int): Flow<br.pedroso.citieslist.domain.City> {
        return citiesDao.getCityById(cityId).map { it.toEntity() }
    }

    override suspend fun updateCityStarredState(
        city: br.pedroso.citieslist.domain.City,
        newStarredState: Boolean,
    ) {
        citiesDao.updateCity(city.toDatabaseCity().copy(isStarred = newStarredState))
    }

    private fun br.pedroso.citieslist.domain.City.toDatabaseCity() =
        br.pedroso.citieslist.database.DatabaseCity(
            id,
            name,
            countryCode,
            coordinates.latitude,
            coordinates.longitude,
            isStarred,
        )

    private fun databasePagerFlowFactory(
        pageSize: Int = 30,
        initialKey: Int = 0,
        pagingSourceFactory: () -> PagingSource<Int, br.pedroso.citieslist.database.DatabaseCity>,
    ): Flow<PagingData<br.pedroso.citieslist.domain.City>> {
        return Pager(
            config = PagingConfig(pageSize),
            initialKey = initialKey,
            pagingSourceFactory = pagingSourceFactory,
        ).flow.map { pagingData ->
            pagingData.map { databaseCity -> databaseCity.toEntity() }
        }
    }
}
