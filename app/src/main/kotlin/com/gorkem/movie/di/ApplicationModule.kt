package com.gorkem.movie.di

import com.gorkem.core.di.qualifier.ApiKey
import com.gorkem.core.di.qualifier.ApiUrl
import com.gorkem.core.di.qualifier.BuildType
import com.gorkem.core.di.qualifier.Debug
import com.gorkem.movie.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ApplicationModule {

    @ApiKey
    @Provides
    fun provideApiKey(): String = BuildConfig.API_KEY

    @ApiUrl
    @Provides
    fun provideApiUrl(): String = BuildConfig.API_URL

    @BuildType
    @Provides
    fun provideBuildType(): String = BuildConfig.BUILD_TYPE

    @Debug
    @Provides
    fun provideDebug(): Boolean = BuildConfig.DEBUG
}
