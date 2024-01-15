package br.pedroso.citieslist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.pedroso.citieslist.Screens.CitiesSearch
import br.pedroso.citieslist.features.citiessearch.CitiesSearchScreen
import br.pedroso.citieslist.features.citymap.MapScreen
import br.pedroso.citieslist.ui.theme.CitiesListTheme
import br.pedroso.citieslist.utils.CityIdArgKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CitiesListTheme {
                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = CitiesSearch.name
                ) {
                    composable(CitiesSearch.name) {
                        CitiesSearchScreen(
                            viewModel = hiltViewModel(),
                            openCityOnMap = { city ->
                                navController.navigate("${Screens.Map.name}/${city.id}")
                            }
                        )
                    }

                    composable(
                        route = "${Screens.Map.name}/{${CityIdArgKey}}",
                        arguments = listOf(navArgument(CityIdArgKey) { type = NavType.IntType })
                    ) {
                        MapScreen(
                            viewModel = hiltViewModel(),
                            onNavigateUp = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}
