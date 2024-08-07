package com.user.management.domain.use_case

import com.user.management.domain.models.UserEntity
import com.user.management.domain.repositories.LocalUserRepository
import javax.inject.Inject

class GetUserLocalUseCase @Inject constructor(
    private val localRepository: LocalUserRepository
) {
    suspend fun insertUser(user: UserEntity) = localRepository.insertUser(user)

    suspend fun insertUsers(users: List<UserEntity>) = localRepository.insertUsers(users)

    fun getUsers() = localRepository.getAllUsers()

    suspend fun deleteUser(id: Int) = localRepository.deleteUser(id)

    suspend fun deleteAllUser() = localRepository.deleteAllUser()
}