package com.user.management.data.models.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.user.management.domain.models.UserDetailEntity

data class UserDetailDTO(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String?,
    @SerializedName("html_url")
    @Expose
    val htmlUrl: String,
    val location: String?,
    val blog: String?,
    val name: String?,
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
        blog = blog,
        name = name,
        followers = followers,
        following = following
    )
}
