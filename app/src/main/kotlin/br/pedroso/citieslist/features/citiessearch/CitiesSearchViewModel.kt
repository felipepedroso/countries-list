package br.pedroso.citieslist.features.citiessearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.citieslist.domain.repository.CitiesRepository
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.*
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.DisplayCitiesList
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Empty
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Error
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesSearchViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val queryStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val _viewState = MutableStateFlow<CitiesSearchUiState>(Loading(queryStateFlow.value))
    val viewStateFlow: StateFlow<CitiesSearchUiState>
        get() = _viewState

    private val viewModelEventChannel = Channel<CitiesSearchViewModelEvent>(Channel.BUFFERED)
    val viewModelEventFlow: Flow<CitiesSearchViewModelEvent>
        get() = viewModelEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            queryStateFlow
                .distinctUntilChanged { old, new -> old.compareTo(new, ignoreCase = true) == 0 }
                .collect { searchQuery -> loadCitiesList(searchQuery) }
        }
    }

    private fun loadCitiesList(searchQuery: String) {
        _viewState.update { Loading(searchQuery) }

        viewModelScope.launch {
            runCatching {
                citiesRepository.getCities(searchQuery)
            }.onSuccess { cities ->
                _viewState.update {
                    if (cities.isNotEmpty()) {
                        DisplayCitiesList(searchQuery, cities)
                    } else {
                        Empty(searchQuery)
                    }
                }

            }.onFailure { error ->
                _viewState.update { Error(searchQuery, error) }
            }
        }
    }

    fun onViewEvent(viewEvent: CitiesSearchUiEvent) {
        when (viewEvent) {
            is ClickedOnCity -> viewModelScope.launch {
                viewModelEventChannel.send(NavigateToMapScreen(viewEvent.city))
            }
            is SearchQueryChanged -> queryStateFlow.value = viewEvent.newQuery
            ClickedOnRetry -> loadCitiesList(queryStateFlow.value)
            ClickedOnClearQuery -> queryStateFlow.value = ""
        }
    }
}
