package com.example.app.ui.login

import androidx.compose.ui.res.stringResource
import app.cash.paparazzi.Paparazzi
import com.example.app.R
import com.example.app.theme.MyAppTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenPaparazziTest {

  @get:Rule
  val paparazzi = Paparazzi()

  @Test
  fun login_filled_light() {
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
  fun login_filled_dark() {
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

  @Test
  fun login_server_error_light() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = false, dynamicColor = false) {
        LoginUi(
          uiState = LoginState(
            username = "Emma",
            password = "123456",
            usernameError = null,
            passwordError = null,
            loginButtonLoading = false,
            serverErrorMessage = stringResource(R.string.login_error_incorrect_credentials),
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun login_server_error_dark() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = true, dynamicColor = false) {
        LoginUi(
          uiState = LoginState(
            username = "Emma",
            password = "123456",
            usernameError = null,
            passwordError = null,
            loginButtonLoading = false,
            serverErrorMessage = stringResource(R.string.login_error_incorrect_credentials),
          ),
          onEvent = {},
        )
      }
    }
  }
}

