package br.pedroso.citieslist.features.citiessearch.fakes

import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.repository.CitiesRepository

class AlwaysEmptyFakeCitiesRepository : CitiesRepository {
    override suspend fun getCities(searchQuery: String): List<City> = emptyList()
}
