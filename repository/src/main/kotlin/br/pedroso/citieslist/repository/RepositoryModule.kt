package br.pedroso.citieslist.repository

import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.databaseinitialization.DatabaseInitializationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideCitiesRepository(
        citiesDao: CitiesDao,
        databaseInitializationManager: DatabaseInitializationManager,
    ): CitiesRepository {
        return CitiesRepositoryImpl(citiesDao, databaseInitializationManager)
    }
}
