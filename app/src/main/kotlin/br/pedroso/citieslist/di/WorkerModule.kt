package br.pedroso.citieslist.di

import androidx.work.Configuration
import br.pedroso.citieslist.database.SeedCitiesDatabaseWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {
    @Singleton
    @Provides
    fun provideWorkerConfiguration(seedCitiesDatabaseWorkerFactory: SeedCitiesDatabaseWorkerFactory): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(seedCitiesDatabaseWorkerFactory)
            .build()
    }
}
