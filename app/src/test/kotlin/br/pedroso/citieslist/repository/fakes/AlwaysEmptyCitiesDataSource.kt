package br.pedroso.citieslist.repository.fakes

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.repository.mapToEntity
import com.appmattus.kotlinfixture.kotlinFixture

class AlwaysEmptyCitiesDataSource : CitiesDataSource {
    override suspend fun getCities(): List<CityDTO> = emptyList()
}
