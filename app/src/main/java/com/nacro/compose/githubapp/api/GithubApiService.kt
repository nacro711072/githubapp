package com.nacro.compose.githubapp.api

import com.nacro.compose.githubapp.api.model.GithubUserRemoteData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users")
    suspend fun getUserList(): Response<List<GithubUserRemoteData>>

    @GET("/users")
    suspend fun getUserList(
        @Query("since") since: Int
    ): Response<List<GithubUserRemoteData>>

}