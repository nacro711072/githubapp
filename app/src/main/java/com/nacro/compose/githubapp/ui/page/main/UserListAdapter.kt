package com.nacro.compose.githubapp.ui.page.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.nacro.compose.githubapp.R
import com.nacro.compose.githubapp.ext.openWeb
import com.nacro.compose.githubapp.ui.page.main.vm.UserItem
import com.nacro.compose.githubapp.ui.transformation.CircleStrokeTransformation
import com.nacro.compose.githubapp.ui.transformation.Stroke

class UserListAdapter: ListAdapter<UserItem, ViewHolder>(UserItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.github_user_item_view, parent, false)
        return object : ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < 0) return

        val item = getItem(position)

        val avatarView = holder.itemView.findViewById<ImageView>(R.id.iv_avatar)
        avatarView.load(item.avatarUrl) {
            crossfade(true)
            transformations(CircleStrokeTransformation(Stroke(4f, Color.Blue.toArgb())))
        }

        val nameView = holder.itemView.findViewById<TextView>(R.id.tv_name)
        nameView.text = item.name

        holder.itemView.setOnClickListener { v ->
            v.context.openWeb("http://www.google.com")
        }
    }
}