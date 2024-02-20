package br.pedroso.citieslist.features.starredcities

sealed class StarredCitiesUiEvent {
    class ClickedOnCity(val city: br.pedroso.citieslist.domain.City) : StarredCitiesUiEvent()

    data object ClickedOnRetry : StarredCitiesUiEvent()
}
