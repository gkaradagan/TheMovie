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
import com.gorkem.common.domain.interactor.FavouriteProgramDeleteUseCase
import com.gorkem.common.domain.interactor.FavouriteProgramInsertUseCase
import com.gorkem.core.domain.model.Result
import com.gorkem.core.presentation.BaseViewModel
import com.gorkem.core.util.languageTag
import com.gorkem.popularmovie.domain.interactor.PopularMoviesUseCase
import com.gorkem.popularmovie.domain.interactor.PopularMoviesUseCase.PopularMovieUseCaseParameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val favouriteProgramInsertUseCase: FavouriteProgramInsertUseCase,
    private val favouriteProgramDeleteUseCase: FavouriteProgramDeleteUseCase
) :
    BaseViewModel<PopularMovieState, PopularMovieIntent, PopularMovieEffect>() {

    override fun initialState(): PopularMovieState = PopularMovieState(
        isLoading = true,
        mutableListOf()
    )

    override fun handleIntent(intent: PopularMovieIntent) {
        when (intent) {
            PopularMovieIntent.LoadPopularMovies -> {
                viewModelScope.launch {
                    popularMoviesUseCase.invoke(
                        PopularMovieUseCaseParameter(
                            1,
                            languageTag()
                        )
                    ).collect { result ->
                        when (result) {
                            Result.Loading -> setState { copy(isLoading = true) }
                            is Result.Success -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        programList = result.data.results
                                    )
                                }
                            }
                            is Result.Error -> {
                                sendEffect { PopularMovieEffect.ShowErrorSnackBar }
                            }
                        }
                    }
                }
            }
            is PopularMovieIntent.UpdateFavourite -> {
                viewModelScope.launch {
                    if (intent.program.isFavourite) {
                        favouriteProgramInsertUseCase.invoke(
                            FavouriteProgramInsertUseCase.Parameter(
                                intent.program
                            )
                        )
                    } else {
                        favouriteProgramDeleteUseCase.invoke(
                            FavouriteProgramDeleteUseCase.Parameter(
                                intent.program.id
                            )
                        )
                    }
                }
            }
        }
    }
}
