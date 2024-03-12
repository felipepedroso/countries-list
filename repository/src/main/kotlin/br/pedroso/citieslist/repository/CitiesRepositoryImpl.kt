package br.pedroso.citieslist.repository

import androidx.paging.PagingData
import br.pedroso.citieslist.databaseinitialization.DatabaseInitializationManager
import br.pedroso.citieslist.domain.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CitiesRepositoryImpl(
    private val citiesDao: br.pedroso.citieslist.database.CitiesDao,
    private val databaseInitializationManager: DatabaseInitializationManager,
) : CitiesRepository {
    override fun getCities(searchQuery: String): Flow<PagingData<City>> {
        return combinePagedCitiesAndDatabaseInitializationFlows(
            citiesPagingDataFlow =
                databasePagerFlowFactory {
                    if (searchQuery.isNotEmpty()) {
                        citiesDao.getCitiesByName(searchQuery)
                    } else {
                        citiesDao.getAllCities()
                    }
                },
            databaseInitializationFlow = databaseInitializationManager.isInitializingDatabase,
        )
    }

    override fun getStarredCities(): Flow<PagingData<City>> {
        return combinePagedCitiesAndDatabaseInitializationFlows(
            citiesPagingDataFlow =
                databasePagerFlowFactory {
                    citiesDao.getStarredCities()
                },
            databaseInitializationFlow = databaseInitializationManager.isInitializingDatabase,
        )
    }

    override fun getCityById(cityId: Int): Flow<City> {
        return citiesDao.getCityById(cityId).map { it.toEntity() }
    }

    override suspend fun updateCityStarredState(
        city: City,
        newStarredState: Boolean,
    ) {
        citiesDao.updateCity(city.toDatabaseCity().copy(isStarred = newStarredState))
    }
}
