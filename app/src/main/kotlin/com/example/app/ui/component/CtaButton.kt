package com.example.app.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.R
import com.example.app.utils.MyAppComponentPreview

@Composable
fun CtaButton(
  text: String,
  modifier: Modifier = Modifier,
  loading: Boolean = false,
  onClick: () -> Unit,
) {
  Button(
    modifier = modifier,
    onClick = onClick,
    enabled = !loading,
  ) {
    if (loading) {
      CircularProgressIndicator(modifier = Modifier.size(24.dp))
    } else {
      Text(text = text)
    }
  }
}

@Preview
@Composable
private fun CtaButtonNormalPreview() {
  MyAppComponentPreview {
    CtaButton(
      text = stringResource(R.string.login_cta_button),
      onClick = {},
    )
  }
}

@Preview
@Composable
private fun CtaButtonLoadingPreview() {
  MyAppComponentPreview {
    CtaButton(
      text = stringResource(R.string.login_cta_button),
      loading = true,
      onClick = {},
    )
  }
}