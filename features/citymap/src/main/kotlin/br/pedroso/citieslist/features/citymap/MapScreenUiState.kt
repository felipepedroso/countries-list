package br.pedroso.citieslist.features.citymap

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

sealed class MapScreenUiState {
    data object Loading : MapScreenUiState()

    @Immutable
    data class DisplayCity(val city: br.pedroso.citieslist.domain.City) : MapScreenUiState()

    @Stable
    data class Error(val error: Throwable) : MapScreenUiState()
}
