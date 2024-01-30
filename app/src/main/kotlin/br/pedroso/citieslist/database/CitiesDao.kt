package br.pedroso.citieslist.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities ORDER BY name ASC")
    suspend fun getAllCities(): List<DatabaseCity>

    @Query("SELECT * FROM cities WHERE name LIKE :query || '%' ORDER BY name ASC")
    suspend fun getCitiesByName(query: String): List<DatabaseCity>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun getCityById(id: Int): Flow<DatabaseCity>

    @Upsert
    suspend fun upsertAll(cities: List<DatabaseCity>)
}