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

    /**
     * insert [UserEntity] to database
     * convert [UserEntity] to [UserModel] before insert
     */
    override suspend fun insertUser(user: UserEntity) {
        val userModel = UserModel(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
            htmlUrl = user.htmlUrl
        )
        userDAO.insertUser(userModel)
    }

    /**
     * insert list [UserEntity] to database
     * convert list [UserEntity] to list [UserModel] before insert
     */
    override suspend fun insertUsers(users: List<UserEntity>) {
        val userList = users.map { UserModel(
            id = it.id,
            login = it.login,
            avatarUrl = it.avatarUrl,
            htmlUrl = it.htmlUrl
        ) }
        userDAO.insertUsers(userList)
    }

    /**
     * get all [UserModel] in db
     * no need suspend because return flow
     */
    override fun getAllUsers(): Flow<List<UserModel>?> = userDAO.getAllUsers()

    /**
     * delete [UserModel] in db
     * using id primary key for selected user need delete
     */
    override suspend fun deleteUser(id: Int) = userDAO.deleteUser(id)

    /**
     * delete all user in db
     */
    override suspend fun deleteAllUser() = userDAO.deleteAllUser()
}