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
package com.gorkem.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.core.presentation.arch.ViewEffect
import com.gorkem.core.presentation.arch.ViewIntent
import com.gorkem.core.presentation.arch.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        STATE : ViewState,
        INTENT : ViewIntent,
        EFFECT : ViewEffect> : ViewModel() {

    /**
     * It holds the last UI state
     * It is state flow because it will send the last state
     * when ui is visible
     */
    private val _mutableState = MutableStateFlow(this.initialState())
    val state: StateFlow<STATE> = _mutableState

    abstract fun initialState(): STATE

    /**
     * User Intentions, send and drop the intent!!!
     * With the shared flow,
     * events are broadcast to an unknown number (zero or more) of subscribers.
     * In the absence of a subscriber, any posted event is immediately dropped.
     * It is a design pattern to use for events that must be processed immediately or not at all.
     */
    private val intents = MutableSharedFlow<INTENT>()

    /**
     * View Effects, show it once!!!
     * With the channel,
     * each event is delivered to a single subscriber.
     * An attempt to post an event without subscribers will suspend as soon as the channel buffer becomes full,
     * waiting for a subscriber to appear.
     * Posted events are never dropped by default.
     */
    private val _effect: Channel<EFFECT> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        /**
         * Listen intents and handle each intent
         */
        viewModelScope.launch {
            if (isActive) {
                intents
                    .distinctUntilChanged()
                    .collect { intent ->
                        applyState(handleIntent(intent))
                    }
            }
        }
    }

    /**
     * State is nullable because developer may need to handle ui with SideEffect
     */
    private fun applyState(newState: STATE?) {
        newState?.let {
            _mutableState.value = newState
            onNewStateUpdated(newState)
        }
    }

    protected fun onNewStateUpdated(newState: STATE) = Unit

    /**
     * Handle Intent and return State
     * It will be saved automatically
     */
    abstract fun handleIntent(intent: INTENT): STATE?

    /**
     * Views send the intention with this function
     */
    fun sendIntent(intent: INTENT) {
        viewModelScope.launch { intents.emit(intent) }
    }

    /**
     * UI will effected with this function from the ViewModel
     * It can be usefull to show Dialog, Toast, Navigation
     */
    protected fun sendEffect(builder: () -> EFFECT) {
        viewModelScope.launch { _effect.send(builder()) }
    }
}
