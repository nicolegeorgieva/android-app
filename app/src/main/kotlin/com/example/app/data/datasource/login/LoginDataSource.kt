package com.example.app.data.datasource.login

import arrow.core.Either
import com.example.app.data.datasource.model.SessionDto

interface LoginDataSource {
  suspend fun login(
    username: String,
    password: String,
  ): Either<Throwable, SessionDto>
}