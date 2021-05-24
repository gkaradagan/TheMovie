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
package com.gorkem.popularmovie.domain.interactor

import com.gorkem.common.domain.model.GenreDomainModel
import com.gorkem.common.ui.PopularUIModel
import com.gorkem.core.domain.interactor.FlowUseCase
import com.gorkem.core.domain.model.Result
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.model.PopularMovieResponse
import com.gorkem.popularmovie.data.model.mapToUIModel
import com.gorkem.popularmovie.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

class PopularMoviesUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: MovieRepository,
) :
    FlowUseCase<PopularMoviesUseCase.PopularMovieUseCaseParameter, PopularUIModel>() {

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override fun execute(parameters: PopularMovieUseCaseParameter): Flow<Result<PopularUIModel>> =
        flow {
            emit(Result.Loading)
            combine(
                repository.getGenreList(parameters.languageCode),
                repository.getPopular(parameters.page)
            ) { genreList: List<GenreDomainModel>, popular: PopularMovieResponse ->
                Result.Success(popular.mapToUIModel(genreList))
            }.conflate().collect {
                emit(it)
            }
        }

    data class PopularMovieUseCaseParameter(
        val page: Int,
        val languageCode: String,
    )
}
