package br.pedroso.citieslist.datasource

import br.pedroso.citieslist.R
import br.pedroso.citieslist.datasource.rawresourcereader.RawResourceReader
import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonResourceCitiesDataSource(
    private val rawResourceReader: RawResourceReader,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : CitiesDataSource {

    override suspend fun getCities(): List<CityDTO> {
        val resourceContent = rawResourceReader.readResourceAsString(R.raw.cities)

        return withContext(dispatcher) {
            runCatching { Json.decodeFromString<List<CityDTO>>(resourceContent) }
                .onFailure { error -> throw FailedToDecodeJsonException(error) }
                .getOrThrow()
        }
    }

    class FailedToDecodeJsonException(cause: Throwable) : Throwable(cause)
}
