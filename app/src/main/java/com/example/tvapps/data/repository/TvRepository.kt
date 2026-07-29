package com.example.tvapps.data.repository

import com.example.tvapps.data.model.CastDto
import com.example.tvapps.data.model.EpisodeDto
import com.example.tvapps.data.model.SeasonDto
import com.example.tvapps.data.model.ShowDto

interface TvRepository {
    suspend fun getShows(page: Int = 0): List<ShowDto>
    suspend fun getShowDetail(id: Int): ShowDto
    suspend fun getSeasons(showId: Int): List<SeasonDto>
    suspend fun getEpisodes(showId: Int): List<EpisodeDto>
    suspend fun getCast(showId: Int): List<CastDto>
}