package br.pedroso.citieslist.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAllCities(): Flow<List<DatabaseCity>>

    @Query("SELECT * FROM cities WHERE name LIKE :query || '%' ORDER BY name ASC")
    fun getCitiesByName(query: String): Flow<List<DatabaseCity>>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun getCityById(id: Int): Flow<DatabaseCity>

    @Upsert
    suspend fun upsertAll(cities: List<DatabaseCity>)
}