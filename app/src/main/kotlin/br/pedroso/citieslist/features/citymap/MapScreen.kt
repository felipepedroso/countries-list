package br.pedroso.citieslist.features.citymap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.pedroso.citieslist.R
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.domain.entities.Coordinates
import br.pedroso.citieslist.ui.theme.CitiesListTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    city: City,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    val cityLatLng = LatLng(city.coordinates.latitude, city.coordinates.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityLatLng, 10f)
    }

    Column(modifier) {
        TopAppBar(
            title = { Text(text = "${city.name}, ${city.countryCode}") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            }
        )

        GoogleMap(modifier = Modifier.weight(1f), cameraPositionState = cameraPositionState) {
            Marker(
                state = MarkerState(position = cityLatLng),
                title = city.name,
                snippet = "Marker in ${city.name}"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MapScreenPreview() {
    CitiesListTheme {
        MapScreen(
            modifier = Modifier.fillMaxSize(),
            city = City(
                name = "Bristol",
                countryCode = "GB",
                coordinates = Coordinates(51.4552, -2.5967),
                id = 1
            )
        )
    }
}