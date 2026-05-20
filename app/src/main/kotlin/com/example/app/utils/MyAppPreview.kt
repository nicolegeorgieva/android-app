package com.example.app.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.app.theme.MyAppTheme

@Composable
fun MyAppComponentPreview(
  content: @Composable () -> Unit,
) {
  MyAppTheme {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.safeDrawing.asPaddingValues()),
      color = MaterialTheme.colorScheme.background,
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
      ) {
        content()
      }
    }
  }
}

@Composable
fun MyAppScreenPreview(
  content: @Composable () -> Unit,
) {
  MyAppTheme {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.safeDrawing.asPaddingValues()),
      color = MaterialTheme.colorScheme.background,
    ) {
      content()
    }
  }
}