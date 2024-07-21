package com.user.management.presenter

sealed class Screen(val route: String){
    data object UserDetailScreen: Screen("user_detail_screen")
    data object UserList: Screen("user_list_screen")
}