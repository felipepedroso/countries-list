package br.pedroso.citieslist.datasource

import br.pedroso.citieslist.datasource.JsonResourceCitiesDataSource.FailedToDecodeJsonException
import br.pedroso.citieslist.datasource.rawresourcereader.RawResourceReader
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import br.pedroso.citieslist.domain.datasource.dtos.CoordinatesDTO
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.whenever
import kotlin.random.Random

@Ignore("There is a serialization issue that I need to address before enabling this test.")
class JsonResourceCitiesDataSourceTest {

    private val rawResourceReader: RawResourceReader = mock()

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createDataSource() =
        JsonResourceCitiesDataSource(rawResourceReader, testDispatcher)

    @Before
    fun resetMocks() = reset(rawResourceReader)

    @Test
    fun `given resource reader returns a valid Json string when calling getCities then data source must return a list of cities`() =
        runTest {
            whenever(rawResourceReader.readResourceAsString(any())).thenReturn(VALID_JSON)

            val citiesDTOs = createDataSource().getCities()

            testDispatcher.scheduler.advanceUntilIdle()

            assertThat(citiesDTOs).isEqualTo(TEST_DTOS)
        }

    @Test(expected = FailedToDecodeJsonException::class)
    fun `given resource reader returns an invalid Json string when calling getCities then data source must return a list of cities`() =
        runTest {
            whenever(rawResourceReader.readResourceAsString(any())).thenReturn(INVALID_JSON)

            createDataSource().getCities()

            testDispatcher.scheduler.advanceUntilIdle()
        }

    @Test(expected = RuntimeException::class)
    fun `given the resource reader throws an exception when calling getCities then data source must throw the same exception`() =
        runTest {
            whenever(rawResourceReader.readResourceAsString(any())).thenThrow(RuntimeException())

            createDataSource().getCities()

            testDispatcher.scheduler.advanceUntilIdle()
        }

    companion object {
        private val fixture = kotlinFixture {
            // This factory was necessary to enable Kotlin Fixture generate a non-null
            // CoordinatesDTO. Reference: https://github.com/appmattus/kotlinfixture/issues/77
            factory<CoordinatesDTO> { CoordinatesDTO(Random.nextDouble(), Random.nextDouble()) }
        }

        private val TEST_DTOS = List<CityDTO>(size = 20) { fixture() }
        @OptIn(ExperimentalSerializationApi::class)
        private val VALID_JSON = Json.encodeToString(TEST_DTOS)
        private const val INVALID_JSON = """[{"country":,"name":,"_id":,"coord":{}},"""
    }
}
