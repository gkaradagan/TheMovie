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
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.gorkem.core.presentation.arch.ViewEffect
import com.gorkem.core.presentation.arch.ViewIntent
import com.gorkem.core.presentation.arch.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<STATE : ViewState,
  INTENT : ViewIntent,
  EFFECT : ViewEffect,
  BINDING : ViewBinding,
  VM : BaseViewModel<STATE, INTENT, EFFECT>> :
  Fragment() {

  private var uiStateJob: Job? = null

  private var _binding: BINDING? = null

  // This property is only valid between onCreateView and
  private val binding get() = _binding!!

  val viewModel: VM by lazy { viewModel() }

  abstract fun viewModel(): VM

  abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = getViewBinding(inflater, container)
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    // Start collecting when the View is visible
    // https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#livedata
    uiStateJob = lifecycleScope.launch {
      viewModel.state.collect { state ->
        renderUI(state)
      }

      viewModel.effect.collect { effect ->
        handleEffect(effect)
      }
    }
  }

  abstract fun renderUI(state: STATE)

  abstract fun handleEffect(effect: EFFECT)

  override fun onStop() {
    // Stop collecting when the View goes to the background
    uiStateJob?.cancel()
    super.onStop()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
