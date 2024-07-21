package com.user.management.domain.models

data class UserEntity(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String
)
