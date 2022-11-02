package com.nacro.compose.githubapp.ui.page.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacro.compose.githubapp.ui.page.main.vm.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainViewModel: ViewModel() {

    private val _users: MutableStateFlow<List<UserItem>> = MutableStateFlow(listOf())
    val users = _users.asStateFlow()

    fun getUsers() {
        val list = mutableListOf<UserItem>()
        for (i in 0..9) {
            list.add(UserItem(i, "test$i", "https://avatars.githubusercontent.com/u/17497074?v=4"))
        }
        _users.value = list
    }

    fun getMore() {
        viewModelScope.launch(Dispatchers.IO) {
            flow<Unit> {
                delay(3000L)
                _users.getAndUpdate {
                    val newList = mutableListOf<UserItem>()
                    val start = it.size
                    for (i in start..start + 10) {
                        newList.add(UserItem(i, "test$i", "https://avatars.githubusercontent.com/u/17497074?v=4"))
                    }

                    it + newList
                }
            }.collect()
        }
    }
}