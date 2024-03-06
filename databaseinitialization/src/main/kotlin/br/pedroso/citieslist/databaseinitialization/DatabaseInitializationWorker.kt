package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DatabaseInitializationWorker
    @AssistedInject
    constructor(
        @Assisted context: Context,
        @Assisted workerParams: WorkerParameters,
        private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
    ) : CoroutineWorker(context, workerParams) {
        override suspend fun doWork(): Result {
            return try {
                initializeDatabaseUseCase()
                Result.success()
            } catch (ex: Exception) {
                Result.failure(
                    Data.Builder().putString(ERROR_MESSAGE_KEY, ex.message).build(),
                )
            }
        }

        companion object {
            const val ERROR_MESSAGE_KEY = "ERROR_MESSAGE_KEY"
        }
    }
