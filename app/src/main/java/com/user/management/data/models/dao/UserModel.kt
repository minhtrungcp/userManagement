package com.user.management.data.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.user.management.data.models.dto.UserDTO
import com.user.management.domain.models.UserEntity

/**
 * UserModel data class
 * store in database
 * object of user_table database
 */
@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String
)

/**
 * mapper DAO to domain object
 */
fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl
    )
}