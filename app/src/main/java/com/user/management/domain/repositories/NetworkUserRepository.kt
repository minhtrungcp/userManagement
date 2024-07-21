package com.user.management.domain.repositories

import com.user.management.domain.models.UserDetailEntity
import com.user.management.domain.models.UserEntity
import kotlinx.coroutines.flow.Flow

interface NetworkUserRepository {
    fun getUserList(perPage: String,
                    page: String): Flow<List<UserEntity>?>
    fun getUserDetailInfo(loginName: String): Flow<UserDetailEntity?>
}