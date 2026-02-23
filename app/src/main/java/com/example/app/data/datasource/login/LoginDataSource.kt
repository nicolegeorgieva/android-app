package com.example.app.data.datasource.login

import arrow.core.Either

interface LoginDataSource {
  suspend fun login(
    username: String,
    password: String,
  ): Either<Throwable, SessionDto>
}