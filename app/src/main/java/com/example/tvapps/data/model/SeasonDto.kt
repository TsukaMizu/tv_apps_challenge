package com.example.tvapps.data.model

data class SeasonDto(
    val id: Int,
    val number: Int? = null,
    val episodeOrder: Int? = null,
    val premiereDate: String? = null,
    val endDate: String? = null
)