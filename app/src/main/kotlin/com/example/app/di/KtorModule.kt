package com.example.app.di

import com.example.app.MainEventBus
import com.example.app.data.datastore.SessionStorage
import com.example.app.data.ktor.KtorLogoutPlugin
import com.example.app.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import javax.inject.Singleton
import kotlin.time.ExperimentalTime
import io.ktor.client.plugins.logging.Logger as KtorLogger

private val AuthenticatedAttributeKey = AttributeKey<Boolean>("authenticated-attribute")

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {
  @Provides
  @Singleton
  fun provideHttpClient(
    json: Json,
    sessionStorage: SessionStorage,
    mainEventBus: MainEventBus,
    appLogger: Logger,
  ): HttpClient {
    return HttpClient {
      install(Logging) {
        level = LogLevel.ALL
        logger = object : KtorLogger {
          override fun log(message: String) {
            appLogger.debug("KtorClient") { message }
          }
        }
      }
      install(ContentNegotiation) {
        json(
          json = json,
          contentType = ContentType.Any
        )
      }
      install(DefaultRequest) {
        contentType(ContentType.Application.Json)
        url(urlString = "https://api.example.com/")
      }
      install(Auth) {
        bearer {
          // If true, sends the auth bearer header without waiting for 401 response
          sendWithoutRequest { request ->
            request.isAuthenticated()
          }
          loadTokens {
            val session = sessionStorage.get()

            if (session != null) {
              BearerTokens(
                accessToken = session.accessToken,
                refreshToken = null,
              )
            } else {
              null
            }
          }
        }
      }
      install(KtorLogoutPlugin) {
        this.mainEventBus = mainEventBus
      }
    }
  }

  @OptIn(ExperimentalTime::class)
  @Provides
  fun provideJson(): Json {
    return Json {
      ignoreUnknownKeys = true
      isLenient = true
      serializersModule = SerializersModule {
        contextual(InstantSerializer)
      }
    }
  }
}

fun HttpRequestBuilder.authenticated() {
  attributes[AuthenticatedAttributeKey] = true
}

fun HttpRequestBuilder.isAuthenticated(): Boolean {
  return attributes.getOrNull(AuthenticatedAttributeKey) == true
}