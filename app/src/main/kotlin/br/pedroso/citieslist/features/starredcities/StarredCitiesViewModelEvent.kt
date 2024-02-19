package br.pedroso.citieslist.features.starredcities

import br.pedroso.citieslist.domain.City

sealed class StarredCitiesViewModelEvent {
    data class NavigateToMapScreen(val cityToFocus: br.pedroso.citieslist.domain.City) : StarredCitiesViewModelEvent()
}
