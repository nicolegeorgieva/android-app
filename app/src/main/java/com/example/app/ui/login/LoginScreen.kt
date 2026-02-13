package com.example.app.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.R
import com.example.app.ui.component.CtaButton
import com.example.app.ui.login.component.LoginInputField
import com.example.app.ui.login.component.LoginInputType
import com.example.app.utils.MyAppScreenPreview

@Composable
fun LoginScreen() {
  val viewModel: LoginViewModel = hiltViewModel()

  LoginUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent,
  )
}

@Composable
fun LoginUi(
  uiState: LoginState,
  onEvent: (LoginEvent) -> Unit
) {
  Scaffold { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = stringResource(R.string.login_title),
        style = MaterialTheme.typography.headlineMedium,
      )
      Spacer(Modifier.height(24.dp))
      LoginInputField(
        input = LoginInputType.Username(
          value = uiState.username,
          error = uiState.usernameError,
        ),
        onValueChange = {
          onEvent(LoginEvent.ChangeUsername(it))
        },
      )
      Spacer(Modifier.height(12.dp))
      LoginInputField(
        input = LoginInputType.Password(
          value = uiState.password,
          error = uiState.passwordError,
        ),
        onValueChange = {
          onEvent(LoginEvent.ChangePassword(it))
        },
        onDone = {
          onEvent(LoginEvent.LoginClick)
        }
      )
      Spacer(Modifier.height(24.dp))
      CtaButton(
        text = stringResource(R.string.login_cta_button),
        loading = uiState.loginButtonLoading,
        onClick = {
          onEvent(LoginEvent.LoginClick)
        }
      )
      uiState.serverErrorMessage?.let {
        Spacer(Modifier.height(12.dp))
        Text(
          text = it,
          color = MaterialTheme.colorScheme.error,
        )
      }
    }
  }
}

@Preview
@Composable
private fun LoginUiPreview() {
  MyAppScreenPreview {
    LoginUi(
      uiState = LoginState(
        username = "Emma",
        password = "123456",
        usernameError = null,
        passwordError = null,
        loginButtonLoading = true,
        serverErrorMessage = null,
      ),
      onEvent = {}
    )
  }
}

@Preview
@Composable
private fun LoginUiPasswordErrorPreview() {
  MyAppScreenPreview {
    LoginUi(
      uiState = LoginState(
        username = "Emma",
        password = "",
        usernameError = null,
        passwordError = stringResource(R.string.login_error_empty_password),
        loginButtonLoading = false,
        serverErrorMessage = null,
      ),
      onEvent = {}
    )
  }
}

@Preview
@Composable
private fun LoginUiServerErrorPreview() {
  MyAppScreenPreview {
    LoginUi(
      uiState = LoginState(
        username = "Emma",
        password = "123456",
        usernameError = null,
        passwordError = null,
        loginButtonLoading = false,
        serverErrorMessage = stringResource(R.string.login_error_incorrect_credentials),
      ),
      onEvent = {}
    )
  }
}