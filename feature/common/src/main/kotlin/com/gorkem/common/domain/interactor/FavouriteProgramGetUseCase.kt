package com.gorkem.common.domain.interactor

import com.gorkem.common.data.repository.FavouriteProgramRepository
import com.gorkem.common.ui.PopularUIModel
import com.gorkem.core.domain.interactor.FlowUseCase
import com.gorkem.core.domain.model.Empty
import com.gorkem.core.domain.model.Result
import com.gorkem.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class FavouriteProgramGetUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: FavouriteProgramRepository,
) :
    FlowUseCase<Empty, List<PopularUIModel>>() {

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override fun execute(parameters: Empty): Flow<Result<List<PopularUIModel>>> =
        flow {
            repository.getFavouriteList().collect {
                emit((Result.Success(it) as Result<List<PopularUIModel>>))
            }
        }
}
