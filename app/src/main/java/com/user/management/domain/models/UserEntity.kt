package com.user.management.domain.models

/**
 * UserEntity data class
 * hold data for app using display user info
 */
data class UserEntity(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String
)
