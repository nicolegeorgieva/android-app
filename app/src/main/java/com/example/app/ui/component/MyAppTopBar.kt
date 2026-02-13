package com.example.app.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.utils.MyAppScreenPreview

@Composable
fun MyAppTopBar(
  title: String,
  modifier: Modifier = Modifier,
  onBackClick: (() -> Unit)? = null,
  actions: @Composable (RowScope.() -> Unit) = {}
) {
  TopAppBar(
    modifier = modifier,
    title = {
      Text(text = title)
    },
    navigationIcon = {
      if (onBackClick != null) {
        BackButton(onBackClick = onBackClick)
      }
    },
    actions = actions,
  )
}

@Composable
private fun BackButton(
  modifier: Modifier = Modifier,
  onBackClick: () -> Unit,
) {
  IconButton(
    modifier = modifier,
    onClick = onBackClick,
  ) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = null,
    )
  }
}

@Preview
@Composable
private fun MyAppTopBarPreview() {
  MyAppScreenPreview {
    Scaffold(
      topBar = {
        MyAppTopBar(
          title = "Settings",
          onBackClick = {}
        )
      }
    ) { paddingValues ->
      CtaButton(
        modifier = Modifier
          .fillMaxWidth()
          .padding(paddingValues)
          .padding(horizontal = 16.dp),
        text = "Delete account",
        onClick = {}
      )
    }
  }
}