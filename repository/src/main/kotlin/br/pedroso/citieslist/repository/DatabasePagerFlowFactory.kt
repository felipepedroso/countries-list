package br.pedroso.citieslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import br.pedroso.citieslist.database.DatabaseCity
import br.pedroso.citieslist.domain.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun databasePagerFlowFactory(
    pageSize: Int = 30,
    initialKey: Int = 0,
    pagingSourceFactory: () -> PagingSource<Int, DatabaseCity>,
): Flow<PagingData<City>> {
    return Pager(
        config = PagingConfig(pageSize),
        initialKey = initialKey,
        pagingSourceFactory = pagingSourceFactory,
    ).flow.map { pagingData ->
        pagingData.map { databaseCity -> databaseCity.toEntity() }
    }
}
