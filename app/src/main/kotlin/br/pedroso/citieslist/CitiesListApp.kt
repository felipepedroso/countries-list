package br.pedroso.citieslist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.pedroso.citieslist.AppScreen.BottomNavigationEntry.CitiesSearch
import br.pedroso.citieslist.AppScreen.BottomNavigationEntry.Starred
import br.pedroso.citieslist.AppScreen.Map
import br.pedroso.citieslist.features.citiessearch.CitiesSearchScreen
import br.pedroso.citieslist.features.citymap.MapScreen
import br.pedroso.citieslist.features.starredcities.StarredCitiesScreen

@Composable
fun CitiesListApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigationBar(
                bottomNavigationItems = listOf(CitiesSearch, Starred),
                navController = navController,
            )
        },
    ) { paddingValues ->
        NavHost(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            navController = navController,
            startDestination = CitiesSearch.route,
        ) {
            composable(CitiesSearch) {
                CitiesSearchScreen(
                    viewModel = hiltViewModel(),
                    openCityOnMap = { city ->
                        navController.navigate(Map.createNavigationRoute(city))
                    },
                )
            }

            composable(Starred) {
                StarredCitiesScreen(
                    viewModel = hiltViewModel(),
                    openCityOnMap = { city ->
                        navController.navigate(Map.createNavigationRoute(city))
                    },
                )
            }

            composable(Map) {
                MapScreen(
                    viewModel = hiltViewModel(),
                    onNavigateUp = { navController.navigateUp() },
                )
            }
        }
    }
}
