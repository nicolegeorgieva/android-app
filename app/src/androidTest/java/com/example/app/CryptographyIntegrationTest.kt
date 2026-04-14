package com.example.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.data.cryptography.Cryptography
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.arrow.isLeft
import strikt.arrow.isRight
import strikt.assertions.isNotEqualTo
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CryptographyIntegrationTest {
  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var cryptography: Cryptography

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun encrypt_and_decrypt_returns_original_string() {
    runBlocking {
      // Given
      val originalText = "123456"

      // When
      val encryptedBase64 = cryptography.encrypt(originalText)
      val decryptionResult = cryptography.decrypt(encryptedBase64)

      // Then
      expectThat(encryptedBase64).isNotEqualTo(originalText)
      expectThat(decryptionResult).isRight(originalText)
    }
  }

  @Test
  fun decrypt_with_invalid_data_returns_failure() {
    runBlocking {
      // Given
      val invalidData = "NotABase64String"

      // When
      val result = cryptography.decrypt(invalidData)

      // Then
      expectThat(result).isLeft()
    }
  }

  @Test
  fun encryption_is_non_deterministic() {
    runBlocking {
      // Given
      val text = "Consistent Text"

      // When
      val firstEncryption = cryptography.encrypt(text)
      val secondEncryption = cryptography.encrypt(text)

      // Then
      expectThat(firstEncryption).isNotEqualTo(secondEncryption)

      expectThat(cryptography.decrypt(firstEncryption)).isRight(text)
      expectThat(cryptography.decrypt(secondEncryption)).isRight(text)
    }
  }
}