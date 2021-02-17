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
package com.gorkem.core.presentation.arch

/**
 * NOTE: Intent are supposed to be quick and non blocking.
 *   It's fine if they launch async co-routines of their own,
 *   but they're responsible for scoping/cancellation and so forth.
 */
/*
interface Intent<T> {
    fun reduce(oldState: T): T
}

fun <T> intent(block: T.() -> T) = BlockIntent(block)

class BlockIntent<T>(val block:T.()->T) : Intent<T> {
    override fun reduce(oldState: T): T = block(oldState)
}

*/
/**
 * By delegating work to other models, repositories or services, we
 * end up with situations where we don't need to update our ModelStore
 * state until the delegated work completes.
 *
 * Use the `sideEffect {}` DSL function for those situations.
 *//*

fun <T> sideEffect(block: T.() -> Unit) : Intent<T> = object :
    Intent<T> {
    override fun reduce(oldState: T): T = oldState.apply(block)
}
*/
