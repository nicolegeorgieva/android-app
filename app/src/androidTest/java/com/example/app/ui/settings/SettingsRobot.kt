package com.example.app.ui.settings

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class SettingsRobot(private val rule: ComposeTestRule) {
  private fun topBar(): SemanticsNodeInteraction = rule.onNodeWithTag("settings_top_bar")
  private fun logoutButton(): SemanticsNodeInteraction =
    rule.onNodeWithTag("settings_logout_button")

  fun assertTopBarVisible(): SettingsRobot {
    topBar().assertIsDisplayed()
    return this
  }

  fun assertLogoutVisible(): SettingsRobot {
    logoutButton().assertIsDisplayed()
    return this
  }

  fun tapLogout(): SettingsRobot {
    logoutButton().performClick()
    return this
  }
}

fun settingsRobot(
  rule: ComposeTestRule,
  block: SettingsRobot.() -> Unit,
) {
  SettingsRobot(rule).apply(block)
}