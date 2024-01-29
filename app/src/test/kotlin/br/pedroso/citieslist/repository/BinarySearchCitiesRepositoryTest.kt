package br.pedroso.citieslist.repository

import br.pedroso.citieslist.domain.datasource.CitiesDataSource
import br.pedroso.citieslist.domain.datasource.dtos.CityDTO
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.repository.fakes.AlwaysEmptyCitiesDataSource
import br.pedroso.citieslist.repository.fakes.AlwaysSuccessfulCitiesDataSource
import br.pedroso.citieslist.repository.fakes.AlwaysThrowingExceptionCitiesDataSource
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BinarySearchCitiesRepositoryTest {

    private val fixture = kotlinFixture()

    private val availableCities: List<City> by lazy {
        val jsonContent = this::class.java.getResource("/cities.json")?.readText().orEmpty()
        Json.decodeFromString<List<CityDTO>>(jsonContent).map { it.mapToEntity() }
    }

    private fun createRepository(cityDataSource: CitiesDataSource) =
        BinarySearchCitiesRepository(cityDataSource, TestCoroutineScope())

    @Test(expected = RuntimeException::class)
    fun `given data source throws an error when getting the cities from repository then same error must be thrown`() =
        runTest {
            createRepository(AlwaysThrowingExceptionCitiesDataSource()).getCities(fixture())
        }

    @Test
    fun `given data source is empty when getting the cities then result must be empty`() =
        runTest {
            val cities = createRepository(AlwaysEmptyCitiesDataSource()).getCities(fixture())

            assertThat(cities).isEmpty()
        }

    @Test
    fun `given the data source is not empty when getting the cities with an empty query then result must be a sorted list with all the cities available`() =
        runTest {
            val dataSource = AlwaysSuccessfulCitiesDataSource(availableCities)

            val citiesFromRepository = createRepository(dataSource).getCities("")

            val expectedResult =
                availableCities.sortedWith(compareBy<City> { it.name }.thenBy { it.countryCode })

            assertThat(citiesFromRepository).isEqualTo(expectedResult)
        }

    @Test
    fun `given the data source is not empty when getting the cities with an invalid query then result must be empty`() =
        runTest {
            val dataSource = AlwaysSuccessfulCitiesDataSource(availableCities)

            val citiesFromRepository = createRepository(dataSource).getCities("qwertyuiopasdfghjkl")

            assertThat(citiesFromRepository).isEmpty()
        }

    @Test
    fun `given the data source is not empty when getting the cities with a valid query then result must be a sorted list with the cities`() =
        runTest {
            val dataSource = AlwaysSuccessfulCitiesDataSource(availableCities)

            val query = "san fran"

            val citiesFromRepository = createRepository(dataSource).getCities(query)

            // cities.json contains 66 occurrences of cities with the prefix "san fran"
            val expectedResult = availableCities
                .filter { city -> city.name.startsWith(query, ignoreCase = true) }
                .sortedWith(compareBy<City> { it.name }.thenBy { it.countryCode })

            assertThat(citiesFromRepository).isEqualTo(expectedResult)
        }

    @Test
    fun `given the data source is not empty when getting the cities with queries with different letter cases then results must be the same`() =
        runTest {
            val dataSource = AlwaysSuccessfulCitiesDataSource(availableCities)

            val repository = createRepository(dataSource)

            val citiesQuery1 = repository.getCities("sAn FrAn")

            val citiesQuery2 = repository.getCities("SaN fRan")

            assertThat(citiesQuery1).isEqualTo(citiesQuery2)
        }

    @Test
    fun `given the data source has one element when getting the cities with valid query then results must be a list with the same element`() =
        runTest {
            val city: City = fixture()

            val dataSource = AlwaysSuccessfulCitiesDataSource(listOf(city))

            val result = createRepository(dataSource).getCities(city.name)

            assertThat(result).isEqualTo(listOf(city))
        }

    @Test
    fun `given the data source is not empty when getting the last element then result must be a sorted list with the cities`() =
        runTest {
            val dataSource = AlwaysSuccessfulCitiesDataSource(availableCities)

            val lastCity = availableCities.last()

            val citiesFromRepository = createRepository(dataSource).getCities(lastCity.name)

            assertThat(citiesFromRepository).isEqualTo(listOf(lastCity))
        }

    @Test
    fun `given the data source is not empty when getting the first element then result must be a sorted list with the cities`() =
        runTest {
            val dataSource = AlwaysSuccessfulCitiesDataSource(availableCities)

            val firstCity = availableCities.first()

            val citiesFromRepository = createRepository(dataSource).getCities(firstCity.name)

            assertThat(citiesFromRepository).isEqualTo(listOf(firstCity))
        }
}
