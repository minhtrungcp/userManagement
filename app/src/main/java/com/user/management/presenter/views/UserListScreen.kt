package com.user.management.presenter.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.user.management.presenter.common.UIState
import com.user.management.presenter.viewmodels.UserViewModel
import com.user.management.presenter.views.custom.HeaderView

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    viewModel.fetchUserList()
    val uiState by viewModel.userList.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    Scaffold(
        topBar = { HeaderView("Github Users", navController) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.refresh()
                    },
                ) {
                    when (uiState) {
                        is UIState.Error -> Text(
                            text = (uiState as UIState.Error).message.orEmpty(),
                            color = MaterialTheme.colors.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                        )

                        is UIState.Loading -> CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )

                        is UIState.Success -> LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            val userList = (uiState as UIState.Success).data
                            items(userList.size) { index ->
                                val user = userList[index]
                                Card(
                                    shape = MaterialTheme.shapes.small,
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .fillMaxWidth()
                                        .clickable {
//                                            navController.navigate(Screen.WeatherCityDetailScreen.route + "/${city.name}")
                                        },
                                    elevation = 8.dp
                                ) {
                                    Text(
                                        text = user.login,
                                        style = MaterialTheme.typography.h2,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    )
}
