package br.pedroso.citieslist

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.pedroso.citieslist.entities.City
import br.pedroso.citieslist.utils.CityIdArgKey

sealed class AppScreen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList(),
) {

    sealed class BottomNavigationEntry(
        route: String,
        @DrawableRes val iconResource: Int,
        @StringRes val labelResource: Int
    ) : AppScreen(route) {
        data object CitiesSearch :
            BottomNavigationEntry("cities-search", R.drawable.ic_list_alt, R.string.cities)

        data object Starred :
            BottomNavigationEntry("starred", R.drawable.ic_star_filled, R.string.starred)
    }

    data object Map : AppScreen(
        route = "map/{$CityIdArgKey}",
        arguments = listOf(navArgument(CityIdArgKey) { type = NavType.IntType }),
    ) {
        fun createNavigationRoute(city: City) = "map/${city.id}"
    }
}

fun NavGraphBuilder.composable(
    screen: AppScreen,
    enterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    popExitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable(
    route = screen.route,
    arguments = screen.arguments,
    deepLinks = screen.deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    content = content
)