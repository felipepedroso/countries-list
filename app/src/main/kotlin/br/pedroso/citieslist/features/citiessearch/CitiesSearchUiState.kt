package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.entities.City

sealed interface CitiesSearchUiState {
    val query: String

    data class Loading(override val query: String) : CitiesSearchUiState
    data class Error(override val query: String, val error: Throwable) : CitiesSearchUiState
    data class DisplayCitiesList(override val query: String, val cities: List<City>) :
        CitiesSearchUiState

    data class Empty(override val query: String) : CitiesSearchUiState
}
