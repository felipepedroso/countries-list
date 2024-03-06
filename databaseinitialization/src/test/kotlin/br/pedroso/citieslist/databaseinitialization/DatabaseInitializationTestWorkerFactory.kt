package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

/**
 * This is a fake implementation of [WorkerFactory] that always returns a [DatabaseInitializationWorker]
 * with a fake use case.
 */
class DatabaseInitializationTestWorkerFactory(
    private val useCase: InitializeDatabaseUseCase,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker {
        return DatabaseInitializationWorker(appContext, workerParameters, useCase)
    }
}
