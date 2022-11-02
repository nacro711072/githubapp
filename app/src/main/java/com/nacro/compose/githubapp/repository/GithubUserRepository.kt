package com.nacro.compose.githubapp.repository

import com.nacro.compose.githubapp.api.GithubApiService
import com.nacro.compose.githubapp.logic.catchCommonException
import com.nacro.compose.githubapp.mapper.DefaultDataMapper
import com.nacro.compose.githubapp.ui.page.main.data.UserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubUserRepository(
    private val service: GithubApiService,
    private val mapper: DefaultDataMapper
) {
    fun getUserItems(): Flow<Result<List<UserItem>>> {
        return flow {
            val response = service.getUserList()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val out = body.map { mapper.githubUserRemoteDataToUserItem(it) }
                    emit(Result.success(out))
                } else {
                    throw Exception("Oops! The body is empty.")
                }
            }
        }.catchCommonException()
    }
    fun getUserItems(since: Int): Flow<Result<List<UserItem>>> {
        return flow {
            val response = service.getUserList(since)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val out = body.map { mapper.githubUserRemoteDataToUserItem(it) }
                    emit(Result.success(out))
                } else {
                    throw Exception("Oops! The body is empty.")
                }
            }
        }.catchCommonException()
    }
}