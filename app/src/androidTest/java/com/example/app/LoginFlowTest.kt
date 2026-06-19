package com.example.app

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.domain.LogoutUseCase
import com.example.app.navigation.Navigator
import com.example.app.robot.home.homeRobot
import com.example.app.robot.login.loginRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginFlowTest {
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
  lateinit var logoutUseCase: LogoutUseCase

  @Inject
  lateinit var navigator: Navigator

  @After
  fun tearDown() {
    InstrumentedTestSupport.resetPersistedAppState(logoutUseCase, navigator)
  }

  @Test
  fun login_with_valid_credentials_navigates_to_home() {
    loginRobot(composeRule) {
      enterUsername("Test")
      enterPassword("123")
      tapLogin()
    }

    composeRule.waitForHomeScreen()
    homeRobot(composeRule) {
      assertTopBarVisible()
      assertTasksListVisible()
    }
  }

  @Test
  fun login_with_invalid_credentials_shows_error() {
    loginRobot(composeRule) {
      enterUsername("Test")
      enterPassword("123456")
      tapLogin()
      assertErrorShown("Incorrect credentials")
    }
  }

  @Test
  fun login_error_removed_on_typing() {
    loginRobot(composeRule) {
      enterUsername("Test")
      enterPassword("123456")
      tapLogin()
      assertErrorShown("Incorrect credentials")
      enterPassword("12345")
      assertNoErrorShown()
    }
  }
}