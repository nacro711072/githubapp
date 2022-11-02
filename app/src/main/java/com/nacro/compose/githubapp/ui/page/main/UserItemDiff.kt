package com.nacro.compose.githubapp.ui.page.main

import androidx.recyclerview.widget.DiffUtil
import com.nacro.compose.githubapp.ui.page.main.vm.UserItem

class UserItemDiff: DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}