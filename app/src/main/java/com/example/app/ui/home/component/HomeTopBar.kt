package com.example.app.ui.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
  searchText: String,
  modifier: Modifier = Modifier,
  onTextChange: (String) -> Unit,
  onSettingsClick: () -> Unit,
) {
  TopAppBar(
    modifier = modifier,
    title = {
      SearchField(
        modifier = Modifier.fillMaxWidth(),
        text = searchText,
        onTextChange = onTextChange,
      )
    },
    actions = {
      SettingsButton(onClick = onSettingsClick)
    }
  )
}

@Composable
private fun SearchField(
  text: String,
  modifier: Modifier = Modifier,
  onTextChange: (String) -> Unit,
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    modifier = modifier,
    value = text,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions = KeyboardActions(
      onDone = {
        focusManager.clearFocus()
      }
    ),
    onValueChange = onTextChange,
    leadingIcon = {
      SearchIcon()
    },
  )
}

@Composable
private fun SearchIcon(modifier: Modifier = Modifier) {
  Icon(
    modifier = modifier,
    painter = painterResource(R.drawable.outline_search_24),
    contentDescription = null,
  )
}

@Composable
private fun SettingsButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  IconButton(
    modifier = modifier,
    onClick = onClick,
  ) {
    Icon(
      painter = painterResource(R.drawable.outline_settings_24),
      contentDescription = null,
    )
  }
}