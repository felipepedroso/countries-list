package br.pedroso.citieslist.databaseinitialization

import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.database.DatabaseCity
import br.pedroso.citieslist.datasource.CitiesJsonDataSource
import br.pedroso.citieslist.datasource.JsonCity
import javax.inject.Inject

interface InitializeDatabaseUseCase : suspend () -> Unit

/**
 * Initializes the database with the data from the cities.json file.
 */
class InitializeDatabaseUseCaseImpl
    @Inject
    constructor(
        private val citiesDao: CitiesDao,
        private val citiesJsonDataSource: CitiesJsonDataSource,
    ) : InitializeDatabaseUseCase {
        override suspend fun invoke() {
            val cities = citiesJsonDataSource.getCities().map(JsonCity::toDatabaseCity)
            citiesDao.upsertAll(cities)
        }
    }

internal fun JsonCity.toDatabaseCity(): DatabaseCity {
    return DatabaseCity(
        name = name,
        countryCode = country,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
        id = id,
        isStarred = false,
    )
}
