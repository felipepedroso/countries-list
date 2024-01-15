package br.pedroso.citieslist.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.entities.Coordinates
import br.pedroso.citieslist.ui.theme.CitiesListTheme

@Composable
fun CitiesList(
    cities: List<City>,
    modifier: Modifier = Modifier,
    onCityClick: (city: City) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(cities) { index, city: City ->
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
