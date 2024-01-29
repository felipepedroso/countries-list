package br.pedroso.citieslist.jsondatasource

interface CitiesJsonDataSource {
    suspend fun getCities(): List<JsonCity>
}
