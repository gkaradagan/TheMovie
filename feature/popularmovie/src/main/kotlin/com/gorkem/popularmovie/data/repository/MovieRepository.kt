/*
 * Copyright 2021 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gorkem.popularmovie.data.repository

import com.gorkem.common.data.model.mapToDomainModel
import com.gorkem.common.domain.model.GenreDomainModel
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.api.MovieService
import com.gorkem.popularmovie.data.local.dao.MovieGenreDao
import com.gorkem.popularmovie.data.local.entity.mapToDomainModel
import com.gorkem.popularmovie.data.local.entity.mapToEntityModel
import com.gorkem.popularmovie.data.model.PopularMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface MovieRepository {
    fun getPopular(page: Int): Flow<PopularMovieResponse>
    fun getGenreList(languageCode: String): Flow<List<GenreDomainModel>>
}

class MovieRepositoryImpl(
    private val coroutineDispatchers: AppCoroutineDispatchers,
    private val api: MovieService,
    private val movieGenreDao: MovieGenreDao,
) : MovieRepository {
    override fun getPopular(page: Int): Flow<PopularMovieResponse> =
        flow { emit(api.popular(page)) }

    override fun getGenreList(languageCode: String): Flow<List<GenreDomainModel>> = flow {
        val cached = movieGenreDao.getAllGenre(languageCode)
        if (cached.isNotEmpty()) {
            emit(cached.map { it.mapToDomainModel() })
        }

        val newList = api.getGenreList().genres

        movieGenreDao.insertGenre(newList.map { it.mapToEntityModel(languageCode) })

        emit(newList.map { it.mapToDomainModel() })
    }.flowOn(coroutineDispatchers.default)
}
