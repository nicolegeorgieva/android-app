package com.example.app

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.app.ui.home.homeRobot
import com.example.app.ui.login.loginRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginFlowTest {
  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeRule: ComposeTestRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun login_with_valid_credentials_navigates_to_home() {
    loginRobot(composeRule) {
      enterUsername("Test")
      enterPassword("123")
      tapLogin()
    }

    homeRobot(composeRule) {
      assertTopBarVisible()
      assertTasksListVisible()
    }
  }
}