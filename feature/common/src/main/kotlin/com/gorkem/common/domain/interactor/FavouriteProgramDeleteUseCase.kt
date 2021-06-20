package com.gorkem.common.domain.interactor

import com.gorkem.common.data.repository.FavouriteProgramRepository
import com.gorkem.core.domain.interactor.NoResultUseCase
import com.gorkem.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher

class FavouriteProgramDeleteUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: FavouriteProgramRepository,
) :
    NoResultUseCase<FavouriteProgramDeleteUseCase.Parameter>() {

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.default

    override suspend fun execute(parameters: Parameter) {
        repository.delete(parameters.id.toString())
    }

    data class Parameter(
        val id: Int
    )
}
