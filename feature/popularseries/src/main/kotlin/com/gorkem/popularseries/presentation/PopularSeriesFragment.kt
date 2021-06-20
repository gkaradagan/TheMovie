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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.gorkem.common.ui.ProgramAdapter
import com.gorkem.core.di.qualifier.ApiImageUrl
import com.gorkem.core.ext.remove
import com.gorkem.core.ext.show
import com.gorkem.core.presentation.BaseFragment
import com.gorkem.popularseries.databinding.FragmentPopularSeriesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularSeriesFragment :
    BaseFragment<PopularSeriesState,
        PopularSeriesIntent,
        PopularSeriesEffect,
        FragmentPopularSeriesBinding,
        PopularSeriesViewModel>() {

    private val _vm: PopularSeriesViewModel by viewModels()

    override fun viewModel(): PopularSeriesViewModel = _vm

    @ApiImageUrl
    @Inject
    lateinit var imageUrl: String

    private val movieAdapter by lazy {
        ProgramAdapter("${imageUrl}w500") {
            viewModel.handleIntent(PopularSeriesIntent.UpdateFavourite(it))
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentPopularSeriesBinding =
        FragmentPopularSeriesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsRecyclerView()
        viewModel.sendIntent(PopularSeriesIntent.LoadPopularTvShows)
    }

    private fun settingsRecyclerView() {
        with(binding.recyclerView) {
            adapter = movieAdapter
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun renderUI(state: PopularSeriesState) {
        with(binding) {
            if (state.isLoading) {
                contentLoading.visibility = View.VISIBLE
                recyclerView.remove()
            } else {
                contentLoading.visibility = View.GONE
                recyclerView.show()
                movieAdapter.addNewList(state.programList)
            }
        }
    }

    override fun handleEffect(effect: PopularSeriesEffect) {
    }
}
