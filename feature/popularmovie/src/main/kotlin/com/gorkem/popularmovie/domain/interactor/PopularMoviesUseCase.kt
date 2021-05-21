package com.gorkem.popularmovie.domain.interactor

import com.gorkem.common.data.model.PopularResponse
import com.gorkem.common.data.model.mapToUIModel
import com.gorkem.common.domain.model.GenreDomainModel
import com.gorkem.common.ui.ProgramUIModel
import com.gorkem.core.domain.interactor.FlowUseCase
import com.gorkem.core.domain.model.Result
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate

class PopularMoviesUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: MovieRepository,
) :
    FlowUseCase<PopularMoviesUseCase.PopularMovieUseCaseParameter, List<ProgramUIModel>>() {

    var times = 0

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override fun execute(parameters: PopularMovieUseCaseParameter): Flow<Result<List<ProgramUIModel>>> =
        combine(
            repository.getGenreList(parameters.languageCode),
            repository.getPopular(parameters.page)
        ) { genreList: List<GenreDomainModel>, popular: PopularResponse ->
            Result.Success(popular.results.map { it.mapToUIModel(genreList) })
        }.conflate()

    data class PopularMovieUseCaseParameter(
        val page: Int,
        val languageCode: String,
    )
}
