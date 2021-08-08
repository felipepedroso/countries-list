package br.pedroso.citieslist.features.citiessearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.citieslist.domain.repository.CitiesRepository
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.ClickedOnCity
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.ClickedOnRetry
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.SearchQueryChanged
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.DisplayCitiesList
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Empty
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Error
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesSearchViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val queryStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val _viewState = MutableStateFlow<CitiesSearchViewState>(Loading)
    val viewStateFlow: StateFlow<CitiesSearchViewState>
        get() = _viewState

    private val viewModelEventChannel = Channel<CitiesSearchViewModelEvent>(Channel.BUFFERED)
    val viewModelEventFlow: Flow<CitiesSearchViewModelEvent>
        get() = viewModelEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            queryStateFlow.collect { searchQuery -> loadCitiesList(searchQuery) }
        }
    }

    private fun loadCitiesList(searchQuery: String) {
        _viewState.value = Loading

        viewModelScope.launch {
            runCatching {
                citiesRepository.getCities(searchQuery)
            }.onSuccess { cities ->
                if (cities.isNotEmpty()) {
                    _viewState.value = DisplayCitiesList(cities)
                } else {
                    _viewState.value = Empty
                }
            }.onFailure { error ->
                _viewState.value = Error(error)
            }
        }
    }

    fun onViewEvent(viewEvent: CitiesSearchViewEvent) {
        when (viewEvent) {
            is ClickedOnCity -> viewModelScope.launch {
                viewModelEventChannel.send(NavigateToMapScreen(viewEvent.city))
            }
            is SearchQueryChanged -> queryStateFlow.value = viewEvent.newQuery
            ClickedOnRetry -> loadCitiesList(queryStateFlow.value)
        }
    }
}
