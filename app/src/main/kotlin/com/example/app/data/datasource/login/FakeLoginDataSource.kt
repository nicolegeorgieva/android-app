package com.example.app.data.datasource.login

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.app.data.datasource.model.SessionDto
import javax.inject.Inject

class FakeLoginDataSource @Inject constructor() : LoginDataSource {
  override suspend fun login(
    username: String,
    password: String,
  ): Either<Throwable, SessionDto> {
    return if (username == "Test" && password == "123") {
      SessionDto(
        accessToken = "fake-access-token",
      ).right()
    } else {
      IncorrectCredentialsException().left()
    }
  }
}

