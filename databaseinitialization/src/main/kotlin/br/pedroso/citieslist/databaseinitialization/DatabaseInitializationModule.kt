package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.room.RoomDatabase
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseInitializationModule {
    @Singleton
    @Provides
    fun provideDatabaseInitializationCallback(databaseInitializationManager: DatabaseInitializationManager): RoomDatabase.Callback {
        return DatabaseInitializationCallback(databaseInitializationManager)
    }

    @Singleton
    @Provides
    fun provideWorkManager(
        @ApplicationContext applicationContext: Context,
    ): WorkManager {
        return WorkManager.getInstance(applicationContext)
    }
}
