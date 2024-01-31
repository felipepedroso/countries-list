package br.pedroso.citieslist.features.citiessearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.*
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.DisplayCitiesList
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Empty
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Error
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiState.Loading
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.repository.CitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CitiesSearchViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val queryStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    val viewStateFlow: StateFlow<CitiesSearchUiState> =
        queryStateFlow
            .distinctUntilChanged { old, new -> old.compareTo(new, ignoreCase = true) == 0 }
            .flatMapLatest { citiesRepository.getCities(it) }
            .map { cities ->
                if (cities.isNotEmpty()) {
                    DisplayCitiesList(queryStateFlow.value, cities)
                } else {
                    Empty(queryStateFlow.value)
                }
            }
            .catch { emit(Error(queryStateFlow.value, it)) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, Loading(queryStateFlow.value))

    private val viewModelEventChannel = Channel<CitiesSearchViewModelEvent>(Channel.BUFFERED)
    val viewModelEventFlow: Flow<CitiesSearchViewModelEvent>
        get() = viewModelEventChannel.receiveAsFlow()

    fun onViewEvent(viewEvent: CitiesSearchUiEvent) {
        when (viewEvent) {
            is ClickedOnCity -> viewModelScope.launch {
                viewModelEventChannel.send(NavigateToMapScreen(viewEvent.city))
            }

            is SearchQueryChanged -> queryStateFlow.update { viewEvent.newQuery }
            ClickedOnRetry -> queryStateFlow.update { it }
            ClickedOnClearQuery -> queryStateFlow.update { "" }
        }
    }
}
