package br.pedroso.citieslist.databaseinitialization

import android.content.Context
import androidx.room.RoomDatabase
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
    fun provideDatabaseInitializationCallback(
        @ApplicationContext applicationContext: Context,
    ): RoomDatabase.Callback {
        return DatabaseInitializationCallback(applicationContext)
    }
}
