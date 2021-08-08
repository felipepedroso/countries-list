package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.entities.City

sealed class CitiesSearchViewEvent {
    class SearchQueryChanged(val newQuery: String) : CitiesSearchViewEvent()
    class ClickedOnCity(val city: City) : CitiesSearchViewEvent()
}
