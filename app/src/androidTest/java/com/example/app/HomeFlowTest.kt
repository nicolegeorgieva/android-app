package com.example.app

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.domain.LogoutUseCase
import com.example.app.domain.SessionUseCase
import com.example.app.navigation.Navigator
import com.example.app.robot.home.homeRobot
import com.example.app.robot.login.loginRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
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
  val preLaunchAppStateRule = PreLaunchAppStateRule(hiltRule) {
    InstrumentedTestSupport.resetPersistedAppState(logoutUseCase, navigator)
  }

  @get:Rule(order = 2)
  val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
    createAndroidComposeRule()

  @Inject
  lateinit var mainEventBus: MainEventBus

  @Inject
  lateinit var logoutUseCase: LogoutUseCase

  @Inject
  lateinit var navigator: Navigator

  @Inject
  lateinit var sessionUseCase: SessionUseCase

  @Before
  fun setup() {
    runBlocking {
      sessionUseCase.login(username = "Test", password = "123")
    }
    composeRule.activityRule.scenario.recreate()
    composeRule.waitForHomeScreen()
  }

  @After
  fun tearDown() {
    InstrumentedTestSupport.resetPersistedAppState(logoutUseCase, navigator)
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
    composeRule.waitForLoginScreen()
    loginRobot(composeRule) {
      assertVisible()
    }
  }
}