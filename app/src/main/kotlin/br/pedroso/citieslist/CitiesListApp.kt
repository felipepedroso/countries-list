package br.pedroso.citieslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.pedroso.citieslist.AppScreen.Map
import br.pedroso.citieslist.AppScreen.TopLevelScreen.CitiesSearch
import br.pedroso.citieslist.AppScreen.TopLevelScreen.Starred
import br.pedroso.citieslist.features.citiessearch.CitiesSearchScreen
import br.pedroso.citieslist.features.citymap.MapScreen
import br.pedroso.citieslist.features.starredcities.StarredCitiesScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CitiesListApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    windowSizeClass: WindowSizeClass,
) {
    val showBottomBar = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val showNavigationRail = !showBottomBar

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                CitiesBottomNavigationBar(
                    bottomNavigationItems = AppScreen.TopLevelScreen.Screens,
                    navController = navController,
                )
            }
        },
    ) { paddingValues ->
        Row(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues),
        ) {
            if (showNavigationRail) {
                CitiesNavigationRail(
                    modifier = Modifier.safeDrawingPadding(),
                    navController = navController,
                )
            }

            Column(Modifier.fillMaxSize()) {
                NavHost(
                    modifier =
                    Modifier
                        .fillMaxSize(),
                    navController = navController,
                    startDestination = CitiesSearch.route,
                ) {
                    composable(CitiesSearch) {
                        br.pedroso.citieslist.features.citiessearch.CitiesSearchScreen(
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
    }
}
