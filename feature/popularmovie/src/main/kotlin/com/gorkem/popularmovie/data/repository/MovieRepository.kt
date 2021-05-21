package com.gorkem.popularmovie.data.repository

import com.gorkem.common.data.model.PopularResponse
import com.gorkem.common.data.model.mapToDomainModel
import com.gorkem.common.domain.model.GenreDomainModel
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.api.MovieService
import com.gorkem.popularmovie.data.local.dao.MovieGenreDao
import com.gorkem.popularmovie.data.local.entity.mapToDomainModel
import com.gorkem.popularmovie.data.local.entity.mapToEntityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface MovieRepository {
    fun getPopular(page: Int): Flow<PopularResponse>
    fun getGenreList(languageCode: String): Flow<List<GenreDomainModel>>
}

class MovieRepositoryImpl(
    private val coroutineDispatchers: AppCoroutineDispatchers,
    private val api: MovieService,
    private val movieGenreDao: MovieGenreDao,
) : MovieRepository {
    override fun getPopular(page: Int): Flow<PopularResponse> =
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
