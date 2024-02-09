package br.pedroso.citieslist.di

import br.pedroso.citieslist.database.CitiesDao
import br.pedroso.citieslist.repository.CitiesRepository
import br.pedroso.citieslist.repository.CitiesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideCitiesRepository(citiesDao: CitiesDao): CitiesRepository {
        return CitiesRepositoryImpl(citiesDao)
    }
}
