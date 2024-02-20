package br.pedroso.citieslist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseCity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
}
