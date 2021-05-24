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
package com.gorkem.popularseries.presentation

import androidx.lifecycle.viewModelScope
import com.gorkem.core.domain.model.Result
import com.gorkem.core.presentation.BaseViewModel
import com.gorkem.core.util.languageTag
import com.gorkem.popularseries.domain.interactor.PopularTvShowsUseCase
import com.gorkem.popularseries.domain.interactor.PopularTvShowsUseCase.PopularTvShowsUseCaseParameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularSeriesViewModel @Inject constructor(private val popularTvShowsUseCase: PopularTvShowsUseCase) :
    BaseViewModel<PopularSeriesState, PopularSeriesIntent, PopularSeriesEffect>() {

    override fun initialState(): PopularSeriesState = PopularSeriesState(
        isLoading = true,
        mutableListOf()
    )

    override fun handleIntent(intent: PopularSeriesIntent) {
        when (intent) {
            PopularSeriesIntent.LoadPopularTvShows -> {
                viewModelScope.launch {
                    popularTvShowsUseCase.invoke(
                        PopularTvShowsUseCaseParameter(
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
                                sendEffect { PopularSeriesEffect.ShowErrorSnackBar }
                            }
                        }
                    }
                }
            }
        }
    }
}
