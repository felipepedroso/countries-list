package br.pedroso.citieslist.features.citymap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.citieslist.domain.City
import br.pedroso.citieslist.features.citymap.MapScreenUiState.DisplayCity
import br.pedroso.citieslist.features.citymap.MapScreenUiState.Error
import br.pedroso.citieslist.features.citymap.MapScreenUiState.Loading
import br.pedroso.citieslist.repository.CitiesRepository
import br.pedroso.citieslist.utils.CITY_ID_ARG_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val citiesRepository: CitiesRepository,
    ) : ViewModel() {
        private val cityId: Int = checkNotNull(savedStateHandle[CITY_ID_ARG_KEY])

        val uiState: StateFlow<MapScreenUiState> by lazy {
            citiesRepository
                .getCityById(cityId)
                .map<br.pedroso.citieslist.domain.City, MapScreenUiState> { city -> DisplayCity(city) }
                .catch { error -> emit(Error(error)) }
                .stateIn(viewModelScope, SharingStarted.Eagerly, Loading)
        }

        fun updateStarredState(
            city: br.pedroso.citieslist.domain.City,
            newStarredState: Boolean,
        ) {
            viewModelScope.launch {
                citiesRepository.updateCityStarredState(city, newStarredState)
            }
        }
    }
