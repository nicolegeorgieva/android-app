package com.example.app.data.cryptography

import android.util.Base64
import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class Cryptography @Inject constructor(
  private val keystore: AndroidKeystore,
) {
  private val transformation = "AES/GCM/NoPadding"
  private val ivSize = 12

  suspend fun encrypt(raw: String): String {
    return withContext(Dispatchers.IO) {
      val cipher = Cipher.getInstance(transformation)
      cipher.init(Cipher.ENCRYPT_MODE, keystore.getSecretKey())

      val iv = cipher.iv
      val encryptedBytes = cipher.doFinal(raw.toByteArray(Charsets.UTF_8))

      val combined = iv + encryptedBytes
      Base64.encodeToString(combined, Base64.DEFAULT)
    }
  }

  suspend fun decrypt(encrypted: String): Either<Throwable, String> {
    return withContext(Dispatchers.IO) {
      Either.catch {
        val combined = Base64.decode(encrypted, Base64.DEFAULT)

        val iv = combined.sliceArray(0 until ivSize)
        val encryptedData = combined.sliceArray(ivSize until combined.size)

        val cipher = Cipher.getInstance(transformation)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, keystore.getSecretKey(), spec)

        val decodedBytes = cipher.doFinal(encryptedData)
        String(decodedBytes, Charsets.UTF_8)
      }
    }
  }
}