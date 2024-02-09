package br.pedroso.citieslist.database

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import br.pedroso.citieslist.jsondatasource.CitiesJsonDataSource
import javax.inject.Inject

class SeedCitiesDatabaseWorkerFactory
    @Inject
    constructor(
        private val citiesDao: CitiesDao,
        private val citiesJsonDataSource: CitiesJsonDataSource,
    ) : WorkerFactory() {
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters,
        ): ListenableWorker? {
            return when (workerClassName) {
                SeedCitiesDatabaseWorker::class.java.name ->
                    SeedCitiesDatabaseWorker(
                        context = appContext,
                        workerParams = workerParameters,
                        citiesDao = citiesDao,
                        citiesJsonDataSource = citiesJsonDataSource,
                    )

                else -> null
            }
        }
    }
