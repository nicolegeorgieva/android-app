package com.example.app.data.cryptography

import arrow.core.Either
import javax.inject.Inject

class FakeCryptography @Inject constructor() : Cryptography {
  override suspend fun encrypt(raw: String): String {
    return raw.reversed()
  }

  override suspend fun decrypt(encrypted: String): Either<Throwable, String> {
    return Either.Right(encrypted.reversed())
  }
}