package com.example.tvapps.data.remote

import com.example.tvapps.data.model.CastDto
import com.example.tvapps.data.model.EpisodeDto
import com.example.tvapps.data.model.SeasonDto
import com.example.tvapps.data.model.ShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApiService {
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int = 0): List<ShowDto>

    @GET("shows/{id}")
    suspend fun getShowDetail(@Path("id") id: Int): ShowDto

    @GET("shows/{id}/seasons")
    suspend fun getSeasons(@Path("id") id: Int): List<SeasonDto>

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") id: Int): List<EpisodeDto>

    @GET("shows/{id}/cast")
    suspend fun getCast(@Path("id") id: Int): List<CastDto>
}