package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializationCallback
    @Inject
    constructor(
        @ApplicationContext private val applicationContext: Context,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val request = OneTimeWorkRequestBuilder<DatabaseInitializationWorker>().build()
            WorkManager.getInstance(applicationContext).enqueue(request)
        }
    }
