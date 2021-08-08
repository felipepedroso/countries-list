package br.pedroso.citieslist.di

import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.entities.Coordinates
import br.pedroso.citieslist.domain.repository.CitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.delay
import kotlin.random.Random

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCitiesRepository(): CitiesRepository {
        // Injecting a fake object to test the UI.
        // This will be replaced by a real repository in the future.
        return object : CitiesRepository {
            override suspend fun getCities(searchQuery: String): List<City> {
                delay(2000)
                return (1..1000).map { index ->
                    City(
                        id = index,
                        name = "City $index",
                        countryCode = "XX",
                        coordinates = Coordinates(
                            latitude = Random.nextDouble(-90.0, 90.0),
                            longitude = Random.nextDouble(-180.0, 180.0)
                        )
                    )
                }
            }
        }
    }
}