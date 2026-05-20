package com.example.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.app.data.database.MyAppDatabase
import com.example.app.data.database.task.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

typealias LocalDataStore = DataStore<Preferences>

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
  private val Context.dataStore: LocalDataStore by preferencesDataStore(name = "app-datastore")

  @Provides
  fun provideDataStore(
    @ApplicationContext
    context: Context,
  ): LocalDataStore {
    return context.dataStore
  }

  @Provides
  fun provideDatabase(
    @ApplicationContext
    context: Context,
  ): MyAppDatabase {
    return Room.databaseBuilder(
      context,
      MyAppDatabase::class.java, "my-app-database"
    )
      .fallbackToDestructiveMigration(dropAllTables = true)
      .build()
  }

  @Provides
  fun provideTaskDao(
    database: MyAppDatabase,
  ): TaskDao {
    return database.taskDao()
  }
}