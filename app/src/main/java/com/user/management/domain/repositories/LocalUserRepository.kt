package com.user.management.domain.repositories

import com.user.management.data.models.dao.UserModel
import com.user.management.domain.models.UserEntity

interface LocalUserRepository {
    fun insertUser(user: UserEntity): Long
    fun insertUsers(users: List<UserEntity>): LongArray
    fun getAllUsers(): List<UserModel>?
    fun deleteUser(id: Int)
    fun deleteAllUser()
}