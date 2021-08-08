package br.pedroso.citieslist.features.citiessearch.fakes

import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.repository.CitiesRepository

class AlwaysThrowingExceptionFakeCitiesRepository : CitiesRepository {

    override suspend fun getCities(searchQuery: String): List<City> = throw EXCEPTION

    companion object {
        val EXCEPTION = Throwable("This repository is destined to always fail!")
    }
}
