package br.pedroso.citieslist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class DatabaseCity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "country_code") val countryCode: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @PrimaryKey val id: Int,
)