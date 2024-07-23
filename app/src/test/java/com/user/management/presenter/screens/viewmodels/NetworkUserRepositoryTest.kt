package com.user.management.presenter.screens.viewmodels

import com.user.management.domain.models.UserDetailEntity
import com.user.management.domain.models.UserEntity
import com.user.management.domain.repositories.NetworkUserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class NetworkUserRepositoryTest : NetworkUserRepository {
    /**
     * The backing hot flow for the list of users for testing.
     */
    private val usersFlow =
        MutableStateFlow<List<UserEntity>>(emptyList())

    /**
     * A test-only API to allow controlling the list of users from tests.
     */
    suspend fun sendUsers(users: List<UserEntity>) {
        usersFlow.emit(users)
    }

    override fun getUserList(perPage: String, page: String): Flow<List<UserEntity>?> {
        return usersFlow
    }

    override fun getUserDetailInfo(loginName: String): Flow<UserDetailEntity?> {
        return flow {  }
    }
}