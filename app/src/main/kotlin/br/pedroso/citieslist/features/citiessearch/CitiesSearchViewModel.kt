package br.pedroso.citieslist.features.citiessearch

import androidx.lifecycle.ViewModel
import br.pedroso.citieslist.domain.repository.CitiesRepository
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Loading
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class CitiesSearchViewModel(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<CitiesSearchViewState>(Loading)
    val viewStateFlow: StateFlow<CitiesSearchViewState>
        get() = _viewState

    private val viewModelEventChannel = Channel<CitiesSearchViewModelEvent>(Channel.BUFFERED)
    val viewModelEventFlow: Flow<CitiesSearchViewModelEvent>
        get() = viewModelEventChannel.receiveAsFlow()

    fun onViewEvent(viewEvent: CitiesSearchViewEvent) {

    }
}
