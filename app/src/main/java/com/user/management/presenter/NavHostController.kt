package com.example.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.user.management.presenter.Screen
import com.user.management.presenter.views.UserListScreen


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
//        composable(
//            route = Screen.UserDetailScreen.route + "/{login_name}"
//        ) {
//            DetailWeatherScreen(navController)
//        }
    }
}