package br.pedroso.citieslist.datasource.rawresourcereader

import android.content.res.Resources
import androidx.annotation.RawRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RawResourceReader(
    private val resources: Resources,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun readResourceAsString(@RawRes rawResource: Int): String = withContext(dispatcher) {
        resources.openRawResource(rawResource).bufferedReader().use { it.readText() }
    }
}
