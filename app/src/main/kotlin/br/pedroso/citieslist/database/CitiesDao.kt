package br.pedroso.citieslist.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Transaction
    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAllCities(): PagingSource<Int, DatabaseCity>

    @Transaction
    @Query("SELECT * FROM cities WHERE name LIKE :query || '%' ORDER BY name ASC")
    fun getCitiesByName(query: String): PagingSource<Int, DatabaseCity>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun getCityById(id: Int): Flow<DatabaseCity>

    @Upsert
    suspend fun upsertAll(cities: List<DatabaseCity>)

    @Update(entity = DatabaseCity::class)
    suspend fun updateCity(updatedCity: DatabaseCity)
}