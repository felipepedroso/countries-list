package br.pedroso.citieslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.database.DatabaseCity
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.entities.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CitiesRepositoryImpl(
    private val citiesDao: CitiesDao,
) : CitiesRepository {
    override fun getCities(searchQuery: String): Flow<PagingData<City>> = databasePagerFlowFactory {
        if (searchQuery.isNotEmpty()) {
            citiesDao.getCitiesByName(searchQuery)
        } else {
            citiesDao.getAllCities()
        }
    }

    override fun getStarredCities(): Flow<PagingData<City>> = databasePagerFlowFactory {
        citiesDao.getStarredCities()
    }

    private fun DatabaseCity.toEntity(): City = City(
        name = name,
        countryCode = countryCode,
        coordinates = Coordinates(latitude, longitude),
        id = id,
        isStarred = isStarred
    )

    override fun getCityById(cityId: Int): Flow<City> {
        return citiesDao.getCityById(cityId).map { it.toEntity() }
    }

    override suspend fun updateCityStarredState(city: City, newStarredState: Boolean) {
        citiesDao.updateCity(city.toDatabaseCity().copy(isStarred = newStarredState))
    }

    private fun City.toDatabaseCity() = DatabaseCity(
        id,
        name,
        countryCode,
        coordinates.latitude,
        coordinates.longitude,
        isStarred
    )

    private fun databasePagerFlowFactory(
        pageSize: Int = 30,
        initialKey: Int = 0,
        pagingSourceFactory: () -> PagingSource<Int, DatabaseCity>
    ): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(pageSize),
            initialKey = initialKey,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { databaseCity -> databaseCity.toEntity() }
        }
    }
}