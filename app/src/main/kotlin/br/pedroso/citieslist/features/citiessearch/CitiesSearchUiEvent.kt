package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.entities.City

sealed class CitiesSearchUiEvent {
    class SearchQueryChanged(val newQuery: String) : CitiesSearchUiEvent()
    class ClickedOnCity(val city: City) : CitiesSearchUiEvent()
    object ClickedOnRetry : CitiesSearchUiEvent()
}
