package com.gorkem.core.util

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
)
