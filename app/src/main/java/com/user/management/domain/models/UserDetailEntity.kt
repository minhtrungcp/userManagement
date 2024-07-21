package com.user.management.domain.models

data class UserDetailEntity(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String,
    val location: String,
    val followers: Int,
    val following: Int
)
