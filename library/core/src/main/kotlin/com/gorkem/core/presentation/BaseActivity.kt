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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.gorkem.core.presentation.arch.ViewEffect
import com.gorkem.core.presentation.arch.ViewIntent
import com.gorkem.core.presentation.arch.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseActivity<STATE : ViewState,
  INTENT : ViewIntent,
  EFFECT : ViewEffect,
  BINDING : ViewBinding,
  VM : BaseViewModel<STATE, INTENT, EFFECT>> :
  AppCompatActivity() {

  private var uiStateJob: Job? = null

  lateinit var binding: BINDING

  val viewModel: VM by lazy { viewModel() }

  abstract fun viewModel(): VM

  abstract fun getViewBinding(): BINDING

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = getViewBinding()
    setContentView(binding.root)
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
}
