package br.pedroso.citieslist.features.citiessearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.pedroso.citieslist.R
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnClearQuery
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnRetry
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.SearchQueryChanged
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.ui.components.ErrorState
import br.pedroso.citieslist.ui.components.PaginatedCitiesList
import br.pedroso.citieslist.ui.theme.CitiesListTheme
import br.pedroso.citieslist.utils.createPreviewCities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CitiesSearchScreen(
    viewModel: CitiesSearchViewModel,
    modifier: Modifier = Modifier,
    openCityOnMap: (city: City) -> Unit = {},
) {
    val lazyPagingItems = viewModel.paginatedCities.collectAsLazyPagingItems()

    val query by viewModel.queryState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.viewModelEventFlow.collectLatest { event ->
            if (event is NavigateToMapScreen) {
                openCityOnMap(event.cityToFocus)
            }
        }
    }

    CitiesSearchScreenUi(
        modifier = modifier,
        query = query,
        lazyPagingItems = lazyPagingItems,
        onViewEvent = viewModel::onViewEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesSearchScreenUi(
    query: String,
    lazyPagingItems: LazyPagingItems<City>,
    modifier: Modifier = Modifier,
    onViewEvent: (viewEvent: CitiesSearchUiEvent) -> Unit = {},
) {
    val isLoading = lazyPagingItems.loadState.refresh is LoadState.Loading

    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = { newQuery -> onViewEvent(SearchQueryChanged(newQuery)) },
        onSearch = {},
        active = true,
        onActiveChange = {},
        placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
        trailingIcon = {
            if (!isLoading && query.isNotEmpty()) {
                IconButton(onClick = { onViewEvent(ClickedOnClearQuery) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
            )
        },
    ) {
        PaginatedCitiesList(
            lazyPagingItems = lazyPagingItems,
            onCityClicked = { city -> onViewEvent(CitiesSearchUiEvent.ClickedOnCity(city)) },
            headerContent = { itemsCount ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                ) {
                    Text(
                        text =
                            pluralStringResource(
                                id = R.plurals.query_result,
                                count = itemsCount,
                                itemsCount,
                            ),
                    )
                }
            },
            errorStateContent = {
                RetryState(
                    modifier = Modifier.fillMaxSize(),
                    message = stringResource(id = R.string.generic_error),
                    onRetryClick = { onViewEvent(ClickedOnRetry) },
                )
            },
            emptyStateContent = {
                RetryState(
                    modifier = Modifier.fillMaxSize(),
                    message = stringResource(id = R.string.empty_list),
                    onRetryClick = { onViewEvent(ClickedOnRetry) },
                )
            },
        )
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
        onButtonClick = onRetryClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun CitiesSearchScreenPreview(
    @PreviewParameter(CitiesSearchScreenPagingDataProvider::class) pagingData: PagingData<City>,
) {
    val pagingDataFlow = MutableStateFlow(pagingData)

    val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()

    CitiesListTheme {
        CitiesSearchScreenUi(
            query = PREVIEW_QUERY,
            lazyPagingItems = lazyPagingItems,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private const val PREVIEW_QUERY = "City"

private class CitiesSearchScreenPagingDataProvider : PreviewParameterProvider<PagingData<City>> {
    override val values: Sequence<PagingData<City>> =
        sequenceOf(
            PagingData.from(createPreviewCities()),
            // Loading state
            PagingData.empty(
                LoadStates(
                    LoadState.Loading,
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                ),
            ),
            // Empty state
            PagingData.empty(
                LoadStates(
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                ),
            ),
            // Error state
            PagingData.empty(
                LoadStates(
                    LoadState.Error(Throwable()),
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                ),
            ),
        )
}
