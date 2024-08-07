package com.user.management.presenter.screens.user_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.user.management.R
import com.user.management.domain.models.UserDetailEntity
import com.user.management.presenter.common.UIState
import com.user.management.presenter.screens.viewmodels.UserViewModel
import com.user.management.presenter.views.custom.HeaderView

@Composable
fun UserDetailScreen(
	navController: NavController,
	viewModel: UserViewModel = hiltViewModel()
) {
	LaunchedEffect(Unit) {
		viewModel.fetchUserDetail()
	}
	val uiState by viewModel.userDetailState.collectAsStateWithLifecycle(
		initialValue = UIState.Loading<UserDetailEntity>()
	)

	Scaffold(
		topBar = { HeaderView(stringResource(R.string.user_detail)) {
			navController.popBackStack()
		} },
		content = { padding ->
			Box(
				modifier = Modifier
					.fillMaxSize()
					.padding(padding)
			) {
				when (uiState) {
					is UIState.Error -> Box(
						contentAlignment = Alignment.Center,
						modifier = Modifier.fillMaxSize()
					) {
						Column {
							Text(
								text = (uiState as UIState.Error).message.orEmpty(),
								color = MaterialTheme.colors.error,
								textAlign = TextAlign.Center,
								modifier = Modifier
									.fillMaxWidth()
									.padding(horizontal = 20.dp)
							)
							Button(modifier = Modifier
								.padding(top = 10.dp)
								.width(150.dp)
								.align(Alignment.CenterHorizontally),
								onClick = {
									viewModel.fetchUserDetail()
								}) {
								Text(
									text = stringResource(id = R.string.retry),
									color = White
								)
							}
						}
					}

					is UIState.Loading ->
						Box(
							contentAlignment = Alignment.Center,
							modifier = Modifier.fillMaxSize()
						) {
							CircularProgressIndicator(
								modifier = Modifier.align(Alignment.Center)
							)
						}

					is UIState.Success ->
						uiState.data?.let { userDetail ->
							Column(
								modifier = Modifier.fillMaxSize()
							) {
								Card(
									shape = MaterialTheme.shapes.small,
									modifier = Modifier
										.padding(12.dp)
										.fillMaxWidth(),
									elevation = 4.dp
								) {
									Row(
										modifier = Modifier
											.fillMaxWidth()
											.padding(16.dp),
									) {
										AsyncImage(
											model = ImageRequest.Builder(LocalContext.current)
												.data(userDetail.avatarUrl)
												.crossfade(true)
												.build(),
											placeholder = painterResource(
												R.drawable.baseline_person_pin_24
											),
											modifier = Modifier
												.size(120.dp)
												.clip(CircleShape)
												.border(1.dp, Gray, CircleShape),
											contentDescription = null,
											contentScale = ContentScale.Crop
										)

										Column(
											modifier = Modifier
												.fillMaxWidth()
												.padding(start = 16.dp, end = 6.dp)
										) {
											Text(
												modifier = Modifier.padding(bottom = 6.dp),
												text = userDetail.name.orEmpty(),
												style = MaterialTheme.typography.subtitle1,
												fontWeight = FontWeight.Bold,
												overflow = TextOverflow.Ellipsis,
												maxLines = 1
											)
											val modifier = Modifier
												.fillMaxWidth()
												.height(0.5.dp)

											Spacer(
												modifier = Modifier
													.height(0.5.dp)
													.then(modifier)
													.background(LightGray),
											)

											Row(modifier = Modifier.padding(top = 6.dp)) {
												Image(
													modifier = Modifier.size(20.dp),
													painter = painterResource(
														R.drawable.baseline_location_on_24
													),
													contentDescription = null
												)
												Text(
													modifier = Modifier.padding(start = 3.dp),
													text = userDetail.location.orEmpty(),
													style = MaterialTheme.typography.caption
												)
											}
										}
									}
								}

								Row(
									modifier = Modifier
										.padding(6.dp)
										.fillMaxWidth(),
									horizontalArrangement = Arrangement.Center
								) {
									Column(
										modifier = Modifier.padding(16.dp),
										verticalArrangement = Arrangement.Center,
										horizontalAlignment = Alignment.CenterHorizontally
									) {
										Image(
											modifier = Modifier.size(40.dp),
											painter = painterResource(
												R.drawable.baseline_follower_24
											),
											contentDescription = null
										)
										Text(
											modifier = Modifier.padding(bottom = 6.dp),
											text = userDetail.followers.toString(),
											style = MaterialTheme.typography.subtitle2
										)

										Text(
											modifier = Modifier.padding(bottom = 6.dp),
											text = stringResource(id = R.string.follower),
											style = MaterialTheme.typography.caption
										)
									}

									Column(
										modifier = Modifier.padding(16.dp),
										verticalArrangement = Arrangement.Center,
										horizontalAlignment = Alignment.CenterHorizontally
									) {
										Image(
											modifier = Modifier.size(40.dp),
											painter = painterResource(R.drawable.baseline_grade_24),
											contentDescription = null
										)
										Text(
											modifier = Modifier.padding(bottom = 6.dp),
											text = userDetail.following.toString(),
											style = MaterialTheme.typography.subtitle2
										)

										Text(
											modifier = Modifier.padding(bottom = 6.dp),
											text = stringResource(id = R.string.following),
											style = MaterialTheme.typography.caption
										)
									}
								}

								Text(
									modifier = Modifier.padding(12.dp),
									text = stringResource(id = R.string.blog),
									style = MaterialTheme.typography.h3,
									fontWeight = FontWeight.Bold,
									overflow = TextOverflow.Ellipsis,
									maxLines = 1
								)

								Text(
									modifier = Modifier.padding(start = 12.dp, end = 12.dp),
									text = userDetail.blog.orEmpty(),
									style = MaterialTheme.typography.body2
								)
							}
						}
				}
			}
		}
	)
}
