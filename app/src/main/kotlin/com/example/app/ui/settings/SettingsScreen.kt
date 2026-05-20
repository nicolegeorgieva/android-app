package com.example.app.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.R
import com.example.app.ui.component.CtaButton
import com.example.app.ui.component.MyAppTopBar
import com.example.app.utils.MyAppScreenPreview

@Composable
fun SettingsScreen() {
  val viewModel: SettingsViewModel = hiltViewModel()
  SettingsUi(
    uiState = viewModel.uiState(),
    onEvent = viewModel::onEvent,
  )
}

@Composable
fun SettingsUi(
  uiState: SettingsState,
  onEvent: (SettingsEvent) -> Unit
) {
  Scaffold(
    topBar = {
      MyAppTopBar(
        modifier = Modifier.testTag("settings_top_bar"),
        title = stringResource(R.string.settings_title),
        onBackClick = {
          onEvent(SettingsEvent.BackClick)
        },
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp)
    ) {
      CtaButton(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("settings_logout_button"),
        text = stringResource(R.string.settings_logout_button),
        loading = uiState.logoutButtonLoading,
        onClick = {
          onEvent(SettingsEvent.LogoutClick)
        }
      )
    }
  }
}

@Preview
@Composable
private fun SettingsUiPreview() {
  MyAppScreenPreview {
    SettingsUi(
      uiState = SettingsState(
        logoutButtonLoading = false,
      ),
      onEvent = {}
    )
  }
}

@Preview
@Composable
private fun SettingsUiLogoutLoadingPreview() {
  MyAppScreenPreview {
    SettingsUi(
      uiState = SettingsState(
        logoutButtonLoading = true,
      ),
      onEvent = {}
    )
  }
}