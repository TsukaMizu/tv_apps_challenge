package com.example.tvapps.data.repository

import com.example.tvapps.data.model.ShowDto
import com.example.tvapps.data.model.CastDto
import com.example.tvapps.data.model.EpisodeDto
import com.example.tvapps.data.model.SeasonDto
import com.example.tvapps.data.remote.TvMazeApiService

class TvRepositoryImpl(
    private val api: TvMazeApiService
) : TvRepository {

    override suspend fun getShows(page: Int): List<ShowDto> {
        return api.getShows(page)
    }

    override suspend fun getShowDetail(id: Int): ShowDto {
        return api.getShowDetail(id)
    }
    override suspend fun getSeasons(showId: Int): List<SeasonDto> {
        return api.getSeasons(showId)
    }

    override suspend fun getEpisodes(showId: Int): List<EpisodeDto> {
        return api.getEpisodes(showId)
    }

    override suspend fun getCast(showId: Int): List<CastDto> {
        return api.getCast(showId)
    }
}