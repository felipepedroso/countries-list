package br.pedroso.citieslist.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class DatabaseInitializerCallback(
    private val applicationContext: Context,
) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val request = OneTimeWorkRequestBuilder<SeedCitiesDatabaseWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(request)
    }
}
