package br.pedroso.citieslist.repository

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import br.pedroso.citieslist.domain.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun combinePagedCitiesAndDatabaseInitializationFlows(
    citiesPagingDataFlow: Flow<PagingData<City>>,
    databaseInitializationFlow: Flow<Boolean>,
): Flow<PagingData<City>> {
    return citiesPagingDataFlow.combine(databaseInitializationFlow) { pagingData, isInitializing ->
        if (isInitializing) {
            PagingData.empty(
                sourceLoadStates =
                LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.NotLoading(endOfPaginationReached = true),
                ),
            )
        } else {
            pagingData
        }
    }
}