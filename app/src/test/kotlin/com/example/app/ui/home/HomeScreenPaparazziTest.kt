package com.example.app.ui.home

import app.cash.paparazzi.Paparazzi
import com.example.app.theme.MyAppTheme
import com.example.app.ui.model.Loadable
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test

class HomeScreenPaparazziTest {

  @get:Rule
  val paparazzi = Paparazzi()

  @Test
  fun home_content_light() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = false, dynamicColor = false) {
        HomeUi(
          uiState = HomeState.Content(
            searchQuery = "",
            tasks = Loadable.Content(
              persistentListOf(
                TaskUi(
                  id = "1",
                  title = "Buy groceries",
                  description = "Milk, Eggs, Bread",
                  color = null,
                ),
                TaskUi(
                  id = "2",
                  title = "Prepare presentation",
                  description = "Finalize slides for Monday",
                  color = null,
                ),
              ),
            ),
            isRefreshing = false,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun home_content_dark() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = true, dynamicColor = false) {
        HomeUi(
          uiState = HomeState.Content(
            searchQuery = "",
            tasks = Loadable.Content(
              persistentListOf(
                TaskUi(
                  id = "1",
                  title = "Buy groceries",
                  description = "Milk, Eggs, Bread",
                  color = null,
                ),
                TaskUi(
                  id = "2",
                  title = "Prepare presentation",
                  description = "Finalize slides for Monday",
                  color = null,
                ),
              ),
            ),
            isRefreshing = false,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun home_loading_light() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = false, dynamicColor = false) {
        HomeUi(
          uiState = HomeState.Content(
            searchQuery = "",
            tasks = Loadable.Loading,
            isRefreshing = false,
          ),
          onEvent = {},
        )
      }
    }
  }

  @Test
  fun home_loading_dark() {
    paparazzi.snapshot {
      MyAppTheme(darkTheme = true, dynamicColor = false) {
        HomeUi(
          uiState = HomeState.Content(
            searchQuery = "",
            tasks = Loadable.Loading,
            isRefreshing = false,
          ),
          onEvent = {},
        )
      }
    }
  }
}