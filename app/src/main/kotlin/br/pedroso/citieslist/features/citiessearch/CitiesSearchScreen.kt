package br.pedroso.citieslist.features.citiessearch

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.pedroso.citieslist.R
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.entities.Coordinates
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnCity
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnClearQuery
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnRetry
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.SearchQueryChanged
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.DisplayCitiesList
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Empty
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Error
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Loading
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.ui.components.CitiesList
import br.pedroso.citieslist.ui.components.ErrorState
import br.pedroso.citieslist.ui.components.LoadingState
import br.pedroso.citieslist.ui.theme.CitiesListTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CitiesSearchScreen(
    viewModel: CitiesSearchViewModel,
    modifier: Modifier = Modifier,
    openCityOnMap: (city: City) -> Unit = {},
) {
    val uiState by viewModel.viewStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.viewModelEventFlow.collectLatest { event ->
            if (event is NavigateToMapScreen) {
                openCityOnMap(event.cityToFocus)
            }
        }
    }

    CitiesSearchScreenUi(
        modifier = modifier,
        uiState = uiState,
        onViewEvent = viewModel::onViewEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesSearchScreenUi(
    uiState: CitiesSearchUiState,
    modifier: Modifier = Modifier,
    onViewEvent: (viewEvent: CitiesSearchUiEvent) -> Unit = {},
) {
    SearchBar(
        modifier = modifier,
        query = uiState.query,
        onQueryChange = { query -> onViewEvent(SearchQueryChanged(query)) },
        onSearch = {},
        active = true,
        onActiveChange = {},
        placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
        trailingIcon = {
            if (uiState !is Loading && uiState.query.isNotEmpty()) {
                IconButton(onClick = { onViewEvent(ClickedOnClearQuery) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        enabled = uiState !is Loading,
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = uiState,
            label = "ui-state"
        ) { state ->
            val stateModifier = Modifier.fillMaxSize()

            when (state) {
                is DisplayCitiesList -> CitiesList(
                    cities = state.cities,
                    stateModifier,
                    onCityClick = { city -> onViewEvent(ClickedOnCity(city)) }
                )

                is Empty -> RetryState(
                    modifier = stateModifier,
                    message = stringResource(id = R.string.empty_list),
                    onRetryClick = { onViewEvent(ClickedOnRetry) }
                )

                is Error -> RetryState(
                    modifier = stateModifier,
                    message = stringResource(id = R.string.generic_error),
                    onRetryClick = { onViewEvent(ClickedOnRetry) }
                )

                is Loading -> LoadingState(stateModifier)
            }
        }
    }
}

@Composable
private fun RetryState(
    message: String,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    ErrorState(
        modifier = modifier,
        message = message,
        buttonText = stringResource(id = R.string.retry),
        onButtonClick = onRetryClick
    )
}

@Preview(showBackground = true)
@Composable
private fun CitiesSearchScreenPreview(
    @PreviewParameter(CitiesSearchScreenUiStateProvider::class) uiState: CitiesSearchUiState
) {
    CitiesListTheme {
        CitiesSearchScreenUi(uiState = uiState, modifier = Modifier.fillMaxSize())
    }
}

private const val previewQuery = "City"

private val previewCities = (1..10).map {
    City(
        name = "City $it",
        countryCode = "BR",
        coordinates = Coordinates(0.0, 0.0),
        id = it
    )
}

private class CitiesSearchScreenUiStateProvider : PreviewParameterProvider<CitiesSearchUiState> {
    override val values: Sequence<CitiesSearchUiState> = sequenceOf(
        DisplayCitiesList(previewQuery, previewCities),
        Loading(previewQuery),
        Empty(previewQuery),
        Error(previewQuery, Throwable()),
    )
}
