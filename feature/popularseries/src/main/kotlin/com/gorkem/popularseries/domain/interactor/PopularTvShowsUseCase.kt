package com.gorkem.popularseries.domain.interactor

import com.gorkem.common.ui.ProgramUIModel
import com.gorkem.core.domain.interactor.FlowUseCase
import com.gorkem.core.domain.model.Result
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularseries.data.repository.TvShowRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PopularTvShowsUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: TvShowRepository,
) :
    FlowUseCase<Int, List<ProgramUIModel>>() {

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override fun execute(parameters: Int): Flow<Result<List<ProgramUIModel>>> = flow {
        emit(Result.Loading)
        val popularMovies = repository.getPopular(parameters)
        // emit(Result.Success(popularMovies.results.map { it.mapToUIModel() }))
    }
}
