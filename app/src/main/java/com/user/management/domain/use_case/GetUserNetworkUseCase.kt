package com.user.management.domain.use_case

import com.user.management.domain.repositories.NetworkUserRepository
import javax.inject.Inject

class GetUserNetworkUseCase @Inject constructor(
    private val networkRepository: NetworkUserRepository
) {
    fun getUserList(perPage: String,
                    page: String) = networkRepository.getUserList(perPage, page)

    fun getUserDetailInfo(loginName: String) = networkRepository.getUserDetailInfo(loginName)
}