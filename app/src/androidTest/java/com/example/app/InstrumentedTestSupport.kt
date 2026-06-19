package com.example.app

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.app.domain.LogoutUseCase
import com.example.app.navigation.Navigator
import com.example.app.navigation.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.runBlocking
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

object InstrumentedTestSupport {
  fun resetPersistedAppState(
    logoutUseCase: LogoutUseCase,
    navigator: Navigator,
  ) {
    runBlocking {
      logoutUseCase.logout()
    }
    navigator.replace(listOf(Screen.Login))
  }
}

class PreLaunchAppStateRule(
  private val hiltRule: HiltAndroidRule,
  private val resetPersistedState: () -> Unit,
) : TestRule {
  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        hiltRule.inject()
        resetPersistedState()
        base.evaluate()
      }
    }
  }
}

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.waitForHomeScreen(
  timeoutMillis: Long = 10_000,
) {
  waitUntil(timeoutMillis) {
    onAllNodesWithTag("home_tasks_list").fetchSemanticsNodes().isNotEmpty()
  }
}

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.waitForLoginScreen(
  timeoutMillis: Long = 10_000,
) {
  waitUntil(timeoutMillis) {
    onAllNodesWithTag("login_button").fetchSemanticsNodes().isNotEmpty()
  }
}