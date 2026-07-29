package com.example.tvapps.data.model

data class ShowDto(
    val id: Int,
    val name: String,
    val url: String?,
    val summary: String?,
    val premiered: String?,
    val rating: RatingDto?,
    val image: ImageDto?
)