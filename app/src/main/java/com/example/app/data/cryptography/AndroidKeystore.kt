package com.example.app.data.cryptography

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

class AndroidKeystore @Inject constructor() {
  companion object {
    private const val PROVIDER = "AndroidKeyStore"
    private const val KEY_ALIAS = "secret_key"
  }

  fun getSecretKey(): SecretKey {
    val keyStore = KeyStore.getInstance(PROVIDER).apply { load(null) }

    val existingKey = keyStore.getKey(KEY_ALIAS, null)
    if (existingKey != null) {
      return existingKey as SecretKey
    }

    val keyGenerator = KeyGenerator.getInstance(
      KeyProperties.KEY_ALGORITHM_AES,
      PROVIDER
    )

    val spec = KeyGenParameterSpec.Builder(
      KEY_ALIAS,
      KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
      .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
      .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
      .setKeySize(256)
      .build()
    keyGenerator.init(spec)

    return keyGenerator.generateKey()
  }
}