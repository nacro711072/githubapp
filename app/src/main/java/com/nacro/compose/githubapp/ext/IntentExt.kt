package com.nacro.compose.githubapp.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openWeb(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent, null)

}