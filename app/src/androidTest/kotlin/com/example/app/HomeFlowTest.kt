package com.example.app

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.domain.SessionUseCase
import com.example.app.robot.home.homeRobot
import com.example.app.robot.login.loginRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeFlowTest() {
  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
    createAndroidComposeRule()

  @Inject
  lateinit var mainEventBus: MainEventBus

  @Inject
  lateinit var sessionUseCase: SessionUseCase

  @Before
  fun setup() {
    hiltRule.inject()
    runBlocking {
      sessionUseCase.login(username = "Test", password = "123")
    }
    composeRule.activityRule.scenario.recreate()
  }

  @Test
  fun home_invalid_session_navigates_to_login() {
    homeRobot(composeRule) {
      assertTopBarVisible()
      assertTasksListVisible()
    }
    runBlocking {
      mainEventBus.emit(MainEvent.InvalidSession)
    }
    loginRobot(composeRule) {
      assertVisible()
    }
  }
}