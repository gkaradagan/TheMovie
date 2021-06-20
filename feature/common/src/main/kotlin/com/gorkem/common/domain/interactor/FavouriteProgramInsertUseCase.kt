package com.gorkem.common.domain.interactor

import com.gorkem.common.data.repository.FavouriteProgramRepository
import com.gorkem.common.ui.ProgramUIModel
import com.gorkem.common.ui.mapToEntity
import com.gorkem.core.domain.interactor.NoResultUseCase
import com.gorkem.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher

class FavouriteProgramInsertUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: FavouriteProgramRepository,
) :
    NoResultUseCase<FavouriteProgramInsertUseCase.Parameter>() {

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.default

    override suspend fun execute(parameters: Parameter) {
        repository.insert(parameters.programUIModel.mapToEntity())
    }

    data class Parameter(
        val programUIModel: ProgramUIModel
    )
}
