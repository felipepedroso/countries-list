package br.pedroso.citieslist.features.starredcities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.pedroso.citieslist.features.starredcities.StarredCitiesUiEvent.ClickedOnCity
import br.pedroso.citieslist.features.starredcities.StarredCitiesViewModelEvent.NavigateToMapScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StarredCitiesScreen(
    viewModel: StarredCitiesViewModel,
    modifier: Modifier = Modifier,
    openCityOnMap: (city: br.pedroso.citieslist.domain.City) -> Unit = {},
) {
    val lazyPagingItems = viewModel.paginatedCities.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.viewModelEventFlow.collectLatest { event ->
            if (event is NavigateToMapScreen) {
                openCityOnMap(event.cityToFocus)
            }
        }
    }

    StarredCitiesScreenUi(lazyPagingItems, modifier, viewModel::onUiEvent)
}

@Composable
private fun StarredCitiesScreenUi(
    lazyPagingItems: LazyPagingItems<br.pedroso.citieslist.domain.City>,
    modifier: Modifier = Modifier,
    onUiEvent: (uiEvent: StarredCitiesUiEvent) -> Unit = {},
) {
    br.pedroso.citieslist.designsystem.components.PaginatedCitiesList(
        modifier = modifier,
        lazyPagingItems = lazyPagingItems,
        showStarredIndicator = false,
        onCityClicked = { city -> onUiEvent(ClickedOnCity(city)) },
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
                            id = R.plurals.starred_cities,
                            count = itemsCount,
                            itemsCount,
                        ),
                )
            }
        },
        errorStateContent = {
        },
        emptyStateContent = {
        },
    )
}

@Preview(showBackground = true)
@Composable
fun StarredCitiesScreenPreview() {
    br.pedroso.citieslist.designsystem.theme.CitiesListTheme {
//        StarredCitiesScreenUi()
    }
}
