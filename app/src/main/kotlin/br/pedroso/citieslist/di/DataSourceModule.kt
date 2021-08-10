package br.pedroso.citieslist.di

import android.content.Context
import br.pedroso.citieslist.datasource.JsonResourceCitiesDataSource
import br.pedroso.citieslist.datasource.rawresourcereader.RawResourceReader
import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideCitiesDataSource(rawResourceReader: RawResourceReader): CitiesDataSource {
        return JsonResourceCitiesDataSource(rawResourceReader)
    }

    @Singleton
    @Provides
    fun provideRawResourceReader(
        @ApplicationContext applicationContext: Context
    ): RawResourceReader {
        return RawResourceReader(applicationContext.resources)
    }
}
