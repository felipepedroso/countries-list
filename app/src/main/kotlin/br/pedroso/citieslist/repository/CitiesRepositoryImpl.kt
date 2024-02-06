package br.pedroso.citieslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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
    override fun getCities(searchQuery: String): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(30),
            initialKey = 0,
            pagingSourceFactory = {
                if (searchQuery.isNotEmpty()) {
                    citiesDao.getCitiesByName(searchQuery)
                } else {
                    citiesDao.getAllCities()
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { databaseCity -> databaseCity.toEntity() }
        }
    }

    private fun DatabaseCity.toEntity(): City = City(
        name = name,
        countryCode = countryCode,
        coordinates = Coordinates(latitude, longitude),
        id = id,
        isBookmarked = isBookmarked
    )

    override fun getCityById(cityId: Int): Flow<City> {
        return citiesDao.getCityById(cityId).map { it.toEntity() }
    }

    override suspend fun updateCity(city: City, newStarredState: Boolean) {
        citiesDao.updateCity(city.toDatabaseCity().copy(isBookmarked = newStarredState))
    }

    private fun City.toDatabaseCity() = DatabaseCity(
        id,
        name,
        countryCode,
        coordinates.latitude,
        coordinates.longitude,
        isBookmarked
    )
}