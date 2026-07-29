package com.example.tvapps.data.model

data class EpisodeDto(
    val id: Int,
    val name: String?,
    val season: Int?,
    val number: Int?,
    val airdate: String?,
    val runtime: Int?,
    val rating: RatingDto?,
    val image: ImageDto?,
    val summary: String?
)