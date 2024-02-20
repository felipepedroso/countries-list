package br.pedroso.citieslist.features.citiessearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.pedroso.citieslist.domain.City
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnCity
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnClearQuery
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.ClickedOnRetry
import br.pedroso.citieslist.features.citiessearch.CitiesSearchUiEvent.SearchQueryChanged
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CitiesSearchViewModel
    @Inject
    constructor(
        private val citiesRepository: br.pedroso.citieslist.repository.CitiesRepository,
    ) : ViewModel() {
        private val _queryState: MutableStateFlow<String> = MutableStateFlow("")

        val queryState: StateFlow<String> = _queryState

        val paginatedCities: Flow<PagingData<City>> =
            _queryState
                .distinctUntilChanged { old, new -> old.compareTo(new, ignoreCase = true) == 0 }
                .flatMapLatest { citiesRepository.getCities(it) }
                .cachedIn(viewModelScope)

        private val viewModelEventChannel = Channel<CitiesSearchViewModelEvent>(Channel.BUFFERED)
        val viewModelEventFlow: Flow<CitiesSearchViewModelEvent>
            get() = viewModelEventChannel.receiveAsFlow()

        fun onViewEvent(viewEvent: CitiesSearchUiEvent) {
            when (viewEvent) {
                is ClickedOnCity ->
                    viewModelScope.launch {
                        viewModelEventChannel.send(NavigateToMapScreen(viewEvent.city))
                    }

                is SearchQueryChanged -> _queryState.update { viewEvent.newQuery }
                ClickedOnRetry -> _queryState.update { it }
                ClickedOnClearQuery -> _queryState.update { "" }
            }
        }
    }
