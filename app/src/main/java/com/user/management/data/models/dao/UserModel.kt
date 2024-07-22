package com.user.management.data.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.user.management.data.models.dto.UserDTO
import com.user.management.domain.models.UserEntity

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String
)

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )
}