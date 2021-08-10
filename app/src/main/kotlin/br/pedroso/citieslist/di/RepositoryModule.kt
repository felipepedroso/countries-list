package br.pedroso.citieslist.di

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.repository.CitiesRepository
import br.pedroso.citieslist.repository.KotlinFilterCitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCitiesRepository(citiesDataSource: CitiesDataSource): CitiesRepository {
        return KotlinFilterCitiesRepository(citiesDataSource)
    }
}