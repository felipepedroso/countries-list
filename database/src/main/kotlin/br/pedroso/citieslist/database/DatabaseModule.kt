package br.pedroso.citieslist.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext applicationContext: Context,
        databaseInitializerCallback: DatabaseInitializerCallback,
    ): AppDatabase {
        val databaseBuilder =
            Room.databaseBuilder(
                context = applicationContext,
                klass = AppDatabase::class.java,
                name = "cities-database",
            )

        return databaseBuilder
            .addCallback(databaseInitializerCallback)
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabaseInitializerCallback(
        @ApplicationContext applicationContext: Context,
    ): DatabaseInitializerCallback {
        return DatabaseInitializerCallback(applicationContext)
    }

    @Provides
    fun provideCitiesDao(appDatabase: AppDatabase): CitiesDao {
        return appDatabase.citiesDao()
    }
}
