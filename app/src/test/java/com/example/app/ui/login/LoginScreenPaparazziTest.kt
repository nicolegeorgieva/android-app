package com.example.app.ui.login

import app.cash.paparazzi.Paparazzi
import com.example.app.theme.MyAppTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenPaparazziTest {

  @get:Rule
  val paparazzi = Paparazzi()

  @Test
  fun login_light() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = false, dynamicColor = false) {
        LoginUi(
          uiState = LoginState(
            username = "Emma",
            password = "123456",
            usernameError = null,
            passwordError = null,
            loginButtonLoading = false,
            serverErrorMessage = null,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun login_dark() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = true, dynamicColor = false) {
        LoginUi(
          uiState = LoginState(
            username = "Emma",
            password = "123456",
            usernameError = null,
            passwordError = null,
            loginButtonLoading = false,
            serverErrorMessage = null,
          ),
          onEvent = {},
        )
      }
    }
  }
}

