package br.pedroso.citieslist.domain.datasource

import br.pedroso.citieslist.domain.datasource.dtos.CityDTO

interface CitiesDataSource {
    suspend fun getCities(): List<CityDTO>
}
