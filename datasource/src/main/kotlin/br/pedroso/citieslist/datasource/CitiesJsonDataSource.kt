package br.pedroso.citieslist.datasource

interface CitiesJsonDataSource {
    suspend fun getCities(): List<JsonCity>
}
