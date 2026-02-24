package com.example.app.ui.settings

import app.cash.paparazzi.Paparazzi
import com.example.app.theme.MyAppTheme
import org.junit.Rule
import org.junit.Test

class SettingsScreenPaparazziTest {

  @get:Rule
  val paparazzi = Paparazzi()

  @Test
  fun settings_light() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = false, dynamicColor = false) {
        SettingsUi(
          uiState = SettingsState(
            logoutButtonLoading = false,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun settings_dark() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = true, dynamicColor = false) {
        SettingsUi(
          uiState = SettingsState(
            logoutButtonLoading = false,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun settings_logout_loading_light() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = false, dynamicColor = false) {
        SettingsUi(
          uiState = SettingsState(
            logoutButtonLoading = true,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun settings_logout_loading_dark() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = true, dynamicColor = false) {
        SettingsUi(
          uiState = SettingsState(
            logoutButtonLoading = true,
          ),
          onEvent = {},
        )
      }
    }
  }
}
