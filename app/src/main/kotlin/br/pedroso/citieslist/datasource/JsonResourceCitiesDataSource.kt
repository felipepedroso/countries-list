package br.pedroso.citieslist.datasource

import br.pedroso.citieslist.datasource.rawresourcereader.RawResourceReader
import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class JsonResourceCitiesDataSource(
    private val rawResourceReader: RawResourceReader,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : CitiesDataSource {

    override suspend fun getCities(): List<CityDTO> {
        TODO()
    }

    class FailedToDecodeJsonException(cause: Throwable) : Throwable(cause)
}
