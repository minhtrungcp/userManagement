package com.user.management.data.repositories

import com.user.management.data.models.dao.UserDAO
import com.user.management.data.models.dao.UserModel
import com.user.management.domain.models.UserEntity
import com.user.management.domain.repositories.LocalUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO
) : LocalUserRepository {

    override suspend fun insertUser(user: UserEntity) {
        val userModel = UserModel(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
            htmlUrl = user.htmlUrl
        )
        userDAO.insertUser(userModel)
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        val userList = users.map { UserModel(
            id = it.id,
            login = it.login,
            avatarUrl = it.avatarUrl,
            htmlUrl = it.htmlUrl
        ) }
        userDAO.insertUsers(userList)
    }

    override fun getAllUsers(): Flow<List<UserModel>?> = userDAO.getAllUsers()

    override suspend fun deleteUser(id: Int) = userDAO.deleteUser(id)

    override suspend fun deleteAllUser() = userDAO.deleteAllUser()
}