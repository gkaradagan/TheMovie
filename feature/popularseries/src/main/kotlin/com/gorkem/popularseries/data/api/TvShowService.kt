package com.gorkem.popularseries.data.api

import com.gorkem.common.data.model.PopularResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowService {

    @GET("tv/popular")
    suspend fun popular(@Query("page") page: Int = 1): PopularResponse
}
