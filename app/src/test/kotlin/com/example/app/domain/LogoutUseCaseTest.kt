package com.example.app.domain

import arrow.core.Either
import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datastore.SessionStorage
import com.example.app.data.datastore.TestDataStore
import com.example.app.fixtures.SESSION_1
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isNull

class LogoutUseCaseTest {
  private val dataStore = TestDataStore()
  private val sessionStorage = SessionStorage(
    dataStore = dataStore,
    cryptography = mockk {
      coEvery { encrypt(any()) } coAnswers {
        firstArg()
      }
      coEvery { decrypt(any()) } coAnswers {
        Either.Right(firstArg())
      }
    }
  )
  private val taskDataSource = mockk<TaskLocalDataSource>()
  private val logoutUseCase = LogoutUseCase(
    sessionStorage = sessionStorage,
    taskDataSource = taskDataSource,
  )

  @Before
  fun setup() {
    dataStore.clear()
  }

  @Test
  fun logout() = runTest {
    // given
    sessionStorage.store(SESSION_1)
    coEvery { taskDataSource.deleteTasks() } just runs
    // when
    logoutUseCase.logout()
    // then
    expectThat(sessionStorage.get()).isNull()
    coVerify(exactly = 1) {
      taskDataSource.deleteTasks()
    }
  }
}