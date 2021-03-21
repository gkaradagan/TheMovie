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
package com.gorkem.favourite.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gorkem.core.presentation.BaseFragment
import com.gorkem.favourite.databinding.FragmentFavouriteBinding

class FavouriteFragment :
    BaseFragment<FavouriteState,
        FavouriteIntent,
        FavouriteEffect,
        FragmentFavouriteBinding,
        FavouriteViewModel>() {
    override fun viewModel(): FavouriteViewModel {
        return FavouriteViewModel()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFavouriteBinding =
        FragmentFavouriteBinding.inflate(
            inflater,
            container,
            false
        )

    override fun renderUI(state: FavouriteState) = Unit

    override fun handleEffect(effect: FavouriteEffect) = Unit
}
