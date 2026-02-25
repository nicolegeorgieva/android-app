package com.example.app.robot.home

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag

class HomeRobot(private val rule: ComposeTestRule) {
  private fun topBar(): SemanticsNodeInteraction = rule.onNodeWithTag("home_top_bar")
  private fun tasksList(): SemanticsNodeInteraction = rule.onNodeWithTag("home_tasks_list")

  fun assertTopBarVisible(): HomeRobot {
    topBar().assertIsDisplayed()
    return this
  }

  fun assertTasksListVisible(): HomeRobot {
    tasksList().assertIsDisplayed()
    return this
  }
}

fun homeRobot(
  rule: ComposeTestRule,
  block: HomeRobot.() -> Unit,
) {
  HomeRobot(rule).apply(block)
}