package com.example.tvapps.util

import android.os.Build
import android.text.Html

/**
 * TVMaze mengembalikan field `summary` dalam bentuk HTML (mis. "<p>Some text</p>").
 * Extension ini membersihkannya jadi plain text yang aman ditampilkan di Compose Text().
 */
fun String?.fromHtml(): String {
    if (this.isNullOrBlank()) return ""
    val spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
    return spanned.toString().trim()
}