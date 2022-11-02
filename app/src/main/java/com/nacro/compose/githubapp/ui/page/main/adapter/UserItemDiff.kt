package com.nacro.compose.githubapp.ui.page.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nacro.compose.githubapp.ui.page.main.data.UserItem

class UserItemDiff: DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}