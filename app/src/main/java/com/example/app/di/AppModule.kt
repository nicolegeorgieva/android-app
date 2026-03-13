package com.example.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.app.data.database.MyAppDatabase
import com.example.app.data.database.task.TaskDao
import com.example.app.data.datasource.login.FakeLoginDataSource
import com.example.app.data.datasource.login.LoginDataSource
import com.example.app.data.datasource.task.FakeTaskRemoteDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import com.example.app.data.datastore.SessionStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app-datastore")

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

  @Provides
  fun provideLoginDataSource(): LoginDataSource {
    return FakeLoginDataSource()
  }

  @Provides
  fun provideTaskRemoteDataSource(
    sessionStorage: SessionStorage,
  ): TaskRemoteDataSource {
    return FakeTaskRemoteDataSource(
      sessionStorage = sessionStorage,
    )
  }
}

typealias LocalDataStore = DataStore<Preferences>