package com.gorkem.movie.di

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
