package com.example.app.di

import com.example.app.data.cryptography.Cryptography
import com.example.app.data.cryptography.CryptographyImpl
import com.example.app.data.datasource.login.FakeLoginDataSource
import com.example.app.data.datasource.login.LoginDataSource
import com.example.app.data.datasource.task.FakeTaskRemoteDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import com.example.app.data.datastore.SessionStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
  @Binds
  abstract fun bindCryptography(cryptography: CryptographyImpl): Cryptography

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