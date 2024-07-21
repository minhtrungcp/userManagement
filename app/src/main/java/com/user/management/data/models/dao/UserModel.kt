package com.user.management.data.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String
)

