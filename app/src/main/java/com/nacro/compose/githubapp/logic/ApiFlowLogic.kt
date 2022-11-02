package com.nacro.compose.githubapp.logic

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.net.HttpURLConnection

fun <T> Flow<Result<T>>.catchCommonException(): Flow<Result<T>> {
    return catch { e ->
        emit(Result.failure(e))
    }
}
