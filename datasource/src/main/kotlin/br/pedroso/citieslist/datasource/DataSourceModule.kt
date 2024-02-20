package br.pedroso.citieslist.datasource

import android.content.Context
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
        return AssetsCitiesJsonDataSource(applicationContext)
    }
}
