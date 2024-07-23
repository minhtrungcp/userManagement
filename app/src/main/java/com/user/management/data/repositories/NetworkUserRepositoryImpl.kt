package com.user.management.data.repositories

import com.user.management.data.GithubApi
import com.user.management.data.models.dto.UserDTO
import com.user.management.data.models.dto.UserDetailDTO
import com.user.management.data.models.dto.toUserDetailEntity
import com.user.management.data.models.dto.toUserEntity
import com.user.management.domain.models.UserDetailEntity
import com.user.management.domain.models.UserEntity
import com.user.management.domain.repositories.NetworkUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkUserRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : NetworkUserRepository {

    /**
     * get list [UserEntity] from github api
     * using retrofit fetch data dto
     * mapper [UserDTO] to [UserEntity]
     * emit data response to flow collect
     */
    override fun getUserList(perPage: String, page: String): Flow<List<UserEntity>> = flow {
        val userList = api.getUserList(perPage, page)
        emit(userList?.map { it.toUserEntity() }.orEmpty())
    }

    /**
     * get [UserDetailEntity] from github api
     * using retrofit fetch data dto
     * mapper [UserDetailDTO] to [UserDetailEntity]
     * emit data response to flow collect
     */
    override fun getUserDetailInfo(loginName: String): Flow<UserDetailEntity?> = flow {
        val userDTO = api.getUserDetail(loginName)
        emit(userDTO?.toUserDetailEntity())
    }
}