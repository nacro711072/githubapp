package com.nacro.compose.githubapp.ui.page.main

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nacro.compose.githubapp.ext.OnBottomReached
import com.nacro.compose.githubapp.ui.page.main.data.UserItem
import com.nacro.compose.githubapp.ext.openWeb
import com.nacro.compose.githubapp.ui.page.main.adapter.UserListAdapter
import com.nacro.compose.githubapp.ui.theme.ShimmerColors
import com.nacro.compose.githubapp.ui.transformation.CircleStrokeTransformation
import com.nacro.compose.githubapp.ui.transformation.Stroke


@Composable
fun UserListScreen(
    userList: List<UserItem> = listOf(),
    useRecyclerView: Boolean = true, // 是否使用 RecyclerView 繪製 list
    onBottomReached: () -> Unit = {},
) {
    if (useRecyclerView) {
        RecyclerListView(userList, onBottomReached)
    } else {
        LazyColListView(userList, onBottomReached)
    }


}

@Composable
private fun RecyclerListView(
    userList: List<UserItem>,
    onBottomReached: () -> Unit = {}
) {
    val userListAdapter = remember { UserListAdapter() }
    val lazyState = rememberLazyListState()
    lazyState.OnBottomReached(onBottomReached)

    LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyState) {
        item {
            AndroidView(factory = { context ->
                RecyclerView(context).apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = userListAdapter
                    addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

//                    如果純 recycler view 要實作 infinity loading, 要加個 scroll listener
//
//                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                            val lm = (recyclerView.layoutManager as? LinearLayoutManager)
//                            if (dy > 0) {
//                                val totalItemCount = lm?.itemCount ?: 0
//                                val lastItemPosition = lm?.findLastVisibleItemPosition() ?: 0
//
//                                滑至剩不到n個項目的時候就加載
//                                val n = 3
//                                if (totalItemCount > 0 && totalItemCount - lastItemPosition < n) {
//                                    onBottomReached.invoke()
//                                }
//                            }
//                        }
//                    })
                }
            }, modifier = Modifier.fillMaxSize()) {
                userListAdapter.submitList(userList)
            }
        }

        items(3) {
            ShimmerItem(ShimmerColors)
        }
    }

}

@Composable
private fun LazyColListView(
    userList: List<UserItem>,
    onBottomReached: () -> Unit = {}
) {
    val context = LocalContext.current
    val lazyState = rememberLazyListState()
    lazyState.OnBottomReached(onBottomReached)

    LazyColumn(Modifier.fillMaxSize(), state = lazyState) {
        items(items = userList, key = { item ->
            item.id
        }) { item ->
            UserItemCell(user = item) {
                context.openWeb(item.userHtmlUrl)
            }
            Divider(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 2.dp),
                color = Color.LightGray
            )
        }

        items(3) {
            ShimmerItem(ShimmerColors)
        }
    }
}

@Composable
private fun UserItemCell(user: UserItem, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(16.dp)
            .height(48.dp)
    ) {

        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(2.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarUrl)
                .crossfade(true)
                .transformations(
                    CircleStrokeTransformation(Stroke(4f, Color.Blue.toArgb()))
                )
                .build(),
            contentDescription = "avatar"
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .align(Alignment.CenterVertically)
            ,
            text = user.name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun ShimmerItem(shimmerColors: List<Color>) {

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp)
    ) {

        Spacer(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(brush)
            )

        Spacer(modifier = Modifier.width(16.dp))

        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(24.dp)
                .align(Alignment.CenterVertically)
                .background(brush)
            ,
        )
    }

}