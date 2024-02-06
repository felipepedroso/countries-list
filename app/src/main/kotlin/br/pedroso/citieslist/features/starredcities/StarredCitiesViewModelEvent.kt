package br.pedroso.citieslist.features.starredcities

import br.pedroso.citieslist.entities.City

sealed class StarredCitiesViewModelEvent {
    data class NavigateToMapScreen(val cityToFocus: City) : StarredCitiesViewModelEvent()
}
