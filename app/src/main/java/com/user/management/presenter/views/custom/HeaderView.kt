package com.user.management.presenter.views.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.user.management.R

@Composable
fun HeaderView(title: String, backPress: () -> Unit) {
	TopAppBar(
		title = {
			Text(
				text = title,
				modifier = Modifier.fillMaxWidth(),
				style = MaterialTheme.typography.subtitle1,
				textAlign = TextAlign.Center
			)
		},
		navigationIcon = {
			IconButton(onClick = {
				backPress()
			}) {
				Icon(
					painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
					contentDescription = null
				)
			}
		},
		actions = {
			Box(modifier = Modifier.size(width = 70.dp, height = 50.dp)) {}
		}
	)
}