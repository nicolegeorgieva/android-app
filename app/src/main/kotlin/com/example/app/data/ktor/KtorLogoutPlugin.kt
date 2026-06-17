package com.example.app.data.ktor

import com.example.app.MainEvent
import com.example.app.MainEventBus
import com.example.app.di.isAuthenticated
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.http.HttpStatusCode

class KtorLogoutPluginConfig {
  lateinit var mainEventBus: MainEventBus
}

val KtorLogoutPlugin = createClientPlugin("Auto Logout", ::KtorLogoutPluginConfig) {
  val eventBus = pluginConfig.mainEventBus

  on(Send) { originalRequest ->
    val originalCall = proceed(originalRequest)
    val response = originalCall.response
    if (originalRequest.isAuthenticated() &&
      response.status == HttpStatusCode.Unauthorized
    ) {
      eventBus.emit(MainEvent.InvalidSession)
      throw InvalidSessionException()
    } else {
      originalCall
    }
  }
}

class InvalidSessionException : IllegalStateException("Session expired or invalid")