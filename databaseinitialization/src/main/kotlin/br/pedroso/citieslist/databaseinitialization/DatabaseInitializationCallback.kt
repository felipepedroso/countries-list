package br.pedroso.citieslist.databaseinitialization

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializationCallback
    @Inject
    constructor(
        private val databaseInitializationManager: DatabaseInitializationManager,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            databaseInitializationManager.startInitializationWorker()
        }
    }
