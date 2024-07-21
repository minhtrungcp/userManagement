package com.user.management.data.models.dto

import com.user.management.domain.models.UserDetailEntity

data class UserDetailDTO(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String,
    val location: String,
    val followers: Int,
    val following: Int
)

fun UserDetailDTO.toUserDetailEntity(): UserDetailEntity {
    return UserDetailEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        location = location,
        followers = followers,
        following = following
    )
}
