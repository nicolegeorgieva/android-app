package com.example.app.data.cryptography

import arrow.core.Either

interface Cryptography {
  suspend fun encrypt(raw: String): String
  suspend fun decrypt(encrypted: String): Either<Throwable, String>
}