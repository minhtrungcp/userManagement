package com.user.management.data.repositories

import com.user.management.data.models.dao.UserDAO
import com.user.management.data.models.dao.UserModel
import com.user.management.domain.models.UserEntity
import com.user.management.domain.repositories.LocalUserRepository
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO
) : LocalUserRepository {

    override fun insertUser(user: UserEntity): Long {
        val userModel = UserModel(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
            htmlUrl = user.htmlUrl
        )
        return userDAO.insertUser(userModel)
    }

    override fun insertUsers(users: List<UserEntity>): LongArray {
        val userList = users.map { UserModel(
            id = it.id,
            login = it.login,
            avatarUrl = it.avatarUrl,
            htmlUrl = it.htmlUrl
        ) }
        return userDAO.insertUsers(userList)
    }

    override fun getAllUsers(): List<UserModel>? = userDAO.getAllUsers()

    override fun deleteUser(id: Int) = userDAO.deleteUser(id)

    override fun deleteAllUser() = userDAO.deleteAllUser()
}