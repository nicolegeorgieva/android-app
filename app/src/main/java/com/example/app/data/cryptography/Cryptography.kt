package com.example.app.data.cryptography

import arrow.core.Either
import javax.inject.Inject

class Cryptography @Inject constructor(
  private val keystore: AndroidKeystore,
) {
  suspend fun encrypt(raw: String): String {
    TODO()
  }

  suspend fun decrypt(encrypted: String): Either<String, String> {
    TODO()
  }
}