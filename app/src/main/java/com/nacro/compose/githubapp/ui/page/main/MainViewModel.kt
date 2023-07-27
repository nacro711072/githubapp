package com.nacro.compose.githubapp.ui.page.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacro.compose.githubapp.repository.GithubUserRepository
import com.nacro.compose.githubapp.ui.page.main.data.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: GithubUserRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val USER_STATE_HANDLE_KEY = "userList"
    }

    private val _users: MutableStateFlow<List<UserItem>> = MutableStateFlow(
        stateHandle.get(
            USER_STATE_HANDLE_KEY
        ) ?: listOf()
    )
    val users = _users.asStateFlow()

    private val _errMsg = MutableSharedFlow<String>()
    val errMsg = _errMsg.asSharedFlow()

    private var fetchLock: Boolean = false

    init {
        viewModelScope.launch {
            _users.collect {
                stateHandle.set(USER_STATE_HANDLE_KEY, it)
            }
            // TODO: test github action
        }
    }

    fun getUsers() {
        if (isLock()) return

        fetchLock = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserItems().collect { result ->
                result.onSuccess {
                    _users.value = it
                }.onFailure {
                    _errMsg.emit(it.message ?: "Unknown Error")
                }
            }
        }.invokeOnCompletion { fetchLock = false }
    }

    fun getMore() {
        if (isLock()) return

        fetchLock = true
        viewModelScope.launch(Dispatchers.IO) {
            flow<Unit> {
                val lastId = _users.value.last().id
                repository.getUserItems(lastId + 1).collect { result ->
                    result.onSuccess { newList ->
                        _users.getAndUpdate { oldList ->
                            oldList + newList
                        }
                    }.onFailure {
                        _errMsg.emit(it.message ?: "Unknown Error")
                    }
                }
            }.collect()
        }.invokeOnCompletion { fetchLock = false }
    }

    private fun isLock(): Boolean {
        return fetchLock
    }
}