package br.pedroso.citieslist.jsondatasource

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AssetsJsonCitiesJsonDataSource(
    private val applicationContext: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CitiesJsonDataSource {
    override suspend fun getCities(): List<JsonCity> {
        return withContext(dispatcher) {
            try {
                applicationContext.assets.open("cities.json").use { inputStream ->
                    inputStream.bufferedReader().use {
                        Json.decodeFromString<List<JsonCity>>(it.readText())
                    }
                }
            } catch (error: Throwable) {
                throw FailedToDecodeJsonException(error)
            }
        }
    }

    class FailedToDecodeJsonException(cause: Throwable) : Throwable(cause)
}
