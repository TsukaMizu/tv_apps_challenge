package com.example.tvapps.ui.list

import com.example.tvapps.data.model.ShowDto

data class ShowListItemUi(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val ratingText: String
)

fun ShowDto.toListItemUi(): ShowListItemUi = ShowListItemUi(
    id = id,
    title = name,
    posterUrl = image?.medium,
    ratingText = rating?.average?.let { "★ %.1f".format(it) } ?: "N/A"
)
