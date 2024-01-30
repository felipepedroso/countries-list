package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnCity
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnRetry
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.SearchQueryChanged
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.DisplayCitiesList
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Empty
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Error
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Loading
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.features.citiessearch.fakes.AlwaysEmptyFakeCitiesRepository
import br.pedroso.citieslist.features.citiessearch.fakes.AlwaysSuccessfulFakeCitiesRepository
import br.pedroso.citieslist.features.citiessearch.fakes.AlwaysThrowingExceptionFakeCitiesRepository
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CitiesSearchViewModelTest {

    private val fixture = kotlinFixture()

    private var testCoroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        testCoroutineDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given repository call is hanging when screen is created then view must display the loading state`() {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        assertThat(viewModel.viewStateFlow.value).isInstanceOf(Loading::class.java)
    }

    @Test
    fun `given repository returns an empty list of cities when screen is created then view must display the empty state`() {
        val viewModel = CitiesSearchViewModel(AlwaysEmptyFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.viewStateFlow.value).isInstanceOf(Empty::class.java)
    }

    @Test
    fun `given repository throws an exception when screen is created then view must display the error state`() {
        val viewModel = CitiesSearchViewModel(AlwaysThrowingExceptionFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateFlow.value as Error

        assertThat(state.error).isEqualTo(AlwaysThrowingExceptionFakeCitiesRepository.EXCEPTION)
    }

    @Test
    fun `given repository returns a list of cities when screen is created then view must display the same list`() {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateFlow.value as DisplayCitiesList

        assertThat(state.cities).isEqualTo(AlwaysSuccessfulFakeCitiesRepository.CITIES_LIST)
    }

    @Test
    fun `given repository call is hanging when the search query changes then view must display the loading state`() {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        assertThat(viewModel.viewStateFlow.value).isInstanceOf(Loading::class.java)
    }

    @Test
    fun `given repository returns an empty list of cities when the search query changes then view must display the empty state`() {
        val viewModel = CitiesSearchViewModel(AlwaysEmptyFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.viewStateFlow.value).isInstanceOf(Empty::class.java)
    }

    @Test
    fun `given repository throws an exception when the search query changes then view must display the error state`() {
        val viewModel = CitiesSearchViewModel(AlwaysThrowingExceptionFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateFlow.value as Error

        assertThat(state.error).isEqualTo(AlwaysThrowingExceptionFakeCitiesRepository.EXCEPTION)
    }

    @Test
    fun `given repository returns a list of cities when the search query changes then view must display the same list`() {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        viewModel.onViewEvent(SearchQueryChanged(fixture()))

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateFlow.value as DisplayCitiesList

        assertThat(state.cities).isEqualTo(AlwaysSuccessfulFakeCitiesRepository.CITIES_LIST)
    }

    @Test
    fun `when user clicks on city then view must navigate to maps screen`() = runTest {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        val city: City = fixture()

        viewModel.onViewEvent(ClickedOnCity(city))

        val event = viewModel.viewModelEventFlow.first() as NavigateToMapScreen

        assertThat(event.cityToFocus).isEqualTo(city)
    }

    @Test
    fun `given repository call is hanging when user clicked on retry then view must display the loading state`() {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        viewModel.onViewEvent(ClickedOnRetry)

        assertThat(viewModel.viewStateFlow.value).isInstanceOf(Loading::class.java)
    }

    @Test
    fun `given repository returns an empty list of cities when user clicked on retry then view must display the empty state`() {
        val viewModel = CitiesSearchViewModel(AlwaysEmptyFakeCitiesRepository())

        viewModel.onViewEvent(ClickedOnRetry)

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.viewStateFlow.value).isInstanceOf(Empty::class.java)
    }

    @Test
    fun `given repository throws an exception when user clicked on retry then view must display the error state`() {
        val viewModel = CitiesSearchViewModel(AlwaysThrowingExceptionFakeCitiesRepository())

        viewModel.onViewEvent(ClickedOnRetry)

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateFlow.value as Error

        assertThat(state.error).isEqualTo(AlwaysThrowingExceptionFakeCitiesRepository.EXCEPTION)
    }

    @Test
    fun `given repository returns a list of cities when user clicked on retry then view must display the same list`() {
        val viewModel = CitiesSearchViewModel(AlwaysSuccessfulFakeCitiesRepository())

        viewModel.onViewEvent(ClickedOnRetry)

        testCoroutineDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateFlow.value as DisplayCitiesList

        assertThat(state.cities).isEqualTo(AlwaysSuccessfulFakeCitiesRepository.CITIES_LIST)
    }
}
