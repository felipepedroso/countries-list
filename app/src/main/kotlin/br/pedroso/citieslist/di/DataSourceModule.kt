package br.pedroso.citieslist.di

import android.content.Context
import br.pedroso.citieslist.jsondatasource.AssetsJsonCitiesJsonDataSource
import br.pedroso.citieslist.jsondatasource.CitiesJsonDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideCitiesDataSource(
        @ApplicationContext applicationContext: Context,
    ): CitiesJsonDataSource {
        return AssetsJsonCitiesJsonDataSource(applicationContext)
    }
}
