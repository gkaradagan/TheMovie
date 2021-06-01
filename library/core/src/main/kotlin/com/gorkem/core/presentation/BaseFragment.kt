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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.gorkem.core.presentation.arch.ViewEffect
import com.gorkem.core.presentation.arch.ViewIntent
import com.gorkem.core.presentation.arch.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<
    STATE : ViewState,
    INTENT : ViewIntent,
    EFFECT : ViewEffect,
    BINDING : ViewBinding,
    VM : BaseViewModel<STATE, INTENT, EFFECT>,
    > :
    Fragment() {

    private var _binding: BINDING? = null

    // This property is only valid between onCreateView and
    val binding get() = _binding!!

    val viewModel: VM by lazy { viewModel() }

    abstract fun viewModel(): VM

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a new coroutine since repeatOnLifecycle is a suspend function
        lifecycleScope.launch {
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from flow when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                viewModel.state.collect { state ->
                    renderUI(state)
                }

                viewModel.effect.collect { effect ->
                    handleEffect(effect)
                }
            }
        }
    }

    abstract fun renderUI(state: STATE)

    abstract fun handleEffect(effect: EFFECT)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
