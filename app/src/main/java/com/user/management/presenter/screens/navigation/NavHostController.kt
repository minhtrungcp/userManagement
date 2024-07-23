package com.user.management.presenter.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.user.management.presenter.screens.user_detail.UserDetailScreen
import com.user.management.presenter.screens.user_list.UserListScreen

/**
 * NavHostController Composable
 * Define navigate flow
 */
@Composable
fun NavHostController(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route
    ) {
        composable(
            route = Screen.UserList.route
        ) {
            UserListScreen(navController)
        }
        composable(
            route = Screen.UserDetailScreen.route + "/{login_name}"
        ) {
            UserDetailScreen(navController)
        }
    }
}