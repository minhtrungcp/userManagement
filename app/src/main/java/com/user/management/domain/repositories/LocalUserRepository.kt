package com.user.management.domain.repositories

import com.user.management.data.models.dao.UserModel
import com.user.management.domain.models.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun insertUsers(users: List<UserEntity>)
    fun getAllUsers(): Flow<List<UserModel>?>
    suspend fun deleteUser(id: Int)
    suspend fun deleteAllUser()
}