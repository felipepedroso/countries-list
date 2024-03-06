package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.testing.TestListenableWorkerBuilder

/**
 * This function creates a [DatabaseInitializationWorker] instance.
 */
fun createDatabaseInitializationWorker(useCase: InitializeDatabaseUseCase): DatabaseInitializationWorker {
    val context = ApplicationProvider.getApplicationContext<Context>()
    return TestListenableWorkerBuilder<DatabaseInitializationWorker>(context = context)
        .setWorkerFactory(DatabaseInitializationTestWorkerFactory(useCase))
        .build()
}
