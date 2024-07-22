package com.user.management.data.models.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.user.management.domain.models.UserEntity

data class UserDTO(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String?,
    @SerializedName("html_url")
    @Expose
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
