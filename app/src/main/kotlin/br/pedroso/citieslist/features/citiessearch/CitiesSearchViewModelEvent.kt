package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.City

sealed class CitiesSearchViewModelEvent {
    data class NavigateToMapScreen(val cityToFocus: br.pedroso.citieslist.domain.City) : CitiesSearchViewModelEvent()
}
