package com.nacro.compose.githubapp.mapper

import com.nacro.compose.githubapp.api.model.GithubUserRemoteData
import com.nacro.compose.githubapp.ui.page.main.data.UserItem

class DefaultDataMapper {

    fun githubUserRemoteDataToUserItem(origin: GithubUserRemoteData): UserItem {
        return UserItem(origin.id, origin.login, origin.avatarUrl, origin.htmlUrl)
    }
}