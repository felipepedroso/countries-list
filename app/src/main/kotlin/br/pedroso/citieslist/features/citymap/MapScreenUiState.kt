package br.pedroso.citieslist.features.citymap

import br.pedroso.citieslist.domain.entities.City

sealed class MapScreenUiState {
    data object Loading : MapScreenUiState()
    data class DisplayCity(val city: City) : MapScreenUiState()
    data class Error(val error: Throwable) : MapScreenUiState()
}