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
package com.gorkem.movie.di

import com.gorkem.core.di.qualifier.ApiImageUrl
import com.gorkem.core.di.qualifier.ApiKey
import com.gorkem.core.di.qualifier.ApiUrl
import com.gorkem.core.di.qualifier.BuildType
import com.gorkem.core.di.qualifier.Debug
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.movie.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApplicationModule {

    @ApiKey
    @Provides
    fun provideApiKey(): String = BuildConfig.API_KEY

    @ApiImageUrl
    @Provides
    fun provideApiImageUrl(): String = BuildConfig.API_IMAGE_URL

    @ApiUrl
    @Provides
    fun provideApiUrl(): String = BuildConfig.API_URL

    @BuildType
    @Provides
    fun provideBuildType(): String = BuildConfig.BUILD_TYPE

    @Debug
    @Provides
    fun provideDebug(): Boolean = BuildConfig.DEBUG

    @Singleton
    @Provides
    fun provideAppCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        default = Dispatchers.Default,
        main = Dispatchers.Main
    )
}
