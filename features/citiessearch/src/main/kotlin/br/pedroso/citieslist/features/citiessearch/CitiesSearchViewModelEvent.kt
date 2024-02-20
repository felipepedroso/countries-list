package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.City

sealed class CitiesSearchViewModelEvent {
    data class NavigateToMapScreen(val cityToFocus: City) : CitiesSearchViewModelEvent()
}
