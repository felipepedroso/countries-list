package br.pedroso.citieslist.features.citiessearch

import br.pedroso.citieslist.domain.entities.City

sealed class CitiesSearchViewState {
    object Loading : CitiesSearchViewState()
    data class Error(val error: Throwable) : CitiesSearchViewState()
    data class DisplayCitiesList(val cities: List<City>) : CitiesSearchViewState()
    object Empty : CitiesSearchViewState()
}
