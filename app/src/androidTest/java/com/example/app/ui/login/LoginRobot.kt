package com.example.app.ui.login

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput

class LoginRobot(private val rule: ComposeTestRule) {
  private fun usernameField(): SemanticsNodeInteraction = rule.onNodeWithTag("login_username")
  private fun passwordField(): SemanticsNodeInteraction = rule.onNodeWithTag("login_password")
  private fun loginButton(): SemanticsNodeInteraction = rule.onNodeWithTag("login_button")
  private fun errorText(): SemanticsNodeInteraction = rule.onNodeWithTag("login_error")

  fun enterUsername(username: String): LoginRobot {
    usernameField().performTextClearance()
    usernameField().performTextInput(username)
    return this
  }

  fun enterPassword(password: String): LoginRobot {
    passwordField().performTextClearance()
    passwordField().performTextInput(password)
    return this
  }

  fun tapLogin(): LoginRobot {
    loginButton()
      .performClick()
    return this
  }

  fun assertErrorShown(message: String): LoginRobot {
    errorText()
      .assertIsDisplayed()
      .assertTextEquals(message)
    return this
  }
}

fun loginRobot(
  rule: ComposeTestRule,
  block: LoginRobot.() -> Unit,
) {
  LoginRobot(rule).apply(block)
}