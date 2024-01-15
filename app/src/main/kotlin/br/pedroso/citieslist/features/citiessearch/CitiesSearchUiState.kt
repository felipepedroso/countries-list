package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.entities.City

sealed interface CitiesSearchViewState {
    val query: String

    data class Loading(override val query: String) : CitiesSearchViewState
    data class Error(override val query: String, val error: Throwable) : CitiesSearchViewState
    data class DisplayCitiesList(override val query: String, val cities: List<City>) :
        CitiesSearchViewState

    data class Empty(override val query: String) : CitiesSearchViewState
}
