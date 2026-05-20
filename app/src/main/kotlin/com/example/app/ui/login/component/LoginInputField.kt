package com.example.app.ui.login.component

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.R
import com.example.app.utils.MyAppComponentPreview

@Immutable
sealed interface LoginInputType {
  val value: String
  val error: String?

  data class Username(
    override val value: String,
    override val error: String?,
  ) : LoginInputType

  data class Password(
    override val value: String,
    override val error: String?,
  ) : LoginInputType
}

@Composable
fun LoginInputField(
  input: LoginInputType,
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit,
  onDone: (KeyboardActionScope.() -> Unit)? = null,
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    modifier = modifier,
    value = input.value,
    label = {
      LoginInputFieldTitle(input = input)
    },
    placeholder = {
      LoginInputFieldTitle(input = input)
    },
    keyboardOptions = KeyboardOptions(
      imeAction = if (input is LoginInputType.Username) ImeAction.Next else ImeAction.Done
    ),
    keyboardActions = if (onDone != null && input is LoginInputType.Password) {
      KeyboardActions(
        onDone = {
          focusManager.clearFocus()
          onDone()
        }
      )
    } else {
      KeyboardActions.Default
    },
    onValueChange = onValueChange,
    isError = input.error != null,
    visualTransformation = if (input is LoginInputType.Password) {
      PasswordVisualTransformation()
    } else {
      VisualTransformation.None
    },
    supportingText = input.error?.let {
      {
        Text(text = it)
      }
    }
  )
}

@Composable
private fun LoginInputFieldTitle(
  input: LoginInputType,
  modifier: Modifier = Modifier,
) {
  Text(
    modifier = modifier,
    text = stringResource(
      when (input) {
        is LoginInputType.Username -> R.string.login_username_field_title
        is LoginInputType.Password -> R.string.login_password_field_title
      }
    )
  )
}

@Preview
@Composable
private fun LoginUsernameFieldPreview() {
  MyAppComponentPreview {
    LoginInputField(
      input = LoginInputType.Username(
        value = "",
        error = null,
      ),
      onValueChange = {}
    )
  }
}

@Preview
@Composable
private fun LoginUsernameFieldFilledPreview() {
  MyAppComponentPreview {
    LoginInputField(
      input = LoginInputType.Username(
        value = "Emily",
        error = null,
      ),
      onValueChange = {}
    )
  }
}

@Preview
@Composable
private fun LoginUsernameFieldErrorPreview() {
  MyAppComponentPreview {
    LoginInputField(
      input = LoginInputType.Username(
        value = "",
        error = stringResource(R.string.login_error_blank_username),
      ),
      onValueChange = {}
    )
  }
}

@Preview
@Composable
private fun LoginPasswordFieldPreview() {
  MyAppComponentPreview {
    LoginInputField(
      input = LoginInputType.Password(
        value = "",
        error = null,
      ),
      onValueChange = {}
    )
  }
}

@Preview
@Composable
private fun LoginPasswordFieldErrorPreview() {
  MyAppComponentPreview {
    LoginInputField(
      input = LoginInputType.Username(
        value = "",
        error = stringResource(R.string.login_error_empty_password),
      ),
      onValueChange = {}
    )
  }
}