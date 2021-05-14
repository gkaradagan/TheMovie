package com.gorkem.core.di

import com.gorkem.core.data.interceptor.TheMovieInterceptor
import com.gorkem.core.di.qualifier.ApiUrl
import com.gorkem.core.di.qualifier.Debug
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(@Debug isDebug: Boolean) =
        HttpLoggingInterceptor().apply {
            level =
                if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        theMovieInterceptor: TheMovieInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(theMovieInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiUrl apiUrl: String,
        okhttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okhttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()
}
