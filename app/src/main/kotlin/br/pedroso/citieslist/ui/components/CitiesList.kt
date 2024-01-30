package br.pedroso.citieslist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.pedroso.citieslist.R
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.entities.Coordinates
import br.pedroso.citieslist.ui.theme.CitiesListTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CitiesList(
    cities: List<City>,
    modifier: Modifier = Modifier,
    onCityClick: (city: City) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        item {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.query_result,
                            count = cities.size,
                            cities.size
                        )
                    )
                }
            }
        }

        itemsIndexed(cities, key = { _, city -> city.id }) { index, city: City ->
            CityItem(
                modifier = Modifier.fillMaxWidth(),
                city = city,
                onClick = { onCityClick(city) })

            if (index < cities.size - 1) {
                Divider()
            }
        }
    }

}

private val previewCities = (1..10).map {
    City(
        name = "City $it",
        countryCode = "BR",
        coordinates = Coordinates(0.0, 0.0),
        id = it
    )
}

@Preview(showBackground = true)
@Composable
private fun CitiesListPreview() {
    CitiesListTheme {
        CitiesList(modifier = Modifier.fillMaxSize(), cities = previewCities)
    }
}
