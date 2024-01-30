package br.pedroso.citieslist.features.citiessearch.fakes

import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.repository.CitiesRepository

class AlwaysEmptyFakeCitiesRepository : CitiesRepository {
    override suspend fun getCities(searchQuery: String): List<City> = emptyList()
    override suspend fun getCityById(cityId: Int): City = error("This method should not be used.")
}
