package br.pedroso.citieslist.features.citymap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.citieslist.domain.repository.CitiesRepository
import br.pedroso.citieslist.features.citymap.MapScreenUiState.*
import br.pedroso.citieslist.utils.CityIdArgKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MapScreenUiState>(Loading)
    val uiState: StateFlow<MapScreenUiState> = _uiState

    init {
        _uiState.update { Loading }

        viewModelScope.launch {
            runCatching {
                val cityId: Int = checkNotNull(savedStateHandle[CityIdArgKey])

                citiesRepository.getCityById(cityId)
            }.onSuccess { city ->
                _uiState.update { DisplayCity(city) }
            }.onFailure { error ->
                _uiState.update { Error(error) }
            }
        }
    }
}