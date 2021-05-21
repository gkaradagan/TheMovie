package com.gorkem.popularmovie.data.api

import com.gorkem.common.data.model.GenreResponse
import com.gorkem.common.data.model.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun popular(@Query("page") page: Int = 1): PopularResponse

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreResponse
}
