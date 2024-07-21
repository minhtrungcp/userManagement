package com.user.management.data.models.dto

import com.user.management.domain.models.UserEntity

data class UserDTO(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String
)

fun UserDTO.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )
}
