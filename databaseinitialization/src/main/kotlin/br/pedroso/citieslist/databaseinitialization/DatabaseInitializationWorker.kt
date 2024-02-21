package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DatabaseInitializationWorker
    @AssistedInject
    constructor(
        @Assisted context: Context,
        @Assisted workerParams: WorkerParameters,
        private val initializeDatabaseWithJsonData: InitializeDatabaseWithJsonData,
    ) : CoroutineWorker(context, workerParams) {
        override suspend fun doWork(): Result {
            return try {
                initializeDatabaseWithJsonData()
                Result.success()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }
