package br.pedroso.citieslist.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.pedroso.citieslist.jsondatasource.CitiesJsonDataSource
import br.pedroso.citieslist.jsondatasource.JsonCity

class SeedCitiesDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val citiesDao: CitiesDao,
    private val citiesJsonDataSource: CitiesJsonDataSource,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val cities = citiesJsonDataSource.getCities()
                .map { it.toDatabaseCity() }

            citiesDao.upsertAll(cities)

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    private fun JsonCity.toDatabaseCity(): DatabaseCity {
        return DatabaseCity(
            name = name,
            countryCode = country,
            latitude = coordinates.latitude,
            longitude = coordinates.longitude,
            id = id,
            isStarred = false
        )
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}