package br.pedroso.citieslist.repository.fakes

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import java.lang.RuntimeException

class AlwaysThrowingExceptionCitiesDataSource : CitiesDataSource {
    override suspend fun getCities(): List<CityDTO> = throw EXCEPTION

    companion object {
        val EXCEPTION = RuntimeException("This data source is destined to always fail!")
    }
}
