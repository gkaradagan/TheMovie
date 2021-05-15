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
package com.gorkem.popularmovie.presentation

import androidx.lifecycle.viewModelScope
import com.gorkem.core.domain.model.Result
import com.gorkem.core.presentation.BaseViewModel
import com.gorkem.popularmovie.domain.interactor.PopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(private val popularMoviesUseCase: PopularMoviesUseCase) :
    BaseViewModel<PopularMovieState, PopularMovieIntent, PopularMovieEffect>() {

    override fun initialState(): PopularMovieState = PopularMovieState(
        isLoading = false,
        mutableListOf()
    )

    override fun handleIntent(intent: PopularMovieIntent) {
        when (intent) {
            PopularMovieIntent.LoadPopularMovies -> {
                viewModelScope.launch {
                    popularMoviesUseCase.invoke(1).collect { result ->
                        when (result) {
                            Result.Loading -> setState { copy(isLoading = true) }
                            is Result.Success -> {
                                setState { copy(isLoading = false, programList = result.data) }
                            }
                            is Result.Error -> {
                                sendEffect { PopularMovieEffect.ShowErrorSnackBar }
                            }
                        }
                    }
                }
            }
        }
    }
}
