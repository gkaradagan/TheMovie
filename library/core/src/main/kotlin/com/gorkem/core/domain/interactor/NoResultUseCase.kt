package com.gorkem.core.domain.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class NoResultUseCase<in P> {

    @Suppress("detekt.TooGenericExceptionCaught")
    suspend operator fun invoke(parameters: P) {
        try {
            withContext(dispatcher) {
                execute(parameters)
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    abstract val dispatcher: CoroutineDispatcher

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P)
}
