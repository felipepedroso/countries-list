package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.City

sealed class CitiesSearchUiEvent {
    class SearchQueryChanged(val newQuery: String) : CitiesSearchUiEvent()

    class ClickedOnCity(val city: br.pedroso.citieslist.domain.City) : CitiesSearchUiEvent()

    data object ClickedOnRetry : CitiesSearchUiEvent()

    data object ClickedOnClearQuery : CitiesSearchUiEvent()
}
