package br.pedroso.citieslist.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideCitiesRepository(citiesDao: br.pedroso.citieslist.database.CitiesDao): br.pedroso.citieslist.repository.CitiesRepository {
        return CitiesRepositoryImpl(citiesDao)
    }
}
