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
import br.pedroso.citieslist.R
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.features.starredcities.StarredCitiesUiEvent.ClickedOnCity
import br.pedroso.citieslist.features.starredcities.StarredCitiesViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.ui.components.PaginatedCitiesList
import br.pedroso.citieslist.ui.theme.CitiesListTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StarredCitiesScreen(
    viewModel: StarredCitiesViewModel,
    modifier: Modifier = Modifier,
    openCityOnMap: (city: City) -> Unit = {},
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
    lazyPagingItems: LazyPagingItems<City>,
    modifier: Modifier = Modifier,
    onUiEvent: (uiEvent: StarredCitiesUiEvent) -> Unit = {},
) {
    PaginatedCitiesList(
        modifier = modifier,
        lazyPagingItems = lazyPagingItems,
        showStarredIndicator = false,
        onCityClicked = { city -> onUiEvent(ClickedOnCity(city)) },
        headerContent = { itemsCount ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = pluralStringResource(
                        id = R.plurals.starred_cities,
                        count = itemsCount,
                        itemsCount
                    )
                )
            }
        },
        errorStateContent = {

        },
        emptyStateContent = {

        }
    )
}

@Preview(showBackground = true)
@Composable
fun StarredCitiesScreenPreview() {
    CitiesListTheme {
//        StarredCitiesScreenUi()
    }
}