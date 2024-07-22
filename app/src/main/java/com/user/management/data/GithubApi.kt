package com.user.management.data

import com.user.management.data.models.dto.UserDTO
import com.user.management.data.models.dto.UserDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("/users")
    suspend fun getUserList(
        @Query("per_page") perPage: String,
        @Query("page") page: String
    ): List<UserDTO>?

    @GET("/users/{username}")
    suspend fun getUserDetail(
        @Path("username") userName: String
    ): UserDetailDTO?
}