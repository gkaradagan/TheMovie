package com.gorkem.popularmovie.data.repository

import com.gorkem.common.data.model.PopularResponse
import com.gorkem.popularmovie.data.api.MovieService

interface MovieRepository {
    suspend fun getPopular(page: Int): PopularResponse
}

class MovieRepositoryImpl(val api: MovieService) : MovieRepository {
    override suspend fun getPopular(page: Int): PopularResponse = api.popular(page)
}
