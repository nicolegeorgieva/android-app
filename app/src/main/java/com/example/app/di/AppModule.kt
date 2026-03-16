package com.example.app.di

import com.example.app.data.datasource.login.FakeLoginDataSource
import com.example.app.data.datasource.login.LoginDataSource
import com.example.app.data.datasource.task.FakeTaskRemoteDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import com.example.app.data.datastore.SessionStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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