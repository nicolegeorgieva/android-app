package com.example.app.domain

import arrow.core.Either
import com.example.app.data.ErrorResponse
import com.example.app.data.cryptography.FakeCryptography
import com.example.app.data.datastore.SessionStorage
import com.example.app.data.datastore.TestDataStore
import com.example.app.data.repository.login.LoginError
import com.example.app.data.repository.login.LoginRepository
import com.example.app.fixtures.CORRECT_CREDENTIALS
import com.example.app.fixtures.SESSION_1
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class SessionUseCaseTest {
  private val dataStore = TestDataStore()
  private val sessionStorage = SessionStorage(
    dataStore = dataStore,
    cryptography = FakeCryptography()
  )
  private val loginRepository = mockk<LoginRepository>()

  private val sessionUseCase = SessionUseCase(
    loginRepository = loginRepository,
    sessionStorage = sessionStorage,
  )

  @Before
  fun setup() {
    dataStore.clear()
    clearMocks(loginRepository, answers = false, recordedCalls = true)
  }

  // region getSession()
  @Test
  fun `There is no session`() = runTest {
    // when
    val session = sessionUseCase.getSession()
    // then
    expectThat(session).isEqualTo(null)
  }

  @Test
  fun `There is session`() = runTest {
    // given
    sessionStorage.store(SESSION_1)
    // when
    val session = sessionUseCase.getSession()
    // then
    expectThat(session).isEqualTo(SESSION_1)
  }
  // endregion

  // region Login
  @Test
  fun `Successful login`() = runTest {
    // given
    coEvery {
      loginRepository.login(
        username = CORRECT_CREDENTIALS.username,
        password = CORRECT_CREDENTIALS.password
      )
    } returns Either.Right(SESSION_1)
    // when
    val res = sessionUseCase.login(
      username = CORRECT_CREDENTIALS.username,
      password = CORRECT_CREDENTIALS.password
    )
    // then
    expectThat(res).isEqualTo(Either.Right(SESSION_1))
    expectThat(sessionStorage.get()).isEqualTo(SESSION_1)
  }

  @Test
  fun `Unsuccessful login - incorrect credentials`() = runTest {
    // given
    coEvery {
      loginRepository.login(any(), any())
    } returns Either.Left(LoginError.IncorrectCredentials)
    // when
    val res = sessionUseCase.login(
      username = "${CORRECT_CREDENTIALS.username}1",
      password = CORRECT_CREDENTIALS.password
    )
    // then
    expectThat(res).isEqualTo(Either.Left(LoginError.IncorrectCredentials))
    expectThat(sessionStorage.get()).isNull()
  }

  @Test
  fun `Unsuccessful login - server error`() = runTest {
    // given
    coEvery {
      loginRepository.login(any(), any())
    } returns Either.Left(LoginError.Other(ErrorResponse.Other))
    // when
    val res = sessionUseCase.login(
      username = CORRECT_CREDENTIALS.username,
      password = CORRECT_CREDENTIALS.password
    )
    // then
    expectThat(res).isEqualTo(Either.Left(LoginError.Other(ErrorResponse.Other)))
    expectThat(sessionStorage.get()).isNull()
  }
  // endregion
}