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
package com.gorkem.movie.presentation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gorkem.core.presentation.BaseActivity
import com.gorkem.movie.R
import com.gorkem.movie.databinding.ActivityDashboardBinding

class DashboardActivity :
    BaseActivity<DashboardState, DashboardIntent, DashboardEffect, ActivityDashboardBinding, DashboardViewModel>() {

    lateinit var navController: NavController

    override fun viewModel(): DashboardViewModel {
        return DashboardViewModel()
    }

    override fun getViewBinding() =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navController
        )
    }

    override fun renderUI(state: DashboardState) = Unit

    override fun handleEffect(effect: DashboardEffect) = Unit

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp() ||
        super.onSupportNavigateUp()
}
