package br.pedroso.citieslist.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.pedroso.citieslist.designsystem.theme.CitiesListTheme
import br.pedroso.citieslist.designsystem.utils.createPreviewCities
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PaginatedCitiesList(
    lazyPagingItems: LazyPagingItems<br.pedroso.citieslist.domain.City>,
    modifier: Modifier = Modifier,
    onCityClicked: (city: br.pedroso.citieslist.domain.City) -> Unit = {},
    headerContent: (@Composable (itemsCount: Int) -> Unit)? = null,
    emptyStateContent: (@Composable () -> Unit)? = null,
    errorStateContent: (@Composable () -> Unit)? = null,
    loadingStateContent: (@Composable () -> Unit)? = {
        LoadingState(modifier = Modifier.fillMaxSize())
    },
    showStarredIndicator: Boolean = true,
) {
    Box(modifier = modifier) {
        val refreshLoadState = lazyPagingItems.loadState.refresh

        when {
            refreshLoadState is LoadState.Error -> errorStateContent?.invoke()

            refreshLoadState is LoadState.Loading -> loadingStateContent?.invoke()

            lazyPagingItems.itemCount == 0 -> emptyStateContent?.invoke()

            else ->
                CitiesList(
                    lazyPagingItems = lazyPagingItems,
                    onCityClicked = onCityClicked,
                    headerContent = headerContent,
                    showStarredIndicator = showStarredIndicator,
                )
        }
    }
}

@Composable
private fun CitiesList(
    lazyPagingItems: LazyPagingItems<br.pedroso.citieslist.domain.City>,
    modifier: Modifier = Modifier,
    onCityClicked: (city: br.pedroso.citieslist.domain.City) -> Unit = {},
    headerContent: (@Composable (itemsCount: Int) -> Unit)? = null,
    showStarredIndicator: Boolean = true,
) {
    LazyColumn(modifier) {
        if (headerContent != null) {
            item {
                headerContent(lazyPagingItems.itemCount)
            }
        }

        items(lazyPagingItems.itemCount) { index ->
            val city = lazyPagingItems[index]

            if (city != null) {
                CityItem(
                    city = city,
                    onClick = { onCityClicked(city) },
                    showStarredIndicator = showStarredIndicator,
                )

                if (index < lazyPagingItems.itemCount - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaginatedCitiesListPreview(
    @PreviewParameter(CitiesListPreviewParameterProvider::class) pagingData: PagingData<br.pedroso.citieslist.domain.City>,
) {
    CitiesListTheme {
        val pagingDataFlow = MutableStateFlow(pagingData)

        val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()

        PaginatedCitiesList(
            lazyPagingItems = lazyPagingItems,
            headerContent = {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(text = "$it cities available:")
                }
            },
            errorStateContent = {
                Box(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "This is an error state.")
                }
            },
            emptyStateContent = {
                Box(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "This is an empty state.")
                }
            },
        )
    }
}

private class CitiesListPreviewParameterProvider :
    PreviewParameterProvider<PagingData<br.pedroso.citieslist.domain.City>> {
    override val values: Sequence<PagingData<br.pedroso.citieslist.domain.City>> =
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
