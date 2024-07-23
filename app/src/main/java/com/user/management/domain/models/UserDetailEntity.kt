package com.user.management.domain.models

/**
 * UserDetailEntity data class
 * hold data for app using display user detail info
 */
data class UserDetailEntity(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val location: String?,
    val blog: String?,
    val name: String?,
    val followers: Int,
    val following: Int
)
