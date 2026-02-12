package com.example.app.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

/**
Should be inherited by the ViewModels of the screens.
It's a must to override the uiState() and onEvent() fun - return UI State and handle events.
 */
abstract class ComposeViewModel<S, E> : ViewModel() {
  @Composable
  abstract fun uiState(): S

  abstract fun onEvent(event: E)
}