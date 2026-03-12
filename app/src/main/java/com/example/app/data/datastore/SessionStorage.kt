package com.example.app.data.datastore

import androidx.datastore.preferences.core.edit
import com.example.app.data.cryptography.Cryptography
import com.example.app.di.LocalDataStore
import com.example.app.domain.model.Session
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionStorage @Inject constructor(
  private val dataStore: LocalDataStore,
  private val cryptography: Cryptography,
) {
  suspend fun get(): Session? {
    return dataStore.data.map {
      val accessToken = it[DataStoreKeys.accessToken]?.let { encryptedAccessToken ->
        cryptography.decrypt(encryptedAccessToken).getOrNull()
      }
      if (accessToken != null) {
        Session(accessToken = accessToken)
      } else {
        null
      }
    }.first()
  }

  suspend fun store(session: Session) {
    dataStore.edit {
      it[DataStoreKeys.accessToken] = cryptography.encrypt(session.accessToken)
    }
  }

  suspend fun remove() {
    dataStore.edit {
      it.remove(DataStoreKeys.accessToken)
    }
  }
}