package br.pedroso.citieslist.datasource.test

import br.pedroso.citieslist.datasource.CitiesJsonDataSource
import br.pedroso.citieslist.datasource.JsonCity

/**
 * A fake implementation of [CitiesJsonDataSource] that returns a list of [JsonCity] instances.
 */
class FakeCitiesJsonDataSource(
    private val jsonCities: List<JsonCity> = emptyList(),
) : CitiesJsonDataSource {
    override suspend fun getCities(): List<JsonCity> = jsonCities
}
