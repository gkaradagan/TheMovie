package com.gorkem.popularseries.data.repository

import com.gorkem.common.data.model.PopularResponse
import com.gorkem.popularseries.data.api.TvShowService

interface TvShowRepository {
    suspend fun getPopular(page: Int): PopularResponse
}

class TvShowRepositoryImpl(val api: TvShowService) : TvShowRepository {
    override suspend fun getPopular(page: Int): PopularResponse = api.popular(page)
}
