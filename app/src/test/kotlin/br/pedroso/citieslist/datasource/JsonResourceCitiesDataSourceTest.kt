package br.pedroso.citieslist.datasource

import br.pedroso.citieslist.datasource.JsonResourceCitiesDataSource.FailedToDecodeJsonException
import br.pedroso.citieslist.datasource.rawresourcereader.RawResourceReader
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import br.pedroso.citieslist.domain.datasource.dtos.CoordinatesDTO
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import kotlin.random.Random

class JsonResourceCitiesDataSourceTest {

    private val rawResourceReader: RawResourceReader = mock()

    private fun createDataSource() =
        JsonResourceCitiesDataSource(rawResourceReader, TestCoroutineDispatcher())

    @Before
    fun resetMocks() = reset(rawResourceReader)

    @Test
    fun `given resource reader returns a valid Json string when calling getCities then data source must return a list of cities`() =
        runBlockingTest {
            whenever(rawResourceReader.readResourceAsString(any())).thenReturn(VALID_JSON)

            val citiesDTOs = createDataSource().getCities()

            assertThat(citiesDTOs).isEqualTo(TEST_DTOS)
        }

    @Test(expected = FailedToDecodeJsonException::class)
    fun `given resource reader returns an invalid Json string when calling getCities then data source must return a list of cities`() =
        runBlockingTest {
            whenever(rawResourceReader.readResourceAsString(any())).thenReturn(INVALID_JSON)

            createDataSource().getCities()
        }

    @Test(expected = RuntimeException::class)
    fun `given the resource reader throws an exception when calling getCities then data source must throw the same exception`() =
        runBlockingTest {
            whenever(rawResourceReader.readResourceAsString(any())).thenThrow(RuntimeException())

            createDataSource().getCities()
        }

    companion object {
        private val fixture = kotlinFixture {
            // This factory was necessary to enable Kotlin Fixture generate a non-null
            // CoordinatesDTO. Reference: https://github.com/appmattus/kotlinfixture/issues/77
            factory<CoordinatesDTO> { CoordinatesDTO(Random.nextDouble(), Random.nextDouble()) }
        }

        private val TEST_DTOS = List<CityDTO>(size = 20) { fixture() }
        private val VALID_JSON = Json.encodeToString(TEST_DTOS)
        private const val INVALID_JSON = """[{"country":,"name":,"_id":,"coord":{}},"""
    }
}
